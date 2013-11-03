package com.atex.milan.plugins.wmodefilter.engine;

import com.atex.milan.plugins.wmodefilter.meters.gauges.CacheHitRatioGauge;
import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.Meter;
import com.yammer.metrics.core.Timer;
import com.yammer.metrics.core.TimerContext;
import com.yammer.metrics.ehcache.InstrumentedEhcache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
import net.sourceforge.wurfl.core.Constants;
import net.sourceforge.wurfl.core.GeneralWURFLEngine;
import net.sourceforge.wurfl.core.WURFLEngine;
import net.sourceforge.wurfl.core.resource.WURFLResource;
import net.sourceforge.wurfl.core.resource.XMLResource;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeviceModelEngine
{
  private static final Logger logger = Logger.getLogger(DeviceModelEngine.class.getName());
  
  private final WURFLEngine wurflEngine;
  private final Ehcache cache;
  
  private final Meter cacheHits;
  private final Meter cacheMiss;
  private final Timer cacheTotal;
  
  private static URI wurflZipURI = null;

  public DeviceModelEngine()
  {
    cacheHits = Metrics.newMeter(DeviceModelEngine.class, "cacheHits", "cacheHits", TimeUnit.SECONDS);
    cacheMiss = Metrics.newMeter(DeviceModelEngine.class, "cacheMiss", "cacheMiss", TimeUnit.SECONDS);
    cacheTotal = Metrics.newTimer(DeviceModelEngine.class, "cacheTotal", TimeUnit.MILLISECONDS, TimeUnit.SECONDS);
    
    Metrics.newGauge(DeviceModelEngine.class, "cacheHitsRatio", new CacheHitRatioGauge(cacheHits, cacheTotal));
    Metrics.newGauge(DeviceModelEngine.class, "cacheMissRatio", new CacheHitRatioGauge(cacheMiss, cacheTotal));

    final CacheManager mgr = CacheManager.getInstance();
    
    final CacheConfiguration cacheConfiguration = new CacheConfiguration(DeviceModelEngine.class.getName(), 10000)
      .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU)
      .overflowToDisk(false)
      .maxElementsOnDisk(20000)
      .eternal(false)
      .timeToLiveSeconds(0)
      .timeToIdleSeconds(3600)
      .diskPersistent(false);
  
    final net.sf.ehcache.Cache cache = new net.sf.ehcache.Cache(cacheConfiguration);
    mgr.addCache(cache);
    
    this.cache = InstrumentedEhcache.instrument(cache);
    
    this.wurflEngine = initWURFLEngine();
  }
  
  private WURFLEngine getWURFLEngine()
  {
    return wurflEngine;
  }
  
  private WURFLEngine initWURFLEngine()
  {
    try {
      final URI wurflUri = getWurflZip();
      
      logger.log(Level.FINE, "WURFL zip: " + wurflUri.toString());
      
      final WURFLResource root = new XMLResource(wurflUri);
      return new GeneralWURFLEngine(root);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  URI getWurflZip() throws Exception
  {
    if (wurflZipURI == null) {
      final File f = File.createTempFile("wurfl", ".zip");
      final InputStream is = this.getClass().getResourceAsStream("/wurfl.zip");
      final FileOutputStream fos = new FileOutputStream(f);
      IOUtils.copy(is, fos);
      fos.flush();
      fos.close();
      is.close();
      f.deleteOnExit();
      wurflZipURI = f.toURI();
    }
    return wurflZipURI;
  }
  
  public Device getDevice(final HttpServletRequest request)
  {
    final String userAgent = request.getHeader(Constants.USER_AGENT);
    return getDevice(userAgent);
  }
  
  public Device getDevice(final String userAgent)
  {
    try {
      return getContent(userAgent, new DeviceFactory(getWURFLEngine(), userAgent));
    } catch (ExecutionException e) {
      logger.log(Level.SEVERE, String.format("Error search for user agent %s: %s", userAgent, e.getMessage()), e);
    }
    return null;
  }
  
  private Device getContent(final String userAgent, final Callable<Device> callable) throws ExecutionException
  {
    final TimerContext context = cacheTotal.time();
    try {
      Element element = cache.get(userAgent);
      if (element == null && callable != null) {
        try {
          cacheMiss.mark();
          final Device value = callable.call();
          if (value != null) {
            setContent(userAgent, value);
          }
          return value;
        } catch (Exception e) {
          throw new ExecutionException(e);
        }
      }
      if (element != null) {
        cacheHits.mark();
        return (Device) element.getObjectValue();
      }
      return null;
    } finally {
      context.stop();
    }
  }

  private void setContent(final String userAgent, final Device device)
  {
    cache.put(new Element(userAgent, device));
  }
  
}

package com.atex.milan.plugins.wmodefilter.engine;

import java.net.URI;
import java.util.UUID;

import junit.framework.TestCase;
import net.sourceforge.wurfl.core.Constants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DeviceModelEngineTest extends TestCase
{
  static DeviceModelEngine engine = null;
  
  @Before
  public void before()
  {
    if (engine == null) {
      engine = new DeviceModelEngine();
    }
  }
  
  @Test
  public void test_WURFLZip() throws Exception
  {
    final URI uri = engine.getWurflZip();
    assertNotNull(uri);
    System.out.println(uri);
  }
  
  @Test
  public void test_NoDevice()
  {
    final String userAgent = UUID.randomUUID().toString();
    
    final Device device = engine.getDevice(userAgent);
    assertNotNull(device);
    
    assertEquals("", device.getUa());
    assertEquals(Constants.GENERIC, device.getId());
    assertEquals(false, device.isMobile());
    assertEquals(true, device.isDesktop());
    assertEquals(false, device.isTablet());
  }
  
  @Test
  public void test_mobile()
  {
    final String userAgent = "Mozilla/5.0 (iPhone; U; CPU like Mac OS X; zh-tw) AppleWebKit/420.1 (KHTML, like Gecko) Version/3.0 Mobile/3B48b Safari/419.3";
    
    final Device device = engine.getDevice(userAgent);
    assertNotNull(device);
    
    System.out.println(device);
    
    assertEquals(userAgent, device.getUa());
    assertTrue(device.getId().toLowerCase().contains("iphone"));
    assertEquals(true, device.isMobile());
    assertEquals(false, device.isDesktop());
    assertEquals(false, device.isTablet());
  }

  @Test
  public void test_ipad()
  {
    final String userAgent = "Mozilla/5.0 (iPad; U; CPU iPhone OS 3_2 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Mobile/7D11";
    
    final Device device = engine.getDevice(userAgent);
    assertNotNull(device);
    
    System.out.println(device);
    
    assertEquals(userAgent, device.getUa());
    assertTrue(device.getId().toLowerCase().contains("ipad"));
    assertEquals(false, device.isMobile());
    assertEquals(false, device.isDesktop());
    assertEquals(true, device.isTablet());
  }

  @Test
  public void test_android_phone()
  {
    final String userAgent = "Mozilla/5.0 (Linux; Android 4.0.4; Galaxy Nexus Build/IMM76B) " +
    		"AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.133 Mobile " +
    		"Safari/535.19";
    
    final Device device = engine.getDevice(userAgent);
    assertNotNull(device);
    
    System.out.println(device);
    
    assertTrue(device.getUa().toLowerCase().contains("android"));
    assertTrue(device.getId().toLowerCase().contains("nexus"));
    assertEquals(true, device.isMobile());
    assertEquals(false, device.isDesktop());
    assertEquals(false, device.isTablet());
  }

  @Test
  public void test_android_tablet()
  {
    final String userAgent = "Mozilla/5.0 (Linux; U; Android 2.2; en-us; SCH-I800 Build/FROYO) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";
    
    final Device device = engine.getDevice(userAgent);
    assertNotNull(device);
    
    System.out.println(device);
    
    assertTrue(device.getUa().toLowerCase().contains("android"));
    assertEquals(false, device.isMobile());
    assertEquals(false, device.isDesktop());
    assertEquals(true, device.isTablet());
  }

  @Test
  public void test_desktop_firefox()
  {
    final String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:24.0) Gecko/20100101 Firefox/24.0";
    
    final Device device = engine.getDevice(userAgent);
    assertNotNull(device);
    
    System.out.println(device);
    
    assertEquals(false, device.isMobile());
    assertEquals(true, device.isDesktop());
    assertEquals(false, device.isTablet());
  }
  
  @Test
  public void test_desktop_chrome()
  {
    final String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36";
    
    final Device device = engine.getDevice(userAgent);
    assertNotNull(device);
    
    System.out.println(device);
    
    assertEquals(false, device.isMobile());
    assertEquals(true, device.isDesktop());
    assertEquals(false, device.isTablet());
  }
  
  @Test
  public void test_desktop_safari()
  {
    final String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_5) AppleWebKit/536.30.1 (KHTML, like Gecko) Version/6.0.5 Safari/536.30.1";
    
    final Device device = engine.getDevice(userAgent);
    assertNotNull(device);
    
    System.out.println(device);
    
    assertEquals(false, device.isMobile());
    assertEquals(true, device.isDesktop());
    assertEquals(false, device.isTablet());
  }
  
}

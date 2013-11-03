package com.atex.milan.plugins.wmodefilter.engine;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DeviceTest extends TestCase
{
  Random rnd = new Random(new Date().getTime());
  
  final String id = UUID.randomUUID().toString();
  final String ua = UUID.randomUUID().toString();
  
  @Test
  public void test_device_random()
  {
    final boolean wireless = rnd.nextBoolean();
    final boolean tablet = rnd.nextBoolean();
    
    Device d = new Device(id, ua, wireless, tablet);
    assertNotNull(d);
    assertEquals(id, d.getId());
    assertEquals(ua, d.getUa());
    assertEquals(wireless, d.isWireless());
    assertEquals(tablet, d.isTablet());
  }
  
  @Test
  public void test_device_mobile()
  {
    final boolean wireless = true;
    final boolean tablet = false;
    
    Device d = new Device(id, ua, wireless, tablet);
    assertNotNull(d);
    assertEquals(id, d.getId());
    assertEquals(ua, d.getUa());
    assertEquals(true, d.isMobile());
    assertEquals(false, d.isTablet());
    assertEquals(false, d.isDesktop());
  }
  
  @Test
  public void test_device_tablet_1()
  {
    final boolean wireless = true;
    final boolean tablet = true;
    
    Device d = new Device(id, ua, wireless, tablet);
    assertNotNull(d);
    assertEquals(id, d.getId());
    assertEquals(ua, d.getUa());
    assertEquals(false, d.isMobile());
    assertEquals(true, d.isTablet());
    assertEquals(false, d.isDesktop());
  }

  @Test
  public void test_device_tablet_2()
  {
    final boolean wireless = false;
    final boolean tablet = true;
    
    Device d = new Device(id, ua, wireless, tablet);
    assertNotNull(d);
    assertEquals(id, d.getId());
    assertEquals(ua, d.getUa());
    assertEquals(false, d.isMobile());
    assertEquals(true, d.isTablet());
    assertEquals(false, d.isDesktop());
  }

  @Test
  public void test_device_desktop()
  {
    final boolean wireless = false;
    final boolean tablet = false;
    
    Device d = new Device(id, ua, wireless, tablet);
    assertNotNull(d);
    assertEquals(id, d.getId());
    assertEquals(ua, d.getUa());
    assertEquals(false, d.isMobile());
    assertEquals(false, d.isTablet());
    assertEquals(true, d.isDesktop());
  }
}

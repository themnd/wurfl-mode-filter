package com.atex.milan.plugins.wmodefilter.engine;

import java.util.concurrent.Callable;

import net.sourceforge.wurfl.core.WURFLEngine;

import com.polopoly.util.StringUtil;

public class DeviceFactory implements Callable<Device>
{
  private final String userAgent;
  private final WURFLEngine engine;
  
  public DeviceFactory(final WURFLEngine engine, final String userAgent)
  {
    this.engine = engine;
    this.userAgent = userAgent;
  }
  
  @Override
  public Device call() throws Exception
  {
    net.sourceforge.wurfl.core.Device device = engine.getDeviceForRequest(userAgent);
    if (device != null) {
      final boolean wireless = StringUtil.equals(device.getCapability("is_wireless_device"), "true");
      final boolean tablet = StringUtil.equals(device.getCapability("is_tablet"), "true");
      return new Device(device.getId(), device.getUserAgent(), wireless, tablet);
    }
    return null;
  }
  
}

package com.atex.milan.plugins.wmodefilter.filter;

import javax.servlet.http.HttpServletRequest;

import com.atex.milan.plugins.wmodefilter.engine.Device;

public abstract class DeviceInRequest
{
  private final static String DEVICE_REQUEST_INFO_ATTR = DeviceInRequest.class + ".wurflDevice";
  
  public static Device getDeviceRequestInfo(final HttpServletRequest request)
  {
    Object deviceAttribute = request.getAttribute(DEVICE_REQUEST_INFO_ATTR);
    if (deviceAttribute != null || deviceAttribute instanceof Device) {
      return (Device) deviceAttribute;
    } else {
      return null;
    }
  }
  
  public static void setDeviceRequestInfo(final Device device, final HttpServletRequest request)
  {
    request.setAttribute(DEVICE_REQUEST_INFO_ATTR, device);
  }
  
}

package com.atex.milan.plugins.wmodefilter.filter;

import java.net.URI;

import com.atex.milan.plugins.wmodefilter.engine.Device;
import com.atex.milan.plugins.wmodefilter.model.WURFLRequestHeader;
import com.polopoly.siteengine.dispatcher.mode.ModeFilter;
import com.polopoly.siteengine.dispatcher.mode.RequestMode;
import com.polopoly.siteengine.dispatcher.mode.RequestModeImpl;
import com.polopoly.siteengine.model.RequestHeader;

public class ModeFilterWURFL implements ModeFilter
{
  private final String MOBILEMODE = "mobile";

  @Override
  public URI rewriteUrlWithMode(final URI requestUrl, final String mode)
  {
    return null;
  }

  @Override
  public URI rewriteUrlWithoutMode(final URI requestUrl, final String mode)
  {
    return requestUrl;
  }

  @Override
  public RequestMode detectMode(final URI requestUrl, final RequestHeader requestHeader)
  {
    RequestMode requestMode = null;
    
    if (requestHeader instanceof WURFLRequestHeader) {
      WURFLRequestHeader req = (WURFLRequestHeader) requestHeader;
      final Device device = DeviceInRequest.getDeviceRequestInfo(req.getRequest());
      if (device != null && device.isMobile()) {
        requestMode = new RequestModeImpl(requestUrl, MOBILEMODE);
      }
    }
    
    return requestMode;
  }
  
}

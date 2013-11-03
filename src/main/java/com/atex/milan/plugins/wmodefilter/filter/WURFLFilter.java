package com.atex.milan.plugins.wmodefilter.filter;

import com.atex.milan.plugins.wmodefilter.engine.Device;
import com.atex.milan.plugins.wmodefilter.engine.DeviceModelEngine;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class WURFLFilter extends WURFLModeUrlTranslatorFilter
{
  ServletContext servletContext;
  DeviceModelEngine engine;
  
  @Override
  public void init(final FilterConfig config) throws ServletException
  {
    this.servletContext = config.getServletContext();
    this.engine = new DeviceModelEngine();

    super.init(config);
  }
  
  @Override
  public void destroy()
  {
    this.engine = null;
    super.destroy();
  }

  @Override
  public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException,
      ServletException
  {
    final HttpServletRequest request = (HttpServletRequest) servletRequest;
    if (DeviceInRequest.getDeviceRequestInfo(request) == null) {
      final Device device = engine.getDevice(request);
      DeviceInRequest.setDeviceRequestInfo(device, request);
    }

    // Right now this filter is extending WURFLModeUrlTranslatorFilter so we should call the super.doFilter method.
    // If this filter implements only the Filter interface you should uncomment the following line and comment out
    // the super.doFilter method.

    //filterChain.doFilter(servletRequest, servletResponse);
    super.doFilter(servletRequest, servletResponse, filterChain);
  }
  
}

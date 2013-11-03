package com.atex.milan.plugins.wmodefilter.model;

import javax.servlet.http.HttpServletRequest;

import com.polopoly.siteengine.model.RequestHeaderFromHttpServletRequest;

public class WURFLRequestHeader extends RequestHeaderFromHttpServletRequest
{
  private HttpServletRequest request;
  
  public WURFLRequestHeader(final HttpServletRequest request)
  {
    super(request);
    this.request = request;
  }
  
  public HttpServletRequest getRequest()
  {
    return request;
  }
}

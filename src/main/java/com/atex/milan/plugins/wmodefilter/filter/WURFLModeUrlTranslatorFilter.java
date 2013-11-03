package com.atex.milan.plugins.wmodefilter.filter;

import com.atex.milan.plugins.wmodefilter.model.WURFLRequestHeader;
import com.polopoly.cm.policy.ObjectResolver;
import com.polopoly.cm.policy.PolicyCMServer;
import com.polopoly.cm.policy.PolicyObjectResolver;
import com.polopoly.common.servlet.RequestUrlFactory;
import com.polopoly.siteengine.dispatcher.FilterUtil;
import com.polopoly.siteengine.dispatcher.mode.*;
import com.polopoly.siteengine.dispatcher.mode.filter.ModeFilterProviderConfiguredModes;
import com.polopoly.siteengine.model.RequestHeader;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

public class WURFLModeUrlTranslatorFilter implements Filter
{
  ServletContext servletContext;
  ModeUrlDispatcher modeUrlDispatcher;
  ModeDetector modeFilter;
  ModeInRequest modeInRequest;
  RequestedUrlInRequest requestedUrlInRequest;
  
  @Override
  public void init(final FilterConfig config) throws ServletException
  {
    this.servletContext = config.getServletContext();
    PolicyCMServer cmServer = FilterUtil.getCmServer(config);

    ObjectResolver objectResovler = new PolicyObjectResolver(cmServer);

    ModeFilterProvider modeFilterProvider = new ModeFilterProviderConfiguredModes(objectResovler);

    this.modeFilter = new ModeDetectorFromModeFilterProvider(new CachedModeProvider(modeFilterProvider));

    this.modeUrlDispatcher = new ModeUrlDispatcher(this.servletContext);

    this.modeInRequest = new ModeInRequestImpl();
    this.requestedUrlInRequest = new RequestedUrlInRequest(new RequestUrlFactory());
  }
  
  @Override
  public void destroy()
  {
  }

  @Override
  public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException,
      ServletException
  {
    HttpServletRequest request = (HttpServletRequest)servletRequest;
    HttpServletResponse response = (HttpServletResponse)servletResponse;

    RequestHeader requestHeader = new WURFLRequestHeader(request);

    this.requestedUrlInRequest.saveRequestedUrlIfNotAlreadySaved(request);

    String path = request.getServletPath();
    if (request.getPathInfo() != null) {
      path = path + request.getPathInfo();
    }

    RequestUrlFactory requestUrlFactory = new RequestUrlFactory();
    URI requestUrl = requestUrlFactory.createURIFromRequestWithNewPathAndQuery(request, path, null);

    RequestMode requestMode = this.modeFilter.detectMode(requestUrl, requestHeader);
    boolean hasDispatched = false;

    if (requestMode != null) {
      this.modeInRequest.setRequestMode(request, requestMode);
      String redirectionAttribute = "p.modeFilter.redirection";
      String redirectedUri = (String)request.getAttribute(redirectionAttribute);
      String thisRedirect = requestUrl.toASCIIString();
      if ((redirectedUri == null) || (!thisRedirect.equals(redirectedUri))) {
        request.setAttribute(redirectionAttribute, thisRedirect);
        hasDispatched = this.modeUrlDispatcher.dispatch(request, response, requestMode);
      }
      ((HttpServletResponse) servletResponse).setHeader("X-channelMode", requestMode.getMode());
    }

    if (!hasDispatched) {
      filterChain.doFilter(servletRequest, servletResponse);
    }
  }
  
}

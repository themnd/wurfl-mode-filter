package com.atex.milan.plugins.wmodefilter.filter;

import com.polopoly.cm.client.CMException;
import com.polopoly.siteengine.dispatcher.mode.ModeFilter;
import com.polopoly.siteengine.dispatcher.mode.filter.ModeFilterBasePolicy;

public class ModeFilterWURFLPolicy extends ModeFilterBasePolicy
{

  @Override
  protected ModeFilter getModeFilterInternal() throws CMException
  {
    return new ModeFilterWURFL();
  }
  
}

package com.atex.milan.plugins.wmodefilter.meters.gauges;

import com.yammer.metrics.core.Meter;
import com.yammer.metrics.core.Timer;
import com.yammer.metrics.util.RatioGauge;

public class CacheHitRatioGauge extends RatioGauge
{
  private final Meter hits;
  private final Timer calls;

  public CacheHitRatioGauge(Meter hits, Timer calls)
  {
    this.hits = hits;
    this.calls = calls;
  }

  @Override
  public double getNumerator()
  {
    return hits.oneMinuteRate();
  }

  @Override
  public double getDenominator()
  {
    return calls.oneMinuteRate();
  }
}

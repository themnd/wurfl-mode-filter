package com.atex.milan.plugins.wmodefilter.engine;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Objects;

/**
 * Recognized device.
 * 
 * @author mnova
 *
 */
public class Device
{
  /**
   * Unique identifier
   */
  final private String id;
  
  /**
   * User agent
   */
  final private String ua;
  
  /**
   * Wireless (i.e. not a Desktop/laptop)
   */
  final private boolean wireless;
  
  /**
   * Table (i.e. iPad)
   */
  final private boolean tablet;
  
  public Device(final String id, final String ua, final boolean wireless, final boolean tablet)
  {
    checkNotNull(id, "id cannot be null");
    checkNotNull(ua, "ua cannot be null");

    this.id = id;
    this.ua = ua;
    this.wireless = wireless;
    this.tablet = tablet;
  }
  
  /**
   * Return the unique identifier
   * 
   * @return a not null String.
   */
  public String getId()
  {
    return id;
  }
  
  /**
   * Return the user agent string
   * 
   * @return a not null String.
   */
  public String getUa()
  {
    return ua;
  }
  
  /**
   * Tell you if this device is a tablet/pda/mobile (i.e. not a Desktop/laptop)
   * 
   * @return
   */
  public boolean isWireless()
  {
    return wireless;
  }
  
  /**
   * Tell you if this device is a tablet.
   * 
   * @return
   */
  public boolean isTablet()
  {
    return tablet;
  }
  
  /**
   * Tell you if this device is a mobile phone.
   * 
   * @return
   */
  public boolean isMobile()
  {
    return wireless && !tablet;
  }
  
  /**
   * Tell you if this device is a desktop/laptop.
   * 
   * @return
   */
  public boolean isDesktop()
  {
    return !wireless && !tablet;
  }
  
  @Override
  public int hashCode()
  {
    return Objects.hashCode(id, ua, wireless, tablet);
  }
  
  @Override
  public String toString()
  {
    return Objects.toStringHelper("Device")
      .add("id", id)
      .add("ua", ua)
      .add("wireless", wireless)
      .add("tablet", tablet)
      .toString();
  }
  
}

package com.dattp.order.config.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

public class MutableHttpServletRequest extends HttpServletRequestWrapper {
  private final Map<String, Object> customHeaders;
  public MutableHttpServletRequest(HttpServletRequest request) {
    super(request);
    this.customHeaders = new HashMap<String, Object>();
  }

  public void putHeader(String name, Object value){
    this.customHeaders.put(name, value);
  }

  @Override
  public String getHeader(String name) {
    // check the custom headers first
    Object headerValue = customHeaders.get(name);
    if (headerValue != null){
      return (String) headerValue;
    }
    // else return from into the original wrapped object
    return ((HttpServletRequest) getRequest()).getHeader(name);
  }

  @Override
  public Enumeration<String> getHeaders(String name) {
    Enumeration<String> originalValues = super.getHeaders(name);
    String customValue = (String) customHeaders.get(name);
    if (customValue != null) {
      return Collections.enumeration(Collections.singletonList(customValue));
    }
    return originalValues;
  }

  @Override
  public Enumeration<String> getHeaderNames() {
    // create a set of the custom header names
    Set<String> set = new HashSet<String>(customHeaders.keySet());
    // now add the headers from the wrapped request object
    Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaderNames();
    while (e.hasMoreElements()) {
      // add the names of the request headers into the list
      String n = e.nextElement();
      set.add(n);
    }
    // create an enumeration from the set and return
    return Collections.enumeration(set);
  }
}
/* Copyright (c) 2008-2015, Avian Contributors

   Permission to use, copy, modify, and/or distribute this software
   for any purpose with or without fee is hereby granted, provided
   that the above copyright notice and this permission notice appear
   in all copies.

   There is NO WARRANTY for this software.  See license.txt for
   details. */

package java.util;

import java.io.InputStream;
import java.io.IOException;
import java.io.Reader;

public class PropertyResourceBundle extends ResourceBundle {
  private final HashMap<String, Object> map;

  public PropertyResourceBundle(InputStream in) throws IOException {
    Properties properties = new Properties();
    properties.load(in);
    map = new HashMap(properties);
  }
  public PropertyResourceBundle (Reader reader) throws IOException {
      Properties properties = new Properties();
      properties.load(reader);
      map = new HashMap(properties);
    }

  public Object handleGetObject(String key) {
    return map.get(key);
  }

  public Enumeration<String> getKeys() {
    return new Collections.IteratorEnumeration<String>(map.keySet().iterator());
  }
}

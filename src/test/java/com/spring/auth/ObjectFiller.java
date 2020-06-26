package com.spring.auth;

import java.lang.reflect.Field;

/** @author diegotobalina created on 24/06/2020 */
public class ObjectFiller {

  public void replace(Object object, String fieldName, Object newValue)
      throws IllegalAccessException {
    for (Field field : object.getClass().getDeclaredFields()) {
      if (field.getName().equals(fieldName)) {
        field.setAccessible(true);
        field.set(object, newValue);
      }
    }
  }
}

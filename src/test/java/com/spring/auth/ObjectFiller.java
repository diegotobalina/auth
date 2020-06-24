package com.spring.auth;

import java.lang.reflect.Field;

public class ObjectFiller {

  public Object replace(Object object, String fieldName, Object newValue)
      throws IllegalAccessException {
    for (Field field : object.getClass().getDeclaredFields()) {
      if (field.getName().equals(fieldName)) {
        field.setAccessible(true);
        field.set(object, newValue);
      }
    }
    return object;
  }
}

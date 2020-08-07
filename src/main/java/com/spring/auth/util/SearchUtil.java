package com.spring.auth.util;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/** @author diegotobalina created on 03/08/2020 */
public class SearchUtil {

  public static Example<?> getExample(Object example, List<String> wantedFields) {
    String[] notWantedFields = getNotWantedFields(example.getClass(), wantedFields);
    ExampleMatcher matcher =
        ExampleMatcher.matching()
            .withIgnorePaths(notWantedFields)
            .withIgnoreNullValues()
            .withIgnoreCase()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    return Example.of(example, matcher);
  }

  private static String[] getNotWantedFields(Class<?> objectClass, List<String> wantedFields) {
    List<String> response = new ArrayList<>();
    for (Field field : objectClass.getDeclaredFields()) {
      String fieldName = field.getName();
      if (!wantedFields.contains(fieldName)) response.add(fieldName);
    }
    String[] strings = response.toArray(new String[response.size()]);
    if (strings.length == 0) return new String[] {"_"};
    return strings;
  }
}

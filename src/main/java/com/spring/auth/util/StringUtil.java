package com.spring.auth.util;

import org.apache.commons.lang3.StringUtils;

public interface StringUtil {
  static String removeRowJumps(String string) {
    if (StringUtils.isBlank(string)) return null;
    return string.replace("\n", "").replace("\r", "");
  }
}

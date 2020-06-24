package com.spring.auth.util;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class StringUtilTest {

  @Test
  public void removeRowJumps() {
    String input = "\n \rtesting \n test\ring";
    String response = StringUtil.removeRowJumps(input);
    assertEquals(" testing  testing", response);
  }
}

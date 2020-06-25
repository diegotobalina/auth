package com.spring.auth.util;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
/** @author diegotobalina created on 24/06/2020 */
class StringUtilTest {

  @Test
  public void removeRowJumps() {
    String input = "\n \rtesting \n test\ring";
    String response = StringUtil.removeRowJumps(input);
    assertEquals(" testing  testing", response);
  }
}

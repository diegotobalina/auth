package com.spring.auth.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class StringUtilTest {

  @Test
  public void removeRowJumps() {
    String input = "\n \rtesting \n test\ring";
    String response = StringUtil.removeRowJumps(input);
    assertEquals(" testing  testing", response);
  }
}

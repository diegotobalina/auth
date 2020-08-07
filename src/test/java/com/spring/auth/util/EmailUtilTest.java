package com.spring.auth.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EmailUtilTest {

  @Test
  void doEmailExists_exists() {
    String existingEmail = "test@asdf.com";
    boolean exists = EmailUtil.doEmailExists(existingEmail);
    Assertions.assertEquals(true, exists);
  }

  @Test
  void doEmailExists_notexists() {
    String existingEmail = "not_existing_email@not_existing_domain.zz";
    boolean exists = EmailUtil.doEmailExists(existingEmail);
    Assertions.assertEquals(false, exists);
  }
}

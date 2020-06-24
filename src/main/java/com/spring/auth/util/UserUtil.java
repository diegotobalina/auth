package com.spring.auth.util;

import com.spring.auth.user.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Principal;
import java.util.UUID;

public abstract class UserUtil {

  public static User getUserFromPrincipal(Principal principal) {
    UsernamePasswordAuthenticationToken authenticationToken =
        (UsernamePasswordAuthenticationToken) principal;
    return (User) authenticationToken.getPrincipal();
  }

  public static String getUserIdFromPrincipal(Principal principal) {
    return getUserFromPrincipal(principal).getId();
  }

  public static String generateUsername(String email) {
    if (StringUtils.isBlank(email)) return null;
    if (!RegexUtil.isEmail(email)) return null;
    String randomSuffix = String.valueOf(System.currentTimeMillis());
    return email.split("@")[0] + randomSuffix.substring(randomSuffix.length() - 4);
  }

  public static String generateRandomPassword() {
    return System.currentTimeMillis() + "-" + UUID.randomUUID().toString();
  }
}

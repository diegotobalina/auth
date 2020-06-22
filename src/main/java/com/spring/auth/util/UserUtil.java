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
    return email.split("@")[0] + UUID.randomUUID().toString().substring(0, 4);
  }

  public static String generateRandomPassword() {
    return System.currentTimeMillis() + "-" + UUID.randomUUID().toString();
  }
}

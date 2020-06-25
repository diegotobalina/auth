package com.spring.auth.util;

import org.apache.commons.lang3.StringUtils;
/** @author diegotobalina created on 24/06/2020 */
public interface RegexUtil {

  static boolean isBearerJwt(String jwt) {
    if (StringUtils.isBlank(jwt)) return false;
    String tokenWithoutPrefix = TokenUtil.removeBearerPrefix(jwt);
    String regex = "^[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.?[A-Za-z0-9-_.+/=]*$";
    return tokenWithoutPrefix.matches(regex);
  }

  static boolean isGoogleJwt(String jwt) {
    if (StringUtils.isBlank(jwt)) return false;
    String tokenWithoutPrefix = TokenUtil.removeGooglePrefix(jwt);
    String regex = "^[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.?[A-Za-z0-9-_.+/=]*$";
    return tokenWithoutPrefix.matches(regex);
  }

  static boolean isEmail(String email) {
    if (StringUtils.isBlank(email)) return false;
    String regex =
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    return email.matches(regex);
  }

  static boolean isSessionToken(String token) {
    if (StringUtils.isBlank(token)) return false;
    String tokenWithoutPrefix = TokenUtil.removeBearerPrefix(token);
    String regex = "[0-9]{13}-[a-z0-9]{8}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{12}";
    return tokenWithoutPrefix.matches(regex);
  }

  static boolean isValidUsername(String string) {
    return string.matches("[a-zA-Z0-9._\\-]{3,15}");
  }

  static boolean isValidPassword(String password) {
    if (StringUtils.isBlank(password)) return false;
    if (password.length() < 6) return false;
    if (password.length() > 254) return false;
    return true;
  }
}

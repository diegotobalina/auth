package com.spring.auth.util;

import com.spring.auth.exceptions.application.InvalidTokenException;
import com.spring.auth.role.domain.Role;
import com.spring.auth.scope.domain.Scope;
import com.spring.auth.user.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/** @author diegotobalina created on 24/06/2020 */
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

  public static User getUserFromBearerJwt(String jwtWithoutPrefix, String secretKey)
      throws InvalidTokenException {
    TokenUtil.JwtWrapper jwtWrapper = TokenUtil.getValues(jwtWithoutPrefix, secretKey);
    String userId = jwtWrapper.getUserId();
    List<String> roleValues = jwtWrapper.getRoles();
    List<Role> roles = getRolesFromValues(roleValues);
    List<String> scopeValues = jwtWrapper.getScopes();
    List<Scope> scopes = getScopesFromValues(scopeValues);
    return new User(userId, roles, scopes);
  }

  private static List<Scope> getScopesFromValues(List<String> scopesString) {
    return scopesString.stream().map(Scope::new).collect(Collectors.toList());
  }

  private static List<Role> getRolesFromValues(List<String> rolesString) {
    return rolesString.stream().map(Role::new).collect(Collectors.toList());
  }
}

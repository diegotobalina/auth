package com.spring.auth.components;

import com.spring.auth.scope.domain.Scope;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

  @Override
  public boolean hasPermission(
      Authentication authentication, Object accessType, Object permission) {
    String requiredPermission = String.valueOf(permission);
    List<Scope> validScopes = getValidScopes(authentication, requiredPermission);
    return (validScopes.isEmpty()) ? false : true;
  }

  private List<Scope> getValidScopes(Authentication authentication, String requiredPermission) {
    List<Scope> scopes = (List<Scope>) authentication.getCredentials();
    List<Scope> validScopes = new ArrayList<>();
    for (Scope scope : scopes) if (isValidScope(requiredPermission, scope)) validScopes.add(scope);
    return validScopes;
  }

  private boolean isValidScope(String requiredPermission, Scope scope) {
    return requiredPermission.startsWith(scope.getValue());
  }

  @Override
  public boolean hasPermission(
      Authentication authentication,
      Serializable serializable,
      String targetType,
      Object permission) {
    return false;
  }
}

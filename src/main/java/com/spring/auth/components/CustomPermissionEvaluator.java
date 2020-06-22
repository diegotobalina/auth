package com.spring.auth.components;

import com.spring.auth.scope.domain.Scope;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Evaluate scope permissions
 *
 * @author diegotobalina
 */
@Component
@Slf4j
public class CustomPermissionEvaluator implements PermissionEvaluator {
  @Override
  public boolean hasPermission(
      final Authentication authentication, final Object accessType, final Object permission) {

    // input validations
    final String requiredPermission = String.valueOf(permission);
    if (Objects.isNull(authentication)) return false;
    if (Objects.isNull(authentication.getCredentials())) return false;
    if (ObjectUtils.isEmpty(authentication.getCredentials())) return false;
    if (!(authentication.getCredentials() instanceof List<?>)) return false;

    // check if the user have the needed permissions ( scope )
    final List<Scope> scopes = (List<Scope>) authentication.getCredentials();
    final List<Scope> validScopes =
        scopes.stream()
            .filter(scope -> requiredPermission.startsWith(scope.getValue()))
            .collect(Collectors.toList());

    if (validScopes.isEmpty()) { // user has not the required permissions
      return false;
    }
    return true;
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

package com.spring.auth.filters;

import com.spring.auth.util.AuthenticationUtil;
import com.spring.auth.role.domain.Role;
import com.spring.auth.scope.domain.Scope;
import com.spring.auth.util.RegexUtil;
import com.spring.auth.util.TokenUtil;
import com.spring.auth.util.TokenUtil.JwtWrapper;
import com.spring.auth.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class BearerAuthenticationFilter extends OncePerRequestFilter {

  private final String secretKey;

  public BearerAuthenticationFilter(final String secretKey) {
    this.secretKey = secretKey;
  }

  @Override
  protected void doFilterInternal(
      final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
      throws ServletException, IOException {

    // check if should try to process the token
    String jwtWithPrefix = request.getHeader("Authorization");
    boolean isGoogleJwt = RegexUtil.isBearerJwt(jwtWithPrefix);
    boolean isAuthenticated = AuthenticationUtil.isAuthenticated();
    if (!isGoogleJwt || isAuthenticated) {
      chain.doFilter(request, response);
      return;
    }

    // try to login with the token
    try {
      final String jwtWithoutPrefix = TokenUtil.removeBearerPrefix(jwtWithPrefix);
      final JwtWrapper jwtWrapper = TokenUtil.getValues(jwtWithoutPrefix, secretKey);
      final String userId = jwtWrapper.getUserId();
      final List<String> rolesString = jwtWrapper.getRoles();
      final List<Role> roles = rolesString.stream().map(Role::new).collect(Collectors.toList());
      final List<String> scopesString = jwtWrapper.getScopes();
      final List<Scope> scopes = scopesString.stream().map(Scope::new).collect(Collectors.toList());
      final User user = new User(userId, roles, scopes);
      AuthenticationUtil.authenticate(user);
      log.info("authentication ok for user {}", user.getId());
    } catch (Exception ex) {
      log.warn("exception: {}", ex.getMessage());
    } finally {
      chain.doFilter(request, response);
    }
  }
}

package com.spring.auth.filters;

import com.spring.auth.exceptions.application.InvalidTokenException;
import com.spring.auth.role.domain.Role;
import com.spring.auth.scope.domain.Scope;
import com.spring.auth.user.domain.User;
import com.spring.auth.util.AuthenticationUtil;
import com.spring.auth.util.RegexUtil;
import com.spring.auth.util.TokenUtil;
import com.spring.auth.util.TokenUtil.JwtWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/** @author diegotobalina created on 24/06/2020 */
@Slf4j
public class BearerAuthenticationFilter extends OncePerRequestFilter {

  private String secretKey;

  public BearerAuthenticationFilter(String secretKey) {
    this.secretKey = secretKey;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    // check if should try to process the token
    String jwtWithPrefix = request.getHeader("Authorization");
    boolean isBearerJwt = RegexUtil.isBearerJwt(jwtWithPrefix);
    boolean isAuthenticated = AuthenticationUtil.isAuthenticated();
    if (!isBearerJwt || isAuthenticated) {
      chain.doFilter(request, response);
      return;
    }

    // try to login with the token
    try {
      String jwtWithoutPrefix = TokenUtil.removeBearerPrefix(jwtWithPrefix);
      User user = getUserFromBearerJwt(jwtWithoutPrefix);
      AuthenticationUtil.authenticate(user);
      log.info("authentication ok for user {}", user.getId());
    } catch (Exception ex) {
      log.warn("exception: {}", ex.getMessage());
    } finally {
      chain.doFilter(request, response);
    }
  }

  private User getUserFromBearerJwt(String jwtWithoutPrefix) throws InvalidTokenException {
    JwtWrapper jwtWrapper = TokenUtil.getValues(jwtWithoutPrefix, secretKey);
    String userId = jwtWrapper.getUserId();
    List<String> rolesString = jwtWrapper.getRoles();
    List<Role> roles = rolesString.stream().map(Role::new).collect(Collectors.toList());
    List<String> scopesString = jwtWrapper.getScopes();
    List<Scope> scopes = scopesString.stream().map(Scope::new).collect(Collectors.toList());
    return new User(userId, roles, scopes);
  }
}

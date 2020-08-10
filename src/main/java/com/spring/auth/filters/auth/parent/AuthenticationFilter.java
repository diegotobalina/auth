package com.spring.auth.filters.auth.parent;

import com.spring.auth.exceptions.application.*;
import com.spring.auth.user.domain.User;
import com.spring.auth.util.AuthenticationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;

/** @author diegotobalina created on 24/06/2020 */
@Slf4j
public abstract class AuthenticationFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    // check if should try to process the token
    String token = request.getHeader("Authorization");
    boolean isAcceptedToken = isAcceptedToken(token);
    boolean isAuthenticated = AuthenticationUtil.isAuthenticated();
    if (!isAcceptedToken || isAuthenticated) {
      chain.doFilter(request, response);
      return;
    }

    // try to login with the token
    try {
      User user = getUserFromToken(token); // get user data only from jwt
      AuthenticationUtil.authenticate(user);
      log.info("authentication ok for user {}", user.getId());
    } catch (Exception ex) {
      log.warn("exception: {}", ex.getMessage());
    } finally {
      chain.doFilter(request, response);
    }
  }

  protected abstract User getUserFromToken(String token)
      throws InvalidTokenException, GeneralSecurityException, DuplicatedKeyException,
          LockedUserException, EmailDoesNotExistsException, GoogleGetInfoException,
          NotFoundException, InfiniteLoopException, IOException;

  protected abstract boolean isAcceptedToken(String token);
}

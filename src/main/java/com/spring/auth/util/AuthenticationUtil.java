package com.spring.auth.util;

import com.spring.auth.role.domain.Role;
import com.spring.auth.user.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

/** @author diegotobalina created on 24/06/2020 */
public class AuthenticationUtil {
  public static boolean isAuthenticated() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication != null
        && authentication.isAuthenticated()
        && !authentication.getPrincipal().equals("anonymousUser");
  }

  public static void authenticate(User user) {
    List<SimpleGrantedAuthority> simpleGrantedAuthorities =
        getSimpleGrantedAuthorities(user.getRoles());
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        new UsernamePasswordAuthenticationToken(user, user.getScopes(), simpleGrantedAuthorities);
    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
  }

  private static List<SimpleGrantedAuthority> getSimpleGrantedAuthorities(List<Role> roles) {
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority(role.getValue()))
        .collect(Collectors.toList());
  }
}

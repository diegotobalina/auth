package com.spring.auth.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.auth.exceptions.application.InvalidTokenException;
import com.spring.auth.role.domain.Role;
import com.spring.auth.scope.domain.Scope;
import com.spring.auth.user.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public abstract class TokenUtil {

  private static final String PREFIX = "Bearer ";
  private static final String GOOGLE_PREFIX = "Google ";

  private TokenUtil() {}

  public static String addBearerPrefix(final String token) {
    if (StringUtils.startsWith(token, PREFIX)) return token;
    return PREFIX + token;
  }

  public static String addGooglePrefix(final String token) {
    if (StringUtils.startsWith(token, GOOGLE_PREFIX)) return token;
    return GOOGLE_PREFIX + token;
  }

  public static String removeBearerPrefix(final String token) {
    if (!StringUtils.startsWith(token, PREFIX)) return token;
    return token.replace(PREFIX, "");
  }

  public static String removeGooglePrefix(final String token) {
    if (!StringUtils.startsWith(token, GOOGLE_PREFIX)) return token;
    return token.replace(GOOGLE_PREFIX, "");
  }

  public static JwtWrapper generateBearerJwt(final User user, final String secretKey) {
    final Map<String, Object> claims = generateJwtClaims(user);
    final String userId = user.getId();
    final Date issuedAt = new Date(System.currentTimeMillis());
    final long expirationTime = Long.valueOf(1000 * 60 * 5); // 1s > 1m > 5m
    final Date expiration = new Date(issuedAt.getTime() + expirationTime);
    byte[] secretKeyBytes = secretKey.getBytes();
    String jwt = generateBearerJwt(userId, claims, issuedAt, expiration, secretKeyBytes);
    var scopes = user.getScopes().stream().map(Scope::getValue).collect(Collectors.toList());
    var roles = user.getRoles().stream().map(Role::getValue).collect(Collectors.toList());
    return new JwtWrapper(jwt, issuedAt, expiration, userId, roles, scopes);
  }

  public static JwtWrapper getValues(final String jwt, final String secretKey)
      throws InvalidTokenException {
    try {
      final Claims claims = getClaims(jwt, secretKey);
      final String userId = getUserId(claims);
      final Date issuedAt = getIssuedAt(claims);
      final Date expiration = getExpiration(claims);
      final List<String> roles = getRoles(claims);
      final List<String> scopes = getScopes(claims);
      return new JwtWrapper(jwt, issuedAt, expiration, userId, roles, scopes);
    } catch (Exception exception) {
      throw new InvalidTokenException("invalid jwt: " + exception.getMessage());
    }
  }

  private static Map<String, Object> generateJwtClaims(final User user) {
    final String userId = user.getId();
    var scopes = user.getScopes().stream().map(Scope::getValue).collect(Collectors.toList());
    var roles = user.getRoles().stream().map(Role::getValue).collect(Collectors.toList());
    return generateJwtClaims(userId, scopes, roles);
  }

  private static Map<String, Object> generateJwtClaims(
      final String userId, final List<String> scopes, final List<String> roles) {
    HashMap<String, Object> map = new HashMap<>();
    map.put("user", userId);
    map.put("scopes", scopes);
    map.put("roles", roles);
    return map;
  }

  private static String generateBearerJwt(
      final String userId,
      final Map<String, Object> claims,
      final Date issuedDate,
      final Date expirationDate,
      final byte[] key) {
    final String jwtId = UUID.randomUUID().toString();
    return Jwts.builder()
        .setId(jwtId)
        .setSubject(userId)
        .setClaims(claims)
        .setIssuedAt(issuedDate)
        .setExpiration(expirationDate)
        .signWith(SignatureAlgorithm.HS512, key)
        .compact();
  }

  private static Claims getClaims(final String token, final String secretKey) {
    return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody();
  }

  private static List<String> getRoles(Claims claims) {
    final ObjectMapper mapper = new ObjectMapper();
    final Object roles = claims.get("roles");
    return mapper.convertValue(roles, new TypeReference<>() {});
  }

  private static List<String> getScopes(Claims claims) {
    final ObjectMapper mapper = new ObjectMapper();
    final Object scopes = claims.get("scopes");
    return mapper.convertValue(scopes, new TypeReference<>() {});
  }

  private static String getUserId(Claims claims) {
    final Object userId = claims.get("user");
    return userId.toString();
  }

  private static Date getIssuedAt(Claims claims) {
    return claims.getIssuedAt();
  }

  private static Date getExpiration(Claims claims) {
    return claims.getExpiration();
  }

  @Getter
  @AllArgsConstructor
  public static class JwtWrapper {
    private final String token;
    private final Date IssuedAt;
    private final Date expiration;
    private final String userId;
    private final List<String> roles;
    private final List<String> scopes;
  }
}

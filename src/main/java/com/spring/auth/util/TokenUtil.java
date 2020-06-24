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

  private static String PREFIX = "Bearer ";
  private static String GOOGLE_PREFIX = "Google ";

  private TokenUtil() {}

  public static String addBearerPrefix(String token) {
    if (StringUtils.startsWith(token, PREFIX)) return token;
    return PREFIX + token;
  }

  public static String addGooglePrefix(String token) {
    if (StringUtils.startsWith(token, GOOGLE_PREFIX)) return token;
    return GOOGLE_PREFIX + token;
  }

  public static String removeBearerPrefix(String token) {
    if (!StringUtils.startsWith(token, PREFIX)) return token;
    return token.replace(PREFIX, "");
  }

  public static String removeGooglePrefix(String token) {
    if (!StringUtils.startsWith(token, GOOGLE_PREFIX)) return token;
    return token.replace(GOOGLE_PREFIX, "");
  }

  public static JwtWrapper generateBearerJwt(User user, String secretKey) {
    Map<String, Object> claims = generateJwtClaims(user);
    String userId = user.getId();
    Date issuedAt = new Date(System.currentTimeMillis());
    long expirationTime = Long.valueOf(1000 * 60 * 5); // 1s > 1m > 5m
    Date expiration = new Date(issuedAt.getTime() + expirationTime);
    byte[] secretKeyBytes = secretKey.getBytes();
    String jwt = generateBearerJwt(userId, claims, issuedAt, expiration, secretKeyBytes);
    List<String> scopes =
        user.getScopes().stream().map(Scope::getValue).collect(Collectors.toList());
    List<String> roles = user.getRoles().stream().map(Role::getValue).collect(Collectors.toList());
    return new JwtWrapper(jwt, issuedAt, expiration, userId, roles, scopes);
  }

  public static JwtWrapper getValues(String jwt, String secretKey) throws InvalidTokenException {
    try {
      Claims claims = getClaims(jwt, secretKey);
      String userId = getUserId(claims);
      Date issuedAt = getIssuedAt(claims);
      Date expiration = getExpiration(claims);
      List<String> roles = getRoles(claims);
      List<String> scopes = getScopes(claims);
      return new JwtWrapper(jwt, issuedAt, expiration, userId, roles, scopes);
    } catch (Exception exception) {
      throw new InvalidTokenException("invalid jwt: " + exception.getMessage());
    }
  }

  private static Map<String, Object> generateJwtClaims(User user) {
    String userId = user.getId();
    var scopes = user.getScopes().stream().map(Scope::getValue).collect(Collectors.toList());
    var roles = user.getRoles().stream().map(Role::getValue).collect(Collectors.toList());
    return generateJwtClaims(userId, scopes, roles);
  }

  private static Map<String, Object> generateJwtClaims(
      String userId, List<String> scopes, List<String> roles) {
    HashMap<String, Object> map = new HashMap<>();
    map.put("user", userId);
    map.put("scopes", scopes);
    map.put("roles", roles);
    return map;
  }

  private static String generateBearerJwt(
      String userId, Map<String, Object> claims, Date issuedDate, Date expirationDate, byte[] key) {
    String jwtId = UUID.randomUUID().toString();
    return Jwts.builder()
        .setId(jwtId)
        .setSubject(userId)
        .setClaims(claims)
        .setIssuedAt(issuedDate)
        .setExpiration(expirationDate)
        .signWith(SignatureAlgorithm.HS512, key)
        .compact();
  }

  private static Claims getClaims(String token, String secretKey) {
    return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody();
  }

  private static List<String> getRoles(Claims claims) {
    ObjectMapper mapper = new ObjectMapper();
    Object roles = claims.get("roles");
    return mapper.convertValue(roles, new TypeReference<>() {});
  }

  private static List<String> getScopes(Claims claims) {
    ObjectMapper mapper = new ObjectMapper();
    Object scopes = claims.get("scopes");
    return mapper.convertValue(scopes, new TypeReference<>() {});
  }

  private static String getUserId(Claims claims) {
    Object userId = claims.get("user");
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
    private String token;
    private Date IssuedAt;
    private Date expiration;
    private String userId;
    private List<String> roles;
    private List<String> scopes;
  }
}

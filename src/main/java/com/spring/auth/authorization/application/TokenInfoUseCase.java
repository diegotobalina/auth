package com.spring.auth.authorization.application;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.authorization.application.ports.in.TokenInfoPort;
import com.spring.auth.authorization.domain.TokenInfo;
import com.spring.auth.exceptions.application.GoogleGetInfoException;
import com.spring.auth.exceptions.application.InvalidTokenException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.exceptions.application.UnknownTokenFormatException;
import com.spring.auth.google.application.ports.out.GoogleGetInfoPort;
import com.spring.auth.session.application.ports.out.FindSessionByTokenPort;
import com.spring.auth.session.domain.Session;
import com.spring.auth.util.RegexUtil;
import com.spring.auth.util.TokenUtil;
import com.spring.auth.util.TokenUtil.JwtWrapper;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;

@UseCase
public class TokenInfoUseCase implements TokenInfoPort {

  @Value("${server.auth.secret-key}")
  private String secretKey;

  private FindSessionByTokenPort findSessionByTokenPort;
  private GoogleGetInfoPort googleGetInfoPort;

  public TokenInfoUseCase(
      FindSessionByTokenPort findSessionByTokenPort, GoogleGetInfoPort googleGetInfoPort) {
    this.findSessionByTokenPort = findSessionByTokenPort;
    this.googleGetInfoPort = googleGetInfoPort;
  }

  @Override
  public TokenInfo tokenInfo(final String token)
      throws NotFoundException, UnknownTokenFormatException, InvalidTokenException,
          GeneralSecurityException, IOException, GoogleGetInfoException {

    if (RegexUtil.isSessionToken(token)) {
      return getSessionTokenInfo(token);
    }

    if (RegexUtil.isBearerJwt(token)) {
      String tokenWithoutPrefix = TokenUtil.removeBearerPrefix(token);
      return getBearerJwtTokenInfo(tokenWithoutPrefix);
    }

    if (RegexUtil.isGoogleJwt(token)) {
      String tokenWithoutPrefix = TokenUtil.removeGooglePrefix(token);
      return getGoogleTokenInfo(tokenWithoutPrefix);
    }

    throw new UnknownTokenFormatException("unknown token format for token: " + token);
  }

  private TokenInfo getGoogleTokenInfo(String token)
      throws GeneralSecurityException, IOException, GoogleGetInfoException {
    GoogleIdToken.Payload payload = googleGetInfoPort.get(token);
    Date issuedAt = new Date(payload.getIssuedAtTimeSeconds() * 1000);
    long payloadExpiration = payload.getExpirationTimeSeconds() * 1000;
    Date expiration = new Date(System.currentTimeMillis() + payloadExpiration);
    String userId = payload.getEmail();
    String googleTokenWithPrefix = TokenUtil.addGooglePrefix(token);
    return new TokenInfo(googleTokenWithPrefix, issuedAt, expiration, userId);
  }

  private TokenInfo getBearerJwtTokenInfo(String token) throws InvalidTokenException {
    JwtWrapper jwtWrapper = TokenUtil.getValues(token, secretKey);
    Date issuedAt = jwtWrapper.getIssuedAt();
    Date expiration = jwtWrapper.getExpiration();
    String userId = jwtWrapper.getUserId();
    List<String> roles = jwtWrapper.getRoles();
    List<String> scopes = jwtWrapper.getScopes();
    String tokenWithPrefix = TokenUtil.addBearerPrefix(token);
    return new TokenInfo(tokenWithPrefix, issuedAt, expiration, userId, roles, scopes);
  }

  private TokenInfo getSessionTokenInfo(String token) throws NotFoundException {
    Session session = findSessionByTokenPort.find(token);
    Date issuedAt = session.getIssuedAt();
    Date expiration = session.getExpiration();
    String userId = session.getUserId();
    String tokenWithPrefix = TokenUtil.addBearerPrefix(token);
    return new TokenInfo(tokenWithPrefix, issuedAt, expiration, userId);
  }
}

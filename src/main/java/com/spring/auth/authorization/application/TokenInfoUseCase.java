package com.spring.auth.authorization.application;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.authorization.application.ports.in.TokenInfoPort;
import com.spring.auth.authorization.domain.TokenInfo;
import com.spring.auth.exceptions.application.InvalidTokenException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.exceptions.application.UnknownTokenFormatException;
import com.spring.auth.google.application.ports.out.GoogleGetInfoPort;
import com.spring.auth.session.application.ports.out.FindSessionByTokenPort;
import com.spring.auth.session.domain.Session;
import com.spring.auth.shared.util.RegexUtil;
import com.spring.auth.shared.util.TokenUtil;
import com.spring.auth.shared.util.TokenUtil.JwtWrapper;
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
          GeneralSecurityException, IOException {

    if (RegexUtil.isSessionToken(token)) {
      return getSessionTokenInfo(token);
    }

    if (RegexUtil.isBearerJwt(token)) {
      String tokenWithoutPrefix = TokenUtil.removeBearerPrefix(token);
      return getJwtTokenInfo(tokenWithoutPrefix);
    }

    if (RegexUtil.isGoogleJwt(token)) {
      String tokenWithoutPrefix = TokenUtil.removeGooglePrefix(token);
      return getGoogleTokenInfo(tokenWithoutPrefix);
    }

    throw new UnknownTokenFormatException("unknown token format for token: " + token);
  }

  private TokenInfo getGoogleTokenInfo(String token) throws GeneralSecurityException, IOException {
    final GoogleIdToken.Payload payload = googleGetInfoPort.get(token);
    final Date issuedAt = new Date(payload.getIssuedAtTimeSeconds() * 1000);
    final Date expiration =
        new Date(System.currentTimeMillis() + (payload.getExpirationTimeSeconds() + 1000));
    final String userId = payload.getEmail();
    return new TokenInfo(TokenUtil.addGooglePrefix(token), issuedAt, expiration, userId);
  }

  private TokenInfo getJwtTokenInfo(String token) throws InvalidTokenException {
    final JwtWrapper jwtWrapper = TokenUtil.getValues(token, secretKey);
    final Date issuedAt = jwtWrapper.getIssuedAt();
    final Date expiration = jwtWrapper.getExpiration();
    final String userId = jwtWrapper.getUserId();
    final List<String> roles = jwtWrapper.getRoles();
    final List<String> scopes = jwtWrapper.getScopes();
    return new TokenInfo(
        TokenUtil.addBearerPrefix(token), issuedAt, expiration, userId, roles, scopes);
  }

  private TokenInfo getSessionTokenInfo(String token) throws NotFoundException {
    final Session session = findSessionByTokenPort.find(token);
    final Date issuedAt = session.getIssuedAt();
    final Date expiration = session.getExpiration();
    final String userId = session.getUserId();
    return new TokenInfo(TokenUtil.addBearerPrefix(token), issuedAt, expiration, userId);
  }
}

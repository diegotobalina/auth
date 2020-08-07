package com.spring.auth.authorization.application;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.authorization.application.ports.in.TokenInfoPort;
import com.spring.auth.authorization.domain.TokenInfo;
import com.spring.auth.client.application.ports.out.FindClientPort;
import com.spring.auth.client.domain.Client;
import com.spring.auth.exceptions.application.*;
import com.spring.auth.google.application.ports.in.GoogleLoginPort;
import com.spring.auth.google.application.ports.out.GoogleGetInfoPort;
import com.spring.auth.session.application.ports.out.FindSessionPort;
import com.spring.auth.session.domain.Session;
import com.spring.auth.user.domain.User;
import com.spring.auth.util.RegexUtil;
import com.spring.auth.util.TokenUtil;
import com.spring.auth.util.TokenUtil.JwtWrapper;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
@UseCase
public class TokenInfoUseCase implements TokenInfoPort {

  @Value("${server.auth.secret-key}")
  private String secretKey;

  private FindSessionPort findSessionPort;
  private GoogleGetInfoPort googleGetInfoPort;
  private FindClientPort findClientPort;
  private GoogleLoginPort googleLoginPort;

  public TokenInfoUseCase(
      FindSessionPort findSessionPort,
      GoogleGetInfoPort googleGetInfoPort,
      FindClientPort findClientPort,
      GoogleLoginPort googleLoginPort) {
    this.findSessionPort = findSessionPort;
    this.googleGetInfoPort = googleGetInfoPort;
    this.findClientPort = findClientPort;
    this.googleLoginPort = googleLoginPort;
  }

  @Override
  public TokenInfo tokenInfo(final String token, String clientId)
      throws NotFoundException, UnknownTokenFormatException, InvalidTokenException,
          GeneralSecurityException, IOException, GoogleGetInfoException,
          EmailDoesNotExistsException, LockedUserException, DuplicatedKeyException,
          InfiniteLoopException {

    // using client_id
    if (clientId != null && !clientId.isEmpty()) { // client_id
      Client client = findClientPort.findByClientId(clientId);
      if (RegexUtil.isBearerJwt(token)) {
        return getBearerJwtTokenInfo(token, client.getClientSecret());
      }

      if (RegexUtil.isGoogleJwt(token)) {
        return getGoogleTokenInfo(token, client.getGoogleClientId());
      }
    }

    // not using client_id
    if (RegexUtil.isSessionToken(token)) {
      return getSessionTokenInfo(token);
    }

    if (RegexUtil.isBearerJwt(token)) {
      return getBearerJwtTokenInfo(token);
    }

    if (RegexUtil.isGoogleJwt(token)) {
      return getGoogleTokenInfo(token);
    }

    throw new UnknownTokenFormatException("unknown token format for token: " + token);
  }

  private TokenInfo getGoogleTokenInfo(String token)
      throws GeneralSecurityException, IOException, GoogleGetInfoException,
          EmailDoesNotExistsException, LockedUserException, InfiniteLoopException,
          NotFoundException, DuplicatedKeyException {
    String tokenWithoutPrefix = TokenUtil.removeGooglePrefix(token);
    GoogleIdToken.Payload payload = googleGetInfoPort.get(tokenWithoutPrefix);
    return getGooglePayloadInfo(token, payload);
  }

  private TokenInfo getGoogleTokenInfo(String token, String googleClientid)
      throws GeneralSecurityException, IOException, GoogleGetInfoException,
          EmailDoesNotExistsException, LockedUserException, InfiniteLoopException,
          NotFoundException, DuplicatedKeyException {
    String tokenWithoutPrefix = TokenUtil.removeGooglePrefix(token);
    GoogleIdToken.Payload payload = googleGetInfoPort.get(tokenWithoutPrefix, googleClientid);
    return getGooglePayloadInfo(token, payload);
  }

  private TokenInfo getGooglePayloadInfo(String token, GoogleIdToken.Payload payload)
      throws NotFoundException, LockedUserException, EmailDoesNotExistsException,
          DuplicatedKeyException, InfiniteLoopException {
    User user = googleLoginPort.login(payload);
    Date issuedAt = new Date(payload.getIssuedAtTimeSeconds() * 1000);
    Date expiration = new Date(payload.getExpirationTimeSeconds() * 1000);
    String userId = user.getId();
    String googleTokenWithPrefix = TokenUtil.addGooglePrefix(token);
    return new TokenInfo(googleTokenWithPrefix, issuedAt, expiration, userId);
  }

  private TokenInfo getBearerJwtTokenInfo(String token) throws InvalidTokenException {
    return getBearerJwtTokenInfo(token, this.secretKey);
  }

  private TokenInfo getBearerJwtTokenInfo(String token, String secretKey)
      throws InvalidTokenException {
    String tokenWithoutPrefix = TokenUtil.removeBearerPrefix(token);
    JwtWrapper jwtWrapper = TokenUtil.getValues(tokenWithoutPrefix, secretKey);
    Date issuedAt = jwtWrapper.getIssuedAt();
    Date expiration = jwtWrapper.getExpiration();
    String userId = jwtWrapper.getUserId();
    List<String> roles = jwtWrapper.getRoles();
    List<String> scopes = jwtWrapper.getScopes();
    return new TokenInfo(token, issuedAt, expiration, userId, roles, scopes);
  }

  private TokenInfo getSessionTokenInfo(String token) throws NotFoundException {
    String tokenWithoutPrefix = TokenUtil.removeBearerPrefix(token);
    Session session = findSessionPort.findByToken(tokenWithoutPrefix);
    Date issuedAt = session.getIssuedAt();
    Date expiration = session.getExpiration();
    String userId = session.getUserId();
    String tokenWithPrefix = TokenUtil.addBearerPrefix(token);
    return new TokenInfo(tokenWithPrefix, issuedAt, expiration, userId);
  }
}

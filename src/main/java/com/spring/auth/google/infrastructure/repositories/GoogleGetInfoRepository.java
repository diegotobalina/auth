package com.spring.auth.google.infrastructure.repositories;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.spring.auth.google.application.ports.out.GoogleGetInfoPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Objects;

@Slf4j
@Repository
public class GoogleGetInfoRepository implements GoogleGetInfoPort {

  @Value("${google.oauth2.client_id}")
  private String googleClientId;

  private NetHttpTransport transport;
  private JacksonFactory jsonFactory;

  @Override
  public Payload get(final String jwt) throws GeneralSecurityException, IOException {
    // instance only the first time
    if (Objects.isNull(transport)) transport = GoogleNetHttpTransport.newTrustedTransport();
    if (Objects.isNull(jsonFactory)) jsonFactory = JacksonFactory.getDefaultInstance();
    // verify google jwt
    final GoogleIdTokenVerifier verifier = getVerifier();
    final GoogleIdToken idToken = verifier.verify(jwt);
    return (idToken == null) ? null : idToken.getPayload();
  }

  private GoogleIdTokenVerifier getVerifier() {
    return new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
        .setAudience(Collections.singletonList(googleClientId))
        .build();
  }
}

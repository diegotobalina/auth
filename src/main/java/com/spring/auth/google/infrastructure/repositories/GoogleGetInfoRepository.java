package com.spring.auth.google.infrastructure.repositories;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.spring.auth.exceptions.application.GoogleGetInfoException;
import com.spring.auth.google.application.ports.out.GoogleGetInfoPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Objects;

/** @author diegotobalina created on 24/06/2020 */
@Slf4j
@Repository
@RefreshScope
public class GoogleGetInfoRepository implements GoogleGetInfoPort {

  @Value("${google.oauth2.client_id}")
  private String googleClientId;

  private NetHttpTransport transport;
  private JacksonFactory jsonFactory;

  @Override
  public Payload get(String jwt)
      throws GeneralSecurityException, IOException, GoogleGetInfoException {

    instanceSingleton();
    GoogleIdTokenVerifier verifier = getVerifier();

    GoogleIdToken idToken = verifier.verify(jwt);
    if (idToken == null) throw new GoogleGetInfoException("google failed login");

    return idToken.getPayload();
  }

  private void instanceSingleton() throws GeneralSecurityException, IOException {
    if (Objects.isNull(transport)) transport = GoogleNetHttpTransport.newTrustedTransport();
    if (Objects.isNull(jsonFactory)) jsonFactory = JacksonFactory.getDefaultInstance();
  }

  private GoogleIdTokenVerifier getVerifier() {
    return new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
        .setAudience(Collections.singletonList(googleClientId))
        .build();
  }
}

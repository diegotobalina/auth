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
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/** @author diegotobalina created on 24/06/2020 */
@Slf4j
@Repository
public class GoogleGetInfoRepository implements GoogleGetInfoPort {

  @Value("${google.oauth2.client_id}")
  private String googleClientId;

  Map<String, NetHttpTransport> transportHashMap = new HashMap<>();
  Map<String, JacksonFactory> jacksonFactoryMap = new HashMap<>();

  @Override
  public Payload get(String jwt)
      throws GeneralSecurityException, IOException, GoogleGetInfoException {
    return this.get(jwt, this.googleClientId);
  }

  @Override
  public Payload get(String jwt, String googleClientId)
      throws GeneralSecurityException, IOException, GoogleGetInfoException {
    if (googleClientId == null || googleClientId.isEmpty())
      googleClientId = this.googleClientId; // if empty google

    instanceSingleton(googleClientId);
    GoogleIdTokenVerifier verifier = getVerifier(googleClientId);

    GoogleIdToken idToken = verifier.verify(jwt);
    if (idToken == null) throw new GoogleGetInfoException("google failed login");

    return idToken.getPayload();
  }

  private void instanceSingleton(String googleClientId)
      throws GeneralSecurityException, IOException {
    if (!transportHashMap.containsKey(googleClientId))
      transportHashMap.put(googleClientId, GoogleNetHttpTransport.newTrustedTransport());
    if (!jacksonFactoryMap.containsKey(googleClientId))
      jacksonFactoryMap.put(googleClientId, JacksonFactory.getDefaultInstance());
  }

  private GoogleIdTokenVerifier getVerifier(String googleClientId) {
    return new GoogleIdTokenVerifier.Builder(
            transportHashMap.get(googleClientId), jacksonFactoryMap.get(googleClientId))
        .setAudience(Collections.singletonList(googleClientId))
        .build();
  }
}

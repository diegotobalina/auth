package com.spring.auth.google.application.ports.out;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.spring.auth.exceptions.application.GoogleGetInfoException;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface GoogleGetInfoPort {
  GoogleIdToken.Payload get(String jwt) throws GeneralSecurityException, IOException, GoogleGetInfoException;
}

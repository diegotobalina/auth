package com.spring.auth.google.infrastructure.repositories.ports;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.spring.auth.exceptions.application.GoogleGetInfoException;

import java.io.IOException;
import java.security.GeneralSecurityException;

/** @author diegotobalina created on 24/06/2020 */
public interface GoogleGetInfoPort {
  GoogleIdToken.Payload get(String jwt) throws GeneralSecurityException, IOException, GoogleGetInfoException;
  GoogleIdToken.Payload get(String jwt,String googleClientId) throws GeneralSecurityException, IOException, GoogleGetInfoException;

}

package com.spring.auth.shared.application.port;

import java.security.GeneralSecurityException;

/** @author diegotobalina created on 04/08/2020 */
public interface EncryptionPort {

  String encrypt(String string) throws GeneralSecurityException;

  String decrypt(String string) throws GeneralSecurityException;
}

package com.spring.auth.util;

import com.google.api.client.util.Base64;
import com.google.crypto.tink.subtle.AesGcmJce;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

/** @author diegotobalina created on 24/06/2020 */
public interface StringUtil {
  static String removeRowJumps(String string) {
    if (StringUtils.isBlank(string)) return null;
    return string.replace("\n", "").replace("\r", "");
  }

  static String toString(byte[] bytes) {
    return new String(bytes, StandardCharsets.UTF_8);
  }

  static String encrypt(String string, String key, String aad) throws GeneralSecurityException {
    AesGcmJce agjEncryption = new AesGcmJce(key.getBytes());
    byte[] encrypted = agjEncryption.encrypt(string.getBytes(), aad.getBytes());
    byte[] bytes = Base64.encodeBase64(encrypted);
    return toString(bytes);
  }

  static String decrypt(String base64, String key, String aad) throws GeneralSecurityException {
    AesGcmJce agjDecryption = new AesGcmJce(key.getBytes());
    byte[] bytes = Base64.decodeBase64(base64);
    byte[] decrypt = agjDecryption.decrypt(bytes, aad.getBytes());
    return toString(decrypt);
  }
}

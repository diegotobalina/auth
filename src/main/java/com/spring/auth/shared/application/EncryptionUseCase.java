package com.spring.auth.shared.application;

import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.shared.application.port.EncryptionPort;
import com.spring.auth.util.RegexUtil;
import com.spring.auth.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.security.GeneralSecurityException;

/** @author diegotobalina created on 04/08/2020 */
@UseCase
public class EncryptionUseCase implements EncryptionPort {

  @Value("${server.encryption.aad}")
  private String aad;

  @Value("${server.encryption.key}")
  private String key;

  @Override
  public String encrypt(String string) throws GeneralSecurityException {
    if (StringUtils.isBlank(string)) throw new IllegalArgumentException("string must not be blank");
    return StringUtil.encrypt(string, key, aad);
  }

  @Override
  public String decrypt(String string) throws GeneralSecurityException {
    if (StringUtils.isBlank(string)) throw new IllegalArgumentException("string must not be blank");
    if (!RegexUtil.isBase64(string))
      throw new IllegalArgumentException("string must be base64 encoded");
    return StringUtil.decrypt(string, key, aad);
  }
}

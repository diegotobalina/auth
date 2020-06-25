package com.spring.auth.authorization.application.ports.in;

import com.spring.auth.authorization.domain.TokenInfo;
import com.spring.auth.exceptions.application.GoogleGetInfoException;
import com.spring.auth.exceptions.application.InvalidTokenException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.exceptions.application.UnknownTokenFormatException;

import java.io.IOException;
import java.security.GeneralSecurityException;

/** @author diegotobalina created on 24/06/2020 */
public interface TokenInfoPort {
  TokenInfo tokenInfo(String token)
          throws NotFoundException, UnknownTokenFormatException, InvalidTokenException,
          GeneralSecurityException, IOException, GoogleGetInfoException;
}

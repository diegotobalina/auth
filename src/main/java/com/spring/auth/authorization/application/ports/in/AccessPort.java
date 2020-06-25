package com.spring.auth.authorization.application.ports.in;

import com.spring.auth.exceptions.application.InvalidTokenException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.util.TokenUtil;

/** @author diegotobalina created on 24/06/2020 */
public interface AccessPort {
  TokenUtil.JwtWrapper access(String token) throws NotFoundException, InvalidTokenException;
}

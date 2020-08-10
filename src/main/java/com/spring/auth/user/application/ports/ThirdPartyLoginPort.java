package com.spring.auth.user.application.ports;

import com.spring.auth.exceptions.application.*;
import com.spring.auth.user.domain.User;

/** @author diegotobalina created on 24/06/2020 */
public interface ThirdPartyLoginPort {
  User thirdPartyLogin(String email, boolean emailVerified) throws NotFoundException, LockedUserException, EmailDoesNotExistsException, DuplicatedKeyException, InfiniteLoopException;
}

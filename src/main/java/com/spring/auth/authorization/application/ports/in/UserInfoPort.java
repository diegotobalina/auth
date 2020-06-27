package com.spring.auth.authorization.application.ports.in;

import com.spring.auth.exceptions.application.LockedUserException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.user.domain.User;

import java.security.Principal;

/** @author diegotobalina created on 24/06/2020 */
public interface UserInfoPort {
  User userInfo(Principal principal) throws NotFoundException, LockedUserException;
}

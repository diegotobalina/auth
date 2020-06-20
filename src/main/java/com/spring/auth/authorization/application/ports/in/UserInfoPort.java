package com.spring.auth.authorization.application.ports.in;

import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.user.domain.User;

import java.security.Principal;

public interface UserInfoPort {
  User userInfo(Principal principal) throws NotFoundException;
}

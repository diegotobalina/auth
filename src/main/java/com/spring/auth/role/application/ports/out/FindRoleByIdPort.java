package com.spring.auth.role.application.ports.out;

import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.role.domain.Role;

public interface FindRoleByIdPort {
  Role find(String id) throws NotFoundException;
}

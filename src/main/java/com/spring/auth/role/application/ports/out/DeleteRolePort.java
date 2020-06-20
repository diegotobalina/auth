package com.spring.auth.role.application.ports.out;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.role.domain.Role;

public interface DeleteRolePort {
  Role delete(Role role) throws DuplicatedKeyException;

  Role delete(String roleId) throws NotFoundException, DuplicatedKeyException;
}

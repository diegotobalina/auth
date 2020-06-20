package com.spring.auth.role.application.ports.in;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.role.domain.Role;

import java.util.List;

public interface AddScopesToRolePort {
  Role add(Role role, List<String> scopeIds) throws DuplicatedKeyException;

  Role add(String roleId, List<String> scopeIds) throws NotFoundException, DuplicatedKeyException;
}

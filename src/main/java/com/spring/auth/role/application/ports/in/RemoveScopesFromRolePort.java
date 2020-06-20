package com.spring.auth.role.application.ports.in;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.role.domain.Role;

import java.util.List;

public interface RemoveScopesFromRolePort {
  Role remove(Role role, List<String> scopes) throws DuplicatedKeyException;

  Role remove(String roleId, List<String> scopes) throws DuplicatedKeyException, NotFoundException;
}

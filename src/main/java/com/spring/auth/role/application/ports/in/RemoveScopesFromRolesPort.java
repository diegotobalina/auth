package com.spring.auth.role.application.ports.in;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.domain.Role;

import java.util.List;

public interface RemoveScopesFromRolesPort {
  List<Role> remove(List<Role> roles, List<String> scopes) throws DuplicatedKeyException;
}

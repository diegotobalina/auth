package com.spring.auth.role.application.ports;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.role.domain.Role;

import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
public interface RemoveScopesFromRolePort {
  Role remove(Role role, List<String> scopes) throws DuplicatedKeyException;

  Role remove(String roleId, List<String> scopes) throws DuplicatedKeyException, NotFoundException;

  List<Role> removeFromAll(String scopeId) throws DuplicatedKeyException;

  List<Role> removeFromAll(List<Role> roles, List<String> scopes) throws DuplicatedKeyException;
}

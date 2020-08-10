package com.spring.auth.role.infrastructure.repositories.ports;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.domain.Role;

import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
public interface UpdateRolePort {
  Role update(Role role) throws DuplicatedKeyException;
  List<Role> updateAll(List<Role> roles) throws DuplicatedKeyException;
}

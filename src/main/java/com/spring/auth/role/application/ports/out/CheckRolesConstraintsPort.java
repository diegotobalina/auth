package com.spring.auth.role.application.ports.out;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.domain.Role;

import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
public interface CheckRolesConstraintsPort {
  void check(List<Role> roles) throws DuplicatedKeyException;
}

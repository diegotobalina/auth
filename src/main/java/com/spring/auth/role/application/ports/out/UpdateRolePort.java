package com.spring.auth.role.application.ports.out;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.domain.Role;

/** @author diegotobalina created on 24/06/2020 */
public interface UpdateRolePort {
  Role update(Role role) throws DuplicatedKeyException;
}

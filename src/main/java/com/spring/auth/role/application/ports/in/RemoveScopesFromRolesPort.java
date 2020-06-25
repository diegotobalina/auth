package com.spring.auth.role.application.ports.in;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.domain.Role;

import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
public interface RemoveScopesFromRolesPort {
  List<Role> remove(List<Role> roles, List<String> scopes) throws DuplicatedKeyException;
}

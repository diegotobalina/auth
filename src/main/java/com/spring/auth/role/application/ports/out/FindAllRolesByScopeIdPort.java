package com.spring.auth.role.application.ports.out;

import com.spring.auth.role.domain.Role;

import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
public interface FindAllRolesByScopeIdPort {
  List<Role> findAll(String scopeId);
}

package com.spring.auth.role.application.ports.out;

import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.role.domain.Role;

import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
public interface FindRolePort {

  List<Role> findAll();

  List<Role> findAllByIds(List<String> roleIds);

  List<Role> findAllByScopeId(String scopeId);

  Role findById(String roleId) throws NotFoundException;

  Role findByValue(String value) throws NotFoundException;
}

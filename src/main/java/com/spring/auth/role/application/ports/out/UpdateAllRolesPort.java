package com.spring.auth.role.application.ports.out;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.domain.Role;

import java.util.List;

public interface UpdateAllRolesPort {
  List<Role> updateAll(List<Role> roles) throws DuplicatedKeyException;
}

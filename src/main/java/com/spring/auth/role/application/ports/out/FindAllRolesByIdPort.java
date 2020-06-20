package com.spring.auth.role.application.ports.out;

import com.spring.auth.role.domain.Role;

import java.util.List;

public interface FindAllRolesByIdPort {
  List<Role> findAll(List<String> roleIds);
}

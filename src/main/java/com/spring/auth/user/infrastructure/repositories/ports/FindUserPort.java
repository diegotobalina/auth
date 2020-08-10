package com.spring.auth.user.infrastructure.repositories.ports;

import com.spring.auth.exceptions.application.InfiniteLoopException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.user.domain.User;
import org.springframework.data.domain.Page;

import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
public interface FindUserPort {

  Page<User> search(String id,String username, String email, int page, int size);

  List<User> findAll();

  List<User> findAllByRoleId(String roleId);

  List<User> findAllByRoleIds(List<String> roleIds);

  List<User> findAllByScopeId(String scopeId);

  User findByEmail(String email) throws NotFoundException;

  User findByUsername(String username) throws NotFoundException;

  User findById(String id) throws NotFoundException;

  User findByUsernameOrEmail(String username, String email) throws NotFoundException;

  String findAvailableUsername(String email) throws InfiniteLoopException;
}

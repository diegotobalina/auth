package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.exceptions.application.InfiniteLoopException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.user.infrastructure.repositories.ports.FindUserPort;
import com.spring.auth.user.domain.User;
import com.spring.auth.user.domain.UserJpa;
import com.spring.auth.user.domain.UserMapper;
import com.spring.auth.user.infrastructure.repositories.jpa.UserRepositoryJpa;
import com.spring.auth.util.SearchUtil;
import com.spring.auth.util.UserUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/** @author diegotobalina created on 24/06/2020 */
@Repository
@AllArgsConstructor
public class FindUserRepository implements FindUserPort {

  private UserRepositoryJpa userRepositoryJpa;

  @Override
  public Page<User> search(String id, String username, String email, int page, int size) {
    UserJpa userJpa = new UserJpa();
    userJpa.setId(id);
    userJpa.setUsername(username);
    userJpa.setEmail(email);

    List<String> wantedFields = List.of("id","username", "email");

    Example<UserJpa> example = (Example<UserJpa>) SearchUtil.getExample(userJpa, wantedFields);
    PageRequest pageRequest = PageRequest.of(page, size);
    Page<UserJpa> pagedResult = userRepositoryJpa.findAll(example, pageRequest);

    return new PageImpl<>(
        UserMapper.parseUserJpaList(pagedResult.getContent()),
        pageRequest,
        pagedResult.getTotalElements());
  }

  @Override
  public List<User> findAll() {
    List<UserJpa> userJpaList = userRepositoryJpa.findAll();
    return UserMapper.parseUserJpaList(userJpaList);
  }

  @Override
  public List<User> findAllByRoleId(String roleId) {
    List<UserJpa> userJpaList = userRepositoryJpa.findAllByRolesId(roleId);
    return UserMapper.parseUserJpaList(userJpaList);
  }

  @Override
  public List<User> findAllByRoleIds(List<String> roleIds) {
    List<UserJpa> userJpaList = userRepositoryJpa.findAllByRolesIdIn(roleIds);
    return UserMapper.parseUserJpaList(userJpaList);
  }

  @Override
  public List<User> findAllByScopeId(String scopeId) {
    List<UserJpa> userJpaList = userRepositoryJpa.findAllByScopesId(scopeId);
    return UserMapper.parseUserJpaList(userJpaList);
  }

  @Override
  public User findByEmail(String email) throws NotFoundException {
    Optional<UserJpa> optional = userRepositoryJpa.findByEmail(email);
    UserJpa userJpa =
        optional.orElseThrow(() -> new NotFoundException("user not found by email: " + email));
    return UserMapper.parse(userJpa);
  }

  @Override
  public User findByUsername(String username) throws NotFoundException {
    Optional<UserJpa> optional = userRepositoryJpa.findByUsername(username);
    UserJpa userJpa =
        optional.orElseThrow(
            () -> new NotFoundException("user not found by username: " + username));
    return UserMapper.parse(userJpa);
  }

  @Override
  public User findById(String id) throws NotFoundException {
    Optional<UserJpa> optional = userRepositoryJpa.findById(id);
    UserJpa userJpa =
        optional.orElseThrow(() -> new NotFoundException("user not found by id: " + id));
    return UserMapper.parse(userJpa);
  }

  @Override
  public User findByUsernameOrEmail(String username, String email) throws NotFoundException {
    Optional<UserJpa> optional = userRepositoryJpa.findByUsernameOrEmail(username, email);
    UserJpa userJpa =
        optional.orElseThrow(
            () ->
                new NotFoundException(
                    "user not found by username: " + username + ", or email: " + email));
    return UserMapper.parse(userJpa);
  }

  @Override
  public String findAvailableUsername(String email) throws InfiniteLoopException {
    for (int i = 0; i < 10; i++) { // loop for limited attempts
      final String username = UserUtil.generateUsername(email);
      if (isAvailable(username)) return username;
    }
    throw new InfiniteLoopException("can not find an empty username");
  }

  private boolean isAvailable(final String username) {
    return !userRepositoryJpa.existsByUsername(username);
  }
}

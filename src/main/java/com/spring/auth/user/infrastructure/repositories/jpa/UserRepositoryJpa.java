package com.spring.auth.user.infrastructure.repositories.jpa;

import com.spring.auth.user.domain.UserJpa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/** Database operations for the UserJpa */
@Repository
public interface UserRepositoryJpa extends MongoRepository<UserJpa, String> {

  /** Finds a user by the username */
  Optional<UserJpa> findByUsername(String username);

  /** Finds a user by the email */
  Optional<UserJpa> findByEmail(String email);

  /** Finds all the users that have the role by the roleId */
  List<UserJpa> findAllByRolesId(String roleId);

  /** Finds all the users that have the role by the roleId list */
  List<UserJpa> findAllByRolesIdIn(List<String> roleIds);

  /** Finds all the users that have the scope by the scopeId */
  List<UserJpa> findAllByScopesId(String scopeId);

  /** Check if the users exists in the database using the username */
  boolean existsByUsername(String username);

  /** Check if the users exists in the database using the email */
  boolean existsByEmail(String email);

  /** Check if the users exists in the database using the username and has not the same id */
  boolean existsByUsernameAndIdNot(String username, String id);

  /** Check if the users exists in the database using the email and has not the same id */
  boolean existsByEmailAndIdNot(String email, String id);

  /** Check if the users exists in the database using the username and has not the same id */
  boolean existsByUsernameInAndIdNotIn(List<String> usernames, List<String> ids);

  /** Check if the users exists in the database using the email and has not the same id */
  boolean existsByEmailInAndIdNotIn(List<String> emails, List<String> ids);
}

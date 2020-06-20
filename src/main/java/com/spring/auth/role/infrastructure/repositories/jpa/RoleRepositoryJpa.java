package com.spring.auth.role.infrastructure.repositories.jpa;

import com.spring.auth.role.domain.RoleJpa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepositoryJpa extends MongoRepository<RoleJpa, String> {
  Optional<RoleJpa> findByValue(String value);

  List<RoleJpa> findAllByScopesId(String scopeId);

  List<RoleJpa> findAllByIdIn(List<String> roleIds);

  List<RoleJpa> findAllByValueIn(List<String> value);

  boolean existsByValue(String value);

  boolean existsByValueAndIdNot(String value, String id);

  boolean existsByValueInAndIdNotIn(List<String> values, List<String> ids);
}

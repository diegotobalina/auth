package com.spring.auth.scope.infrastructure.repositories.jpa;

import com.spring.auth.scope.domain.ScopeJpa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScopeRepositoryJpa extends MongoRepository<ScopeJpa, String> {
  Optional<ScopeJpa> findByValue(String value);

  List<ScopeJpa> findAllByIdIn(List<String> ids);

  boolean existsByValue(String value);
}

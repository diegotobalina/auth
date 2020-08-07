package com.spring.auth.client.infrastructure.repositories.jpa;

import com.spring.auth.client.domain.ClientJpa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepositoryJpa extends MongoRepository<ClientJpa, String> {
  boolean existsByClientIdInAndIdNotIn(List<String> clientIds, List<String> ids);

    Optional<ClientJpa> findByClientId(String clientId);
}

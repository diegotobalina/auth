package com.spring.auth.scope.application.ports.out;

import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.scope.domain.Scope;
import org.springframework.data.domain.Page;

import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
public interface FindScopePort {
  Page<Scope> search(String id,String name, String description, String value, int page, int size);

  List<Scope> findAllByIds(List<String> ids);

  List<Scope> findAll();

  Scope findById(String scopeId) throws NotFoundException;

  Scope findByValue(String value) throws NotFoundException;
}

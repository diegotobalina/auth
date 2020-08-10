package com.spring.auth.role.infrastructure.repositories;

import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.role.infrastructure.repositories.ports.FindRolePort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.role.domain.RoleJpa;
import com.spring.auth.role.domain.RoleMapper;
import com.spring.auth.role.infrastructure.repositories.jpa.RoleRepositoryJpa;
import com.spring.auth.util.SearchUtil;
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
public class FindRoleRepository implements FindRolePort {

  private RoleRepositoryJpa roleRepositoryJpa;

  @Override
  public Page<Role> search(String id,String name, String description, String value, int page, int size) {
    RoleJpa roleJpa = new RoleJpa();
    roleJpa.setId(id);
    roleJpa.setName(name);
    roleJpa.setDescription(description);
    roleJpa.setValue(value);

    List<String> wantedFields = List.of("id","name", "description", "value");

    Example<RoleJpa> example = (Example<RoleJpa>) SearchUtil.getExample(roleJpa, wantedFields);
    PageRequest pageRequest = PageRequest.of(page, size);
    Page<RoleJpa> pagedResult = roleRepositoryJpa.findAll(example, pageRequest);

    return new PageImpl<>(
            RoleMapper.parseRoleJpaList(pagedResult.getContent()),
            pageRequest,
            pagedResult.getTotalElements());
  }

  @Override
  public List<Role> findAll() {
    List<RoleJpa> roleJpaList = roleRepositoryJpa.findAll();
    return RoleMapper.parseRoleJpaList(roleJpaList);
  }

  @Override
  public List<Role> findAllByIds(List<String> roleIds) {
    List<RoleJpa> roleJpaList = roleRepositoryJpa.findAllByIdIn(roleIds);
    return RoleMapper.parseRoleJpaList(roleJpaList);
  }

  @Override
  public List<Role> findAllByScopeId(String scopeId) {
    List<RoleJpa> roleJpaList = roleRepositoryJpa.findAllByScopesId(scopeId);
    return RoleMapper.parseRoleJpaList(roleJpaList);
  }

  @Override
  public Role findById(String roleId) throws NotFoundException {
    Optional<RoleJpa> optional = roleRepositoryJpa.findById(roleId);
    RoleJpa roleJpa =
        optional.orElseThrow(() -> new NotFoundException("role not found by id: " + roleId));
    return RoleMapper.parse(roleJpa);
  }

  @Override
  public Role findByValue(String value) throws NotFoundException {
    Optional<RoleJpa> optional = roleRepositoryJpa.findByValue(value);
    RoleJpa roleJpa =
        optional.orElseThrow(() -> new NotFoundException("role not found by value: " + value));
    return RoleMapper.parse(roleJpa);
  }
}

package com.spring.auth.events.ports;

import com.spring.auth.role.domain.Role;

import java.util.List;

/** @author diegotobalina created on 19/06/2020 */
public interface PublishRolesUpdatedEventPort {
  void publish(List<Role> roles);
}

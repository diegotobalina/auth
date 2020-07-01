package com.spring.auth.events.ports;

import com.spring.auth.role.domain.Role;
import com.spring.auth.user.domain.User;

/** @author diegotobalina created on 19/06/2020 */
public interface PublishUpdatedPasswordEventPort {
  void publish(User user);
}

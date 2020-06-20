package com.spring.auth.events.ports;

import com.spring.auth.role.domain.Role;

/**
 * @author diegotobalina
 * created on 19/06/2020
 */
public interface PublishRoleDeletedEventPort {
    void publish(Role role);
}

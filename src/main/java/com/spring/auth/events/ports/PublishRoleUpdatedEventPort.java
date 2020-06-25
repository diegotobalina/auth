package com.spring.auth.events.ports;

import com.spring.auth.role.domain.Role;

/** @author diegotobalina created on 24/06/2020 */
public interface PublishRoleUpdatedEventPort {
    void publish(Role role);
}

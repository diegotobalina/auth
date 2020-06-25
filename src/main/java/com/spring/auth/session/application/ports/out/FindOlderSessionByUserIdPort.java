package com.spring.auth.session.application.ports.out;

import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.session.domain.Session;

/** @author diegotobalina created on 24/06/2020 */
public interface FindOlderSessionByUserIdPort {
  Session find(String userId) throws NotFoundException;
}

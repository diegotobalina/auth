package com.spring.auth.shared.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

/** @author diegotobalina created on 04/08/2020 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class Auditable {
  @CreatedDate protected Date createdAt;
  @LastModifiedDate protected Date lastModified;
  @CreatedBy protected String createdBy;
  @LastModifiedBy protected String lastModifiedBy;
}

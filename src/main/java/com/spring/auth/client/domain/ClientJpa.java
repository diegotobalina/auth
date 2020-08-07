package com.spring.auth.client.domain;

import com.spring.auth.shared.domain.Auditable;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "spring_client")
public class ClientJpa extends Auditable {

  @Setter @Id @Indexed private String id;
  @Setter @Indexed private String clientId = "";
  @Setter private String clientSecret = "";
  private Date creationDate = new Date();
  private List<String> allowedUrls = new ArrayList<>();
  private List<String> allowedCallbackUrls = new ArrayList<>();
  @Setter private long expirationTimeToken = (long) (1000 * 60 * 20); // 1s > 1m > 20m
  @Setter private String googleClientId = "";

  public ClientJpa(
      Date createdAt,
      Date lastModified,
      String createdBy,
      String lastModifiedBy,
      String id,
      String clientId,
      String clientSecret,
      Date creationDate,
      List<String> allowedUrls,
      List<String> allowedCallbackUrls,
      long expirationTimeToken,
      String googleClientId) {
    super(createdAt, lastModified, createdBy, lastModifiedBy);
    this.id = id;
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.creationDate = creationDate;
    this.allowedUrls = allowedUrls;
    this.allowedCallbackUrls = allowedCallbackUrls;
    this.expirationTimeToken = expirationTimeToken;
    this.googleClientId = googleClientId;
  }
}

package com.spring.auth.client.domain;

import com.spring.auth.shared.domain.Auditable;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Client extends Auditable implements Serializable {
  private String id;
  private String clientId = "";
  private String clientSecret = "";
  private Date creationDate = new Date();
  @Setter private List<String> allowedUrls = new ArrayList<>();
  @Setter private List<String> allowedCallbackUrls = new ArrayList<>();
  @Setter private long expirationTimeToken = (long) (1000 * 60 * 20); // 1s > 1m > 20m
  @Setter private String googleClientId = "";

  public Client(
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

  public void addAllowedUrl(String url) {
    if (allowedUrls.contains(url)) return;
    this.allowedUrls.add(url);
  }

  public void addAllowedCallbackUrl(String url) {
    if (allowedCallbackUrls.contains(url)) return;
    this.allowedCallbackUrls.add(url);
  }

  public void removeAllowedUrl(String url) {
    if (!this.allowedUrls.contains(url)) return;
    for (String allowedUrl : allowedUrls) {
      if (allowedUrl.equals(url)) {
        allowedUrls.remove(allowedUrl);
        return;
      }
    }
  }

  public void removeAllowedCallbackUrl(String url) {
    if (!this.allowedCallbackUrls.contains(url)) return;
    for (String allowedCallbackUrl : allowedCallbackUrls) {
      if (allowedCallbackUrl.equals(url)) {
        allowedCallbackUrls.remove(allowedCallbackUrl);
        return;
      }
    }
  }
}

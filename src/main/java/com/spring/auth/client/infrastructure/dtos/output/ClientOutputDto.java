package com.spring.auth.client.infrastructure.dtos.output;

import com.spring.auth.client.domain.Client;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/** @author diegotobalina created on 31/07/2020 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ClientOutputDto {
  private String id;
  private String client_id;
  private String client_secret;
  private Date creation_date;
  private List<String> allowed_urls;
  private List<String> allowed_callback_urls;
  private long expiration_time_token;
  private String google_client_id;

  public ClientOutputDto(Client client) {
    this.id = client.getId();
    this.client_id = client.getClientId();
    this.client_secret = client.getClientSecret();
    this.creation_date = client.getCreationDate();
    this.allowed_urls = client.getAllowedUrls();
    this.allowed_callback_urls = client.getAllowedCallbackUrls();
    this.expiration_time_token = client.getExpirationTimeToken();
    this.google_client_id = client.getGoogleClientId();
  }
}

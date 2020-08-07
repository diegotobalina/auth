package com.spring.auth.client.infrastructure.dtos.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

/** @author diegotobalina created on 31/07/2020 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UpdateClientInputDto {
  @ApiModelProperty(example = "[http://localhost]")
  private List<@URL String> allowedUrls = new ArrayList<>();

  @ApiModelProperty(example = "[http://localhost]")
  private List<@URL String> allowedCallbackUrls = new ArrayList<>();

  @Min(0)
  @Max(86400000)
  private long expirationTokenTime = 0l;

  private String googleClientId = "";
}

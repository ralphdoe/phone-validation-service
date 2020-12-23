package com.verizonmedia.phonevalidationservice.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PhoneNumberResponse {

  private String number;
  private String country;
  private Boolean isValid;

}

package com.verizonmedia.phonevalidationservice.domain.responses;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PhoneNumberNumVerifyResponse {

  private String valid;
  private String number;
  private String local_format;
  private String international_format;
  private String country_prefix;
  private String country_code;
  private String country_name;
  private String location;
  private String carrier;
  private String line_type;

}

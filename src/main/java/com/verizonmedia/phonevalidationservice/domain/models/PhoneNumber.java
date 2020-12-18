package com.verizonmedia.phonevalidationservice.domain.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PhoneNumber {

  private String number;
  private Boolean isValid;
  private Long localFormat;
  private String internationalFormat;
  private String countryName;
  private String countryCode;
  private String countryPrefix;
  private String registeredLocation;
  private String carrier;
  private String lineType;

}

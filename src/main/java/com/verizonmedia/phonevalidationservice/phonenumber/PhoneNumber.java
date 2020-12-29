package com.verizonmedia.phonevalidationservice.phonenumber;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PhoneNumber {

  private String number;
  @JsonProperty(value = "valid")
  private Boolean isValid;
  @JsonProperty(value = "local_format")
  private Long localFormat;
  @JsonProperty(value = "international_format")
  private String internationalFormat;
  @JsonProperty(value = "country_name")
  private String countryName;
  @JsonProperty(value = "country_code")
  private String countryCode;
  @JsonProperty(value = "country_prefix")
  private String countryPrefix;
  @JsonProperty(value = "location")
  private String registeredLocation;
  private String carrier;
  @JsonProperty(value = "line_type")
  private String lineType;

}

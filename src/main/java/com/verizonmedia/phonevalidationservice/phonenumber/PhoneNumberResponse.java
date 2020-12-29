package com.verizonmedia.phonevalidationservice.phonenumber;

import com.googlecode.jmapper.annotations.JMap;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PhoneNumberResponse {

  @JMap("number")
  private String number;
  @JMap("countryName")
  private String country;
  @JMap("isValid")
  private Boolean isValid;
  @JMap("countryPrefix")
  private String countryPrefix;
  @JMap("countryCode")
  private String countryCode;

}

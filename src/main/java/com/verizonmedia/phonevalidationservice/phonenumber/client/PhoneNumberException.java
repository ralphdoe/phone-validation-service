package com.verizonmedia.phonevalidationservice.phonenumber.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class PhoneNumberException extends RuntimeException {

  private String code;
  private String type;
  private String info;

}

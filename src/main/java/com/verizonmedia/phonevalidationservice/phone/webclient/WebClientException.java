package com.verizonmedia.phonevalidationservice.phone.webclient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WebClientException extends Exception {

  private String statusCode;
  private String message;
}

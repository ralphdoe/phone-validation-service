package com.verizonmedia.phonevalidationservice.infrastructure.controllers;

import com.verizonmedia.phonevalidationservice.domain.responses.PhoneNumberResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhoneNumberController {
  @GetMapping("/validate/{number}")
  public ResponseEntity<PhoneNumberResponse> validatePhoneNumber(@PathVariable("number") String number) {
    PhoneNumberResponse phoneNumberResponse = new PhoneNumberResponse();
    phoneNumberResponse.setCountry("United States");
    phoneNumberResponse.setNumber(number);
    phoneNumberResponse.setValid(Boolean.TRUE);
    return ResponseEntity.ok(phoneNumberResponse);

  }


}

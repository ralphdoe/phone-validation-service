package com.verizonmedia.phonevalidationservice.infrastructure.controllers;

import com.verizonmedia.phonevalidationservice.application.PhoneNumberValidationService;
import com.verizonmedia.phonevalidationservice.domain.responses.PhoneNumberResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhoneNumberController {

  @Autowired
  private PhoneNumberValidationService phoneNumberValidationService;

  @GetMapping("/validate/{number}")
  public ResponseEntity<PhoneNumberResponse> validatePhoneNumber(
      @PathVariable("number") String number) {
    PhoneNumberResponse phoneNumberResponse = phoneNumberValidationService
        .validatePhoneNumber(number);
    return ResponseEntity.ok(phoneNumberResponse);

  }


}

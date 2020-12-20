package com.verizonmedia.phonevalidationservice.infrastructure.controllers.implementation;

import com.verizonmedia.phonevalidationservice.application.PhoneNumberValidationService;
import com.verizonmedia.phonevalidationservice.domain.responses.PhoneNumberResponse;
import com.verizonmedia.phonevalidationservice.infrastructure.controllers.PhoneNumberController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhoneNumberRestController implements PhoneNumberController {

  @Autowired
  private PhoneNumberValidationService phoneNumberValidationService;

  @GetMapping("/validate/{number}")
  public ResponseEntity<PhoneNumberResponse> validatePhoneNumber(
      @PathVariable("number") String number) {
    PhoneNumberResponse phoneNumberResponse = phoneNumberValidationService
        .validatePhoneNumber(number);
    if (StringUtils.isEmpty(phoneNumberResponse.getNumber())) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(phoneNumberResponse);
    }
  }
}

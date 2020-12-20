package com.verizonmedia.phonevalidationservice.infrastructure.controllers;

import com.verizonmedia.phonevalidationservice.domain.responses.PhoneNumberResponse;
import org.springframework.http.ResponseEntity;

public interface PhoneNumberController {

  ResponseEntity<PhoneNumberResponse> validatePhoneNumber(String number);
}

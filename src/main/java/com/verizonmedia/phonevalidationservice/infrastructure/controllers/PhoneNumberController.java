package com.verizonmedia.phonevalidationservice.infrastructure.controllers;

import com.verizonmedia.phonevalidationservice.domain.responses.PhoneNumberResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.http.ResponseEntity;

@OpenAPIDefinition(
    info = @Info(title = "Phone Number Validation Service",
        description = "Phone Validation", version = "1.0.0"))
public interface PhoneNumberController {

  @Operation(summary = "You can validate one Phone Number.")
  ResponseEntity<PhoneNumberResponse> validatePhoneNumber(String number);
}

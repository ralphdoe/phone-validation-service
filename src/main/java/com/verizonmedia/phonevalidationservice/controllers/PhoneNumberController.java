package com.verizonmedia.phonevalidationservice.controllers;

import com.verizonmedia.phonevalidationservice.models.PhoneNumberResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import java.util.List;
import org.springframework.http.ResponseEntity;

@OpenAPIDefinition(
    info = @Info(title = "Phone Number Validation Service",
        description = "Phone Validation", version = "1.0.0"))
public interface PhoneNumberController {

  @Operation(summary = "You can validate one Phone Number.")
  ResponseEntity<PhoneNumberResponse> validatePhoneNumber(String number);

  @Operation(summary = "You can validate a list of Phone Numbers.")
  ResponseEntity<List<PhoneNumberResponse>> validatePhoneNumbers(List<String> number);

}

package com.verizonmedia.phonevalidationservice.infrastructure.controllers.implementation;

import com.verizonmedia.phonevalidationservice.application.PhoneNumberValidationService;
import com.verizonmedia.phonevalidationservice.domain.responses.PhoneNumberResponse;
import com.verizonmedia.phonevalidationservice.infrastructure.controllers.PhoneNumberController;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PhoneNumberRestController implements PhoneNumberController {

  private final PhoneNumberValidationService phoneNumberValidationService;

  @GetMapping("/validate/{number}")
  public ResponseEntity<PhoneNumberResponse> validatePhoneNumber(
      @PathVariable("number") String number) {
    Optional<PhoneNumberResponse> phoneNumberResponse = phoneNumberValidationService
        .validatePhoneNumber(number);
    return phoneNumberResponse.map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}

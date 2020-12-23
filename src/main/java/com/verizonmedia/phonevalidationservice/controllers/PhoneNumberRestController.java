package com.verizonmedia.phonevalidationservice.controllers;

import com.verizonmedia.phonevalidationservice.models.PhoneNumberResponse;
import com.verizonmedia.phonevalidationservice.services.PhoneNumberValidationService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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

  @GetMapping("/validate")
  public ResponseEntity<List<PhoneNumberResponse>> validatePhoneNumbers(
      @RequestParam List<String> numbers) {
    List<PhoneNumberResponse> response = phoneNumberValidationService.validatePhoneNumbers(numbers);
    if (response.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(response);
  }
}

package com.verizonmedia.phonevalidationservice.phone;

import com.verizonmedia.phonevalidationservice.phone.client.PhoneNumberException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    try {
      Optional<PhoneNumberResponse> phoneNumberResponse = phoneNumberValidationService
          .validatePhoneNumber(number);
      return phoneNumberResponse.map(ResponseEntity::ok)
          .orElseGet(() -> ResponseEntity.notFound().build());
    } catch (PhoneNumberException ex) {
      return ResponseEntity.status(Integer.parseInt(ex.getStatusCode())).build();
    }
  }

  @GetMapping("/validate")
  public ResponseEntity<List<PhoneNumberResponse>> validatePhoneNumbers(
      @RequestParam Set<String> numbers) {
    List<PhoneNumberResponse> response = phoneNumberValidationService.validatePhoneNumbers(numbers);
    if (response.isEmpty()) {
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}

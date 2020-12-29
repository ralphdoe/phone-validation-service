package com.verizonmedia.phonevalidationservice.phonenumber;

import com.verizonmedia.phonevalidationservice.phonenumber.client.PhoneNumberException;
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
  public ResponseEntity validatePhoneNumber(
      @PathVariable("number") String number) {
    try {
      Optional<PhoneNumberResponse> phoneNumberResponse = phoneNumberValidationService
          .validatePhoneNumber(number);
      if (phoneNumberResponse.isPresent()) {
        return new ResponseEntity<>(phoneNumberResponse.get(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Error Finding Data", HttpStatus.BAD_REQUEST);
      }
    } catch (PhoneNumberException ex) {
      return ResponseEntity.status(Integer.parseInt(ex.getStatusCode())).body(ex.getMessage());
    }
  }

  @GetMapping("/validate")
  public ResponseEntity validatePhoneNumbers(
      @RequestParam Set<String> numbers) {
    List<PhoneNumberResponse> response = phoneNumberValidationService.validatePhoneNumbers(numbers);
    if (response.isEmpty()) {
      return new ResponseEntity<>("Error Finding Data", HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}

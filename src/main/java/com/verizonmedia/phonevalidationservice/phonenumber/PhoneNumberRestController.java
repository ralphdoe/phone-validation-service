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
  public ResponseEntity<PhoneNumberResponse> validatePhoneNumber(
      @PathVariable("number") String number) {
    try {
      Optional<PhoneNumberResponse> phoneNumberResponse = phoneNumberValidationService
          .validatePhoneNumber(number);
      return phoneNumberResponse
          .map(numberResponse -> new ResponseEntity<>(numberResponse, HttpStatus.OK)).orElseGet(
              () -> new ResponseEntity("Error Finding Data In Service", HttpStatus.BAD_REQUEST));
    } catch (PhoneNumberException ex) {
      return new ResponseEntity(ex.getInfo(), HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/validate")
  public ResponseEntity<List<PhoneNumberResponse>> validatePhoneNumbers(
      @RequestParam Set<String> numbers) {
    try {
      List<PhoneNumberResponse> response = phoneNumberValidationService
          .validatePhoneNumbers(numbers);
      if (response.isEmpty()) {
        return new ResponseEntity("Error Finding Data In Service", HttpStatus.BAD_REQUEST);
      }
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (PhoneNumberException ex) {
      return new ResponseEntity(ex.getInfo(), HttpStatus.BAD_REQUEST);

    }


  }
}

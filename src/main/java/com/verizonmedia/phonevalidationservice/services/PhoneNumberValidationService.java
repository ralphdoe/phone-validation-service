package com.verizonmedia.phonevalidationservice.services;

import com.verizonmedia.phonevalidationservice.consumers.PhoneNumberClient;
import com.verizonmedia.phonevalidationservice.repository.PhoneNumberRepository;
import com.verizonmedia.phonevalidationservice.models.PhoneNumber;
import com.verizonmedia.phonevalidationservice.models.PhoneNumberResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Service
public class PhoneNumberValidationService {

  private final PhoneNumberRepository phoneNumberJDBCRepository;
  private final PhoneNumberClient phoneNumberClient;

  public Optional<PhoneNumberResponse> validatePhoneNumber(String number) {
    Optional<PhoneNumber> phoneNumber = phoneNumberJDBCRepository.findByNumber(number);
    if (phoneNumber.isEmpty()) {
      phoneNumber = phoneNumberClient.getPhoneNumber(number);
      if (phoneNumber.isPresent()) {
        PhoneNumber phoneNumberObject = phoneNumber.get();
        if (!StringUtils.isEmpty(phoneNumberObject.getNumber())) {
          phoneNumberJDBCRepository.createPhoneNumber(phoneNumberObject);
          return Optional.of(getPhoneNumberResponseFromPhoneNumber(
              phoneNumberObject));
        }
      }
    } else {
      return Optional.of(getPhoneNumberResponseFromPhoneNumber(phoneNumber.get()));

    }
    return Optional.empty();
  }

  private PhoneNumberResponse getPhoneNumberResponseFromPhoneNumber(PhoneNumber phoneNumberObject) {
    PhoneNumberResponse phoneNumberResponse = new PhoneNumberResponse();
    phoneNumberResponse.setIsValid(phoneNumberObject.getIsValid());
    phoneNumberResponse.setCountry(phoneNumberObject.getCountryName());
    phoneNumberResponse.setNumber(phoneNumberObject.getNumber());
    return phoneNumberResponse;
  }
}

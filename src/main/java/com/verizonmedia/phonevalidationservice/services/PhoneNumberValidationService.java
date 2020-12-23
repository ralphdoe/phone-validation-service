package com.verizonmedia.phonevalidationservice.services;

import com.verizonmedia.phonevalidationservice.consumers.PhoneNumberClient;
import com.verizonmedia.phonevalidationservice.models.PhoneNumber;
import com.verizonmedia.phonevalidationservice.models.PhoneNumberResponse;
import com.verizonmedia.phonevalidationservice.repository.PhoneNumberRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
@Service
public class PhoneNumberValidationService {

  private final Logger logger = LoggerFactory.getLogger(PhoneNumberValidationService.class);

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
          logger.info("Product found in External Service.");
          return Optional.of(getPhoneNumberResponseFromPhoneNumber(
              phoneNumberObject));
        }
      }
    } else {
      logger.info("Product found in Database.");
      return Optional.of(getPhoneNumberResponseFromPhoneNumber(phoneNumber.get()));
    }
    return Optional.empty();
  }

  public List<PhoneNumberResponse> validatePhoneNumbers(List<String> numbers) {
    List<PhoneNumberResponse> response = new ArrayList<>();
    numbers.forEach(phoneNumber -> validatePhoneNumber(phoneNumber).ifPresent(response::add));
    return response;
  }

  private PhoneNumberResponse getPhoneNumberResponseFromPhoneNumber(PhoneNumber phoneNumberObject) {
    PhoneNumberResponse phoneNumberResponse = new PhoneNumberResponse();
    phoneNumberResponse.setIsValid(phoneNumberObject.getIsValid());
    phoneNumberResponse.setCountry(phoneNumberObject.getCountryName());
    phoneNumberResponse.setNumber(phoneNumberObject.getNumber());
    return phoneNumberResponse;
  }
}

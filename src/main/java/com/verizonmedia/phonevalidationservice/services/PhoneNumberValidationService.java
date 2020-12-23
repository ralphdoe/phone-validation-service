package com.verizonmedia.phonevalidationservice.services;

import com.googlecode.jmapper.JMapper;
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

  JMapper<PhoneNumberResponse, PhoneNumber> mapper = new JMapper<>(PhoneNumberResponse.class,
      PhoneNumber.class);

  /**
   * Validates if a Phone Number is Valid or Not, also gets some data from database or a third party
   * service.
   *
   * @param number to search.
   * @return an Optional Response for the endpoint.
   */
  public Optional<PhoneNumberResponse> validatePhoneNumber(String number) {
    Optional<PhoneNumber> phoneNumber = phoneNumberJDBCRepository.findByNumber(number);
    if (phoneNumber.isEmpty()) {
      // If the Phone Number is not in the Database it goes to a third party service to obtain data.
      phoneNumber = phoneNumberClient.getPhoneNumber(number);
      if (phoneNumber.isPresent()) {
        PhoneNumber phoneNumberObject = phoneNumber.get();
        if (!StringUtils.isEmpty(phoneNumberObject.getNumber())) {
          phoneNumberJDBCRepository.createPhoneNumber(phoneNumberObject);
          logger.info("Product found in External Service.");
          return Optional.of(mapper.getDestination(phoneNumberObject));

        }
      }
    } else {
      logger.info("Product found in Database.");
      return Optional.of(mapper.getDestination(phoneNumber.get()));
    }
    return Optional.empty();
  }

  /**
   * Validates if a List of Phone Numbers are Valid or Not, also gets some data from database of a
   * third party service.
   *
   * @param numbers list to search.
   * @return List with Responses. zxlow
   */
  public List<PhoneNumberResponse> validatePhoneNumbers(List<String> numbers) {
    List<PhoneNumberResponse> response = new ArrayList<>();
    numbers.forEach(phoneNumber -> validatePhoneNumber(phoneNumber).ifPresent(response::add));
    return response;
  }
}

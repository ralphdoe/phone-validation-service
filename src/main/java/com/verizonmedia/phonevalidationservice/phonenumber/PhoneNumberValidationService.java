package com.verizonmedia.phonevalidationservice.phonenumber;

import com.googlecode.jmapper.JMapper;
import com.verizonmedia.phonevalidationservice.phonenumber.client.PhoneNumberFeignService;
import com.verizonmedia.phonevalidationservice.phonenumber.client.PhoneNumberException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
@Service
public class PhoneNumberValidationService {

  private final PhoneNumberJDBCRepository phoneNumberJDBCRepository;
  private final PhoneNumberFeignService phoneNumberFeignService;

  JMapper<PhoneNumberResponse, PhoneNumber> mapper = new JMapper<>(PhoneNumberResponse.class,
      PhoneNumber.class);

  /**
   * Validates if a Phone Number is Valid or Not, also gets some data from database or a third party
   * service.
   *
   * @param number to search.
   * @return an Optional Response for the endpoint.
   */
  public Optional<PhoneNumberResponse> validatePhoneNumber(String number)
      throws PhoneNumberException {
    Optional<PhoneNumber> phoneNumber;
    try {
      phoneNumber = phoneNumberJDBCRepository.findByNumber(number);
      if (phoneNumber.isPresent()) {
        return Optional.of(phoneNumber.map(mapper::getDestination).get());
      }
    } catch (EmptyResultDataAccessException exception) {
      log.error("Error searching the data: " + exception.getMessage());
    }

    // If the Phone Number is not in the Database it goes to a third party service to obtain data.
    phoneNumber = phoneNumberFeignService.getPhoneNumber(number);

    if (phoneNumber.isPresent()) {
      PhoneNumber phoneNumberObject = phoneNumber.get();
      if (!StringUtils.isEmpty(phoneNumberObject.getNumber())) {
        phoneNumberJDBCRepository.createPhoneNumber(phoneNumberObject);
        return Optional.of(mapper.getDestination(phoneNumberObject));
      }
    }
    return Optional.empty();
  }

  /**
   * Validates if a List of Phone Numbers are Valid or Not, also gets some data from database of a
   * third party service.
   *
   * @param numbers list to search.
   * @return List with Responses.
   */
  public List<PhoneNumberResponse> validatePhoneNumbers(Set<String> numbers) {
    List<PhoneNumber> phoneNumberList = phoneNumberJDBCRepository.findByListOfNumbers(numbers);
    List<PhoneNumber> phoneNumberBatch = new ArrayList<>();
    for (String number : numbers) {
      if (phoneNumberList.stream().map(PhoneNumber::getNumber).noneMatch(number::equals)) {
        phoneNumberFeignService.getPhoneNumber(number).map(phoneNumberBatch::add);
      }
    }
    if (phoneNumberBatch.size() > 0) {
      phoneNumberJDBCRepository.createPhoneNumbersBatchMode(phoneNumberBatch);
      phoneNumberList.addAll(phoneNumberBatch);
    }
    return phoneNumberList.stream().map(mapper::getDestination).collect(Collectors.toList());
  }
}

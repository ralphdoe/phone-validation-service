package com.verizonmedia.phonevalidationservice.phonenumber.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.verizonmedia.phonevalidationservice.phonenumber.PhoneNumber;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service to connect with Feign Client. It helps us to handle the Communication with Third Party
 * Endpoint.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PhoneNumberFeignService {

  @Value("${numverify.access.token}")
  private String numVerifyToken;

  private final PhoneNumberFeignClient phoneNumberFeignClient;

  /**
   * Get PhoneNumber from FeignClient.
   *
   * @param number to retrieve.
   * @return Optional<PhoneNumber> when the service call works.</PhoneNumber>
   * @throws PhoneNumberException in case there is any problem retrieving the data.
   */
  public Optional<PhoneNumber> getPhoneNumber(String number) throws PhoneNumberException {
    Object response = phoneNumberFeignClient
        .getPhoneNumber(numVerifyToken, number);

    return getPhoneNumberFromFeignResponse((LinkedHashMap) response);
  }

  /**
   * This method is to manage the Feign response, it map the response object and depending if it is
   * a valid result or an error it returns the data or throws an PhoneNumberException.
   *
   * @param response obtained from feign call.
   * @return Optional<PhoneNumber> when the mapping is successful.</PhoneNumber>
   */
  private Optional<PhoneNumber> getPhoneNumberFromFeignResponse(LinkedHashMap response) {
    ObjectMapper mapper = new ObjectMapper();
    Map responseMap = response;
    if (responseMap.containsKey("success") && !((Boolean) responseMap.get("success"))) {
      Map errorMap = (LinkedHashMap) responseMap.get("error");
      PhoneNumberException phoneNumberException = mapper
          .convertValue(errorMap, PhoneNumberException.class);
      log.error("Error calling service");
      throw phoneNumberException;
    } else {
      PhoneNumber phoneNumber = mapper.convertValue(responseMap, PhoneNumber.class);
      log.info("Service call worked successfully.");
      return Optional.ofNullable(phoneNumber);
    }
  }
}

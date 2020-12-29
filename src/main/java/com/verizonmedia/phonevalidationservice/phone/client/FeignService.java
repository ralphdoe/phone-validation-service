package com.verizonmedia.phonevalidationservice.phone.client;

import com.verizonmedia.phonevalidationservice.phone.PhoneNumber;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeignService {

  @Value("${numverify.access.token}")
  private String numVerifyToken;

  private final PhoneNumberFeignClient phoneNumberFeignClient;

  public Optional<PhoneNumber> getPhoneNumber(String number) {
    PhoneNumber phoneNumberObject = phoneNumberFeignClient
        .getPhoneNumber(numVerifyToken, number);
    if (phoneNumberObject != null && StringUtils.isEmpty(phoneNumberObject.getNumber())) {
      return Optional.empty();

    }
    return Optional.ofNullable(phoneNumberObject);
  }

}

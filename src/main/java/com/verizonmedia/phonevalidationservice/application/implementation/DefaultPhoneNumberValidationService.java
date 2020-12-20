package com.verizonmedia.phonevalidationservice.application.implementation;

import com.verizonmedia.phonevalidationservice.application.PhoneNumberValidationService;
import com.verizonmedia.phonevalidationservice.domain.models.PhoneNumber;
import com.verizonmedia.phonevalidationservice.domain.responses.PhoneNumberResponse;
import com.verizonmedia.phonevalidationservice.infrastructure.consumers.implementation.PhoneNumberNumVerifyWebClient;
import com.verizonmedia.phonevalidationservice.infrastructure.repository.PhoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DefaultPhoneNumberValidationService implements PhoneNumberValidationService {

  @Value("${numverify.access.token}")
  private String token;

  @Autowired
  private PhoneNumberRepository phoneNumberJDBCRepository;

  @Autowired
  private PhoneNumberNumVerifyWebClient phoneNumberWebClient;

  @Override
  public PhoneNumberResponse validatePhoneNumber(String number) {
    PhoneNumber phoneNumber = phoneNumberJDBCRepository.findByNumber(number);
    if (phoneNumber == null) {
      phoneNumber = phoneNumberWebClient
          .getPhoneNumber(token, number);

      if (!StringUtils.isEmpty(phoneNumber.getNumber())) {
        phoneNumberJDBCRepository.createPhoneNumber(phoneNumber);
      }
    }
    PhoneNumberResponse phoneNumberResponse = new PhoneNumberResponse();
    phoneNumberResponse.setIsValid(phoneNumber.getIsValid());
    phoneNumberResponse.setCountry(phoneNumber.getCountryName());
    phoneNumberResponse.setNumber(phoneNumber.getNumber());

    return phoneNumberResponse;
  }
}

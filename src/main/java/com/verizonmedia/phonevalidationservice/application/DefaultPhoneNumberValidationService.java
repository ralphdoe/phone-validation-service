package com.verizonmedia.phonevalidationservice.application;

import com.verizonmedia.phonevalidationservice.domain.models.PhoneNumber;
import com.verizonmedia.phonevalidationservice.domain.responses.PhoneNumberResponse;
import com.verizonmedia.phonevalidationservice.infrastructure.repository.PhoneNumberJDBCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultPhoneNumberValidationService implements PhoneNumberValidationService {

  @Autowired
  private PhoneNumberJDBCRepository phoneNumberJDBCRepository;

  @Override
  public PhoneNumberResponse validatePhoneNumber(String number) {
    PhoneNumber phoneNumber = phoneNumberJDBCRepository.findByNumber(number);
    if (phoneNumber == null) {
      phoneNumber = new PhoneNumber();
      phoneNumber.setNumber("TEST2");
      phoneNumber.setCountryName("TEST");
      phoneNumber.setCountryCode("TEST");
      phoneNumber.setCountryPrefix("TEST");
      phoneNumber.setIsValid(Boolean.TRUE);
      phoneNumber.setCarrier("TEST");
      phoneNumber.setLineType("TEST");
      phoneNumber.setLocalFormat(123456L);
      phoneNumber.setInternationalFormat("TEST");
      phoneNumber.setRegisteredLocation("TEST");
      phoneNumberJDBCRepository.createPhoneNumber(phoneNumber);
    }
    PhoneNumberResponse phoneNumberResponse = new PhoneNumberResponse();
    phoneNumberResponse.setIsValid(phoneNumber.getIsValid());
    phoneNumberResponse.setCountry(phoneNumber.getCountryName());
    phoneNumberResponse.setNumber(phoneNumber.getNumber());

    return phoneNumberResponse;
  }
}

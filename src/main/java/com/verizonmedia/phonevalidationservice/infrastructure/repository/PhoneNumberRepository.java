package com.verizonmedia.phonevalidationservice.infrastructure.repository;

import com.verizonmedia.phonevalidationservice.domain.models.PhoneNumber;

public interface PhoneNumberRepository {

  PhoneNumber findByNumber(String number);
  void createPhoneNumber(PhoneNumber phoneNumber);

}

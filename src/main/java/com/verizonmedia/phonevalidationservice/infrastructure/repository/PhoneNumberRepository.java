package com.verizonmedia.phonevalidationservice.infrastructure.repository;

import com.verizonmedia.phonevalidationservice.domain.models.PhoneNumber;
import java.util.Optional;

public interface PhoneNumberRepository {

  Optional<PhoneNumber> findByNumber(String number);
  void createPhoneNumber(PhoneNumber phoneNumber);

}

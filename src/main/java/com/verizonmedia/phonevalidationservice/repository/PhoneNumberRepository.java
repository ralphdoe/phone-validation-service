package com.verizonmedia.phonevalidationservice.repository;

import com.verizonmedia.phonevalidationservice.models.PhoneNumber;
import java.util.Optional;

public interface PhoneNumberRepository {

  Optional<PhoneNumber> findByNumber(String number);
  void createPhoneNumber(PhoneNumber phoneNumber);

}

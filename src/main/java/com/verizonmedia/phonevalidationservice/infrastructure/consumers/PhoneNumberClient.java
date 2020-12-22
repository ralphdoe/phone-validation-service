package com.verizonmedia.phonevalidationservice.infrastructure.consumers;

import com.verizonmedia.phonevalidationservice.domain.models.PhoneNumber;
import java.util.Optional;

public interface PhoneNumberClient {

  Optional<PhoneNumber> getPhoneNumber(String number);

}

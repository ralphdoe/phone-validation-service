package com.verizonmedia.phonevalidationservice.consumers;

import com.verizonmedia.phonevalidationservice.models.PhoneNumber;
import java.util.Optional;

public interface PhoneNumberClient {

  Optional<PhoneNumber> getPhoneNumber(String number);

}

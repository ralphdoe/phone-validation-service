package com.verizonmedia.phonevalidationservice.infrastructure.consumers;

import com.verizonmedia.phonevalidationservice.domain.models.PhoneNumber;

public interface PhoneNumberClient {

  PhoneNumber getPhoneNumber(String token, String number);

}

package com.verizonmedia.phonevalidationservice.application;

import com.verizonmedia.phonevalidationservice.domain.responses.PhoneNumberResponse;

public interface PhoneNumberValidationService {
  PhoneNumberResponse validatePhoneNumber(String number);

}

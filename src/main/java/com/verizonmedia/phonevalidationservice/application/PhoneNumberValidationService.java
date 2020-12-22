package com.verizonmedia.phonevalidationservice.application;

import com.verizonmedia.phonevalidationservice.domain.responses.PhoneNumberResponse;
import java.util.Optional;

public interface PhoneNumberValidationService {

  Optional<PhoneNumberResponse> validatePhoneNumber(String number);

}

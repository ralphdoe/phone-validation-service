package com.verizonmedia.phonevalidationservice.infrastructure.consumers.implementation;

import com.verizonmedia.phonevalidationservice.domain.models.PhoneNumber;
import com.verizonmedia.phonevalidationservice.domain.responses.PhoneNumberNumVerifyResponse;
import com.verizonmedia.phonevalidationservice.infrastructure.consumers.PhoneNumberClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PhoneNumberNumVerifyWebClient implements PhoneNumberClient {

  private final WebClient webClient = WebClient.create("http://apilayer.net");

  private final String ACCESS_KEY_NAME = "access_key";
  private final String NUMBER_NAME = "number";

  public PhoneNumber getPhoneNumber(String token, String number) {
    PhoneNumberNumVerifyResponse phoneNumberNumVerifyResp = this.webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/api/validate")
            .queryParam(ACCESS_KEY_NAME, token)
            .queryParam(NUMBER_NAME, number)
            .build())
        .retrieve().bodyToFlux(PhoneNumberNumVerifyResponse.class).blockFirst();

    PhoneNumber phoneNumber = new PhoneNumber();
    phoneNumber.setNumber(phoneNumberNumVerifyResp.getNumber());
    phoneNumber.setIsValid(Boolean.valueOf(phoneNumberNumVerifyResp.getValid()));
    phoneNumber.setCountryPrefix(phoneNumberNumVerifyResp.getCountry_prefix());
    phoneNumber.setCountryCode(phoneNumberNumVerifyResp.getCountry_code());
    phoneNumber.setCountryName(phoneNumberNumVerifyResp.getCountry_name());
    phoneNumber.setCarrier(phoneNumberNumVerifyResp.getCarrier());
    phoneNumber.setLineType(phoneNumberNumVerifyResp.getLine_type());
    phoneNumber.setInternationalFormat(phoneNumberNumVerifyResp.getInternational_format());
    phoneNumber.setLocalFormat(Long.valueOf(phoneNumberNumVerifyResp.getLocal_format()));
    phoneNumber.setRegisteredLocation(phoneNumberNumVerifyResp.getLocation());

    return phoneNumber;
  }
}

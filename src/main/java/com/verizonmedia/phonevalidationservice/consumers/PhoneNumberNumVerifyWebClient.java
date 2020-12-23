package com.verizonmedia.phonevalidationservice.consumers;

import com.verizonmedia.phonevalidationservice.models.PhoneNumber;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PhoneNumberNumVerifyWebClient implements PhoneNumberClient {

  @Value("${numverify.access.token}")
  private String token;

  private static final String URL = "http://apilayer.net";
  private static final String ENDPOINT = "/api/validate";
  private static final String ACCESS_KEY_NAME = "access_key";
  private static final String NUMBER_NAME = "number";

  private final WebClient webClient = WebClient.create(URL);

  public Optional<PhoneNumber> getPhoneNumber(String number) {
      return Optional.ofNullable(this.webClient.get()
          .uri(uriBuilder -> uriBuilder
              .path(ENDPOINT)
              .queryParam(ACCESS_KEY_NAME, token)
              .queryParam(NUMBER_NAME, number)
              .build())
          .retrieve().bodyToFlux(PhoneNumber.class).blockFirst());
  }
}

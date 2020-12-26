package com.verizonmedia.phonevalidationservice.phone.webclient;

import com.verizonmedia.phonevalidationservice.phone.PhoneNumber;
import java.time.Duration;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class PhoneNumberAbstractaWebClient implements PhoneNumberClient {

  public static final int SECONDS = 10;
  private static final String URL = "https://phonevalidation.abstractapi.com";
  private static final String ENDPOINT = "/v1";
  private static final String ACCESS_KEY_NAME = "api_key";

  @Value("${abstracta.access.token}")
  private String token;

  private final WebClient webClient = WebClient.create(URL);

  public Optional<PhoneNumber> getPhoneNumber(String number) {
    return Optional.ofNullable(this.webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path(ENDPOINT)
            .queryParam(ACCESS_KEY_NAME, token)
            .queryParam(NUMBER_NAME, number)
            .build())
        .retrieve().bodyToFlux(PhoneNumber.class).timeout(Duration.ofSeconds(SECONDS))
        .blockFirst());
  }
}

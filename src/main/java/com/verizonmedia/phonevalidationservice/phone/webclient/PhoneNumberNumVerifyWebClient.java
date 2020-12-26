package com.verizonmedia.phonevalidationservice.phone.webclient;

import com.verizonmedia.phonevalidationservice.phone.PhoneNumber;
import java.time.Duration;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class PhoneNumberNumVerifyWebClient implements PhoneNumberClient {

  public static final int SECONDS = 10;
  private static final String URL = "http://apilayer.net";
  private static final String ENDPOINT = "/api/validate";
  private static final String ACCESS_KEY_NAME = "access_key";

  @Value("${numverify.access.token}")
  private String token;


  private final WebClient webClient = WebClient.create(URL);

  public Optional<PhoneNumber> getPhoneNumber(String number) throws WebClientException{
    return this.webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path(ENDPOINT)
            .queryParam(ACCESS_KEY_NAME, token)
            .queryParam(NUMBER_NAME, number)
            .build())
        .retrieve()
        .onStatus(HttpStatus::is4xxClientError,
            clientResponse -> Mono.error(new WebClientException("404", "Phone Number Not Found")))
        .onStatus(HttpStatus::is1xxInformational,
            clientResponse -> Mono.error(new WebClientException("104", "User Limit Reached")))
        .bodyToMono(PhoneNumber.class).timeout(Duration.ofSeconds(SECONDS)).blockOptional();
  }
}

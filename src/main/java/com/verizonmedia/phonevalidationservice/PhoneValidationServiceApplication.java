package com.verizonmedia.phonevalidationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PhoneValidationServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(PhoneValidationServiceApplication.class, args);
  }

}

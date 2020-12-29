package com.verizonmedia.phonevalidationservice.phonenumber.client;

import com.verizonmedia.phonevalidationservice.phonenumber.PhoneNumber;
import com.verizonmedia.phonevalidationservice.phonenumber.client.PhoneNumberFeignClient.PhoneNumberFeignClientFallbackFactory;
import feign.FeignException;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "http://apilayer.net", name = "PhoneNumberFeignClient",
    fallbackFactory = PhoneNumberFeignClientFallbackFactory.class)
public interface PhoneNumberFeignClient {

  @GetMapping("/api/validate")
  PhoneNumber getPhoneNumber(@RequestParam String access_key, @RequestParam String number);

  @Component
  class PhoneNumberFeignClientFallbackFactory implements
      FallbackFactory<PhoneNumberFeignClient> {

    @Override
    public PhoneNumberFeignClient create(Throwable cause) {
      return (access_key, number) -> {
        String httpStatus =
            cause instanceof FeignException ? Integer.toString(((FeignException) cause).status())
                : "";
        throw new PhoneNumberException(httpStatus, cause.getMessage());
      };
    }
  }
}

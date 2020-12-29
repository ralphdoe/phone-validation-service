package com.verizonmedia.phonevalidationservice.phone.client;

import com.verizonmedia.phonevalidationservice.phone.PhoneNumber;
import feign.FeignException;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "http://apilayer.net", name = "PhoneNumberFeignClient")
public interface PhoneNumberFeignClient {

  @GetMapping("/api/validate")
  PhoneNumber getPhoneNumber(@RequestParam String access_key, @RequestParam String number);

  @Component
  class PhonenumberFeignClientFallbackFactory implements
      FallbackFactory<PhoneNumberFeignClient> {

    @Override
    public PhoneNumberFeignClient create(Throwable cause) {
      return new PhoneNumberFeignClient() {
        @Override
        public PhoneNumber getPhoneNumber(String access_key, String number) {
          String httpStatus =
              cause instanceof FeignException ? Integer.toString(((FeignException) cause).status())
                  : "";
          throw new PhoneNumberException(httpStatus, cause.getMessage());
        }
      };
    }
  }
}

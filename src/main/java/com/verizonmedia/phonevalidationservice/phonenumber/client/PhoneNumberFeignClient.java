package com.verizonmedia.phonevalidationservice.phonenumber.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "http://apilayer.net", name = "PhoneNumberFeignClient")
public interface PhoneNumberFeignClient {

  @GetMapping("/api/validate")
  Object getPhoneNumber(@RequestParam String access_key, @RequestParam String number);

}

package com.verizonmedia.phonevalidationservice;

import static org.assertj.core.api.Assertions.assertThat;

import com.verizonmedia.phonevalidationservice.phonenumber.PhoneNumberRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PhoneValidationServiceApplicationTests {

  @Autowired
  private PhoneNumberRestController phoneNumberRestController;

  @Test
  void contextLoads() {
    assertThat(phoneNumberRestController).isNotNull();
  }

}

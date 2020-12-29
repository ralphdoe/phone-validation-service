package com.verizonmedia.phonevalidationservice.phonenumber;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

import com.verizonmedia.phonevalidationservice.phonenumber.client.PhoneNumberException;
import com.verizonmedia.phonevalidationservice.phonenumber.client.PhoneNumberFeignClient;
import com.verizonmedia.phonevalidationservice.phonenumber.client.PhoneNumberFeignService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PhoneNumberFeignServiceTest {

  public static final String NUMBER = "14154582468";
  public static final String TOKEN = "d58248631716f7fd62a8fd7ce1b546f0";

  @Mock
  private PhoneNumberFeignClient phoneNumberFeignClient;

  @InjectMocks
  private PhoneNumberFeignService phoneNumberFeignService;

  @Test
  public void obtainDataFromFeignClient() throws PhoneNumberException {
    PhoneNumber phoneNumber = new PhoneNumber();
    phoneNumber.setNumber("14154582468");
    phoneNumber.setCountryName("United States");
    phoneNumber.setCountryPrefix("US");
    phoneNumber.setCountryCode("+1");
    phoneNumber.setIsValid(Boolean.TRUE);
    when(phoneNumberFeignClient.getPhoneNumber(TOKEN, NUMBER)).thenReturn(phoneNumber);
    Optional<PhoneNumber> phoneNumberOptional = phoneNumberFeignService.getPhoneNumber(NUMBER);
    assertFalse(phoneNumberOptional.isPresent());

  }
}
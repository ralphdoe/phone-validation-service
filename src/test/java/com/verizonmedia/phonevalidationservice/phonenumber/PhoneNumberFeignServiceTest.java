package com.verizonmedia.phonevalidationservice.phonenumber;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.verizonmedia.phonevalidationservice.phonenumber.client.PhoneNumberException;
import com.verizonmedia.phonevalidationservice.phonenumber.client.PhoneNumberFeignClient;
import com.verizonmedia.phonevalidationservice.phonenumber.client.PhoneNumberFeignService;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class PhoneNumberFeignServiceTest {

  public static final String NUMBER = "14158586273";
  public static final String TOKEN = "d58248631716f7fd62a8fd7ce1b546f0";

  @MockBean
  private PhoneNumberFeignClient phoneNumberFeignClient;

  @Autowired
  private PhoneNumberFeignService phoneNumberFeignService;

  @Test
  public void obtainDataFromFeignClient() throws PhoneNumberException {
    PhoneNumber phoneNumber = new PhoneNumber();
    phoneNumber.setNumber("14158586273");
    phoneNumber.setCountryName("United States");
    phoneNumber.setCountryPrefix("+1");
    phoneNumber.setCountryCode("US");
    phoneNumber.setRegisteredLocation("Novato");
    phoneNumber.setLineType("mobile");
    phoneNumber.setInternationalFormat("+14158586273");
    phoneNumber.setCarrier("AT&T Mobility LLC");
    phoneNumber.setLocalFormat(4158586273L);
    phoneNumber.setIsValid(Boolean.TRUE);
    Optional<PhoneNumber> phoneNumberOptional = phoneNumberFeignService.getPhoneNumber(NUMBER);
    assertTrue(phoneNumberOptional.isPresent());
    PhoneNumber phoneNumberResult = phoneNumberOptional.get();
    assertEquals(phoneNumber.getNumber(), phoneNumberResult.getNumber());
    assertEquals(phoneNumber.getCountryCode(), phoneNumberResult.getCountryCode());
    assertEquals(phoneNumber.getCountryPrefix(), phoneNumberResult.getCountryPrefix());
    assertEquals(phoneNumber.getLocalFormat(), phoneNumberResult.getLocalFormat());
    assertEquals(phoneNumber.getInternationalFormat(), phoneNumberResult.getInternationalFormat());
    assertEquals(phoneNumber.getRegisteredLocation(), phoneNumberResult.getRegisteredLocation());
  }

  @Test
  public void whenNumberIsNullThrowCustomException() throws PhoneNumberException {
    PhoneNumberException phoneNumberException = assertThrows(PhoneNumberException.class, () -> {
      Map responseMap = new LinkedHashMap<>();
      responseMap.put("success", Boolean.FALSE);

      Map errorMap = new LinkedHashMap<>();
      errorMap.put("code", "210");
      errorMap.put("type", "no_phone_number_provided");
      errorMap.put("info", "Please specify a phone number. [Example: 14158586273]");
      responseMap.put("error", errorMap);
      when(phoneNumberFeignClient.getPhoneNumber(TOKEN, "")).thenReturn(responseMap);
      phoneNumberFeignService.getPhoneNumber("");
    });

    assertEquals("210", phoneNumberException.getCode());
    assertEquals("no_phone_number_provided", phoneNumberException.getType());
    assertEquals("Please specify a phone number. [Example: 14158586273]",
        phoneNumberException.getInfo());

  }
}
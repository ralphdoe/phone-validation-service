package com.verizonmedia.phonevalidationservice.phone;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PhoneNumberRestController.class)
class PhoneNumberRestControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private PhoneNumberValidationService phoneNumberValidationService;

  @Test
  void getPhoneNumberFromEndpoint() throws Exception {

    String number = "14154582468";

    PhoneNumberResponse phoneNumberResponse = new PhoneNumberResponse();
    phoneNumberResponse.setIsValid(Boolean.TRUE);
    phoneNumberResponse.setCountry("United States");
    phoneNumberResponse.setCountryCode("US");
    phoneNumberResponse.setCountryPrefix("+1");
    phoneNumberResponse.setNumber(number);

    when(phoneNumberValidationService.validatePhoneNumber(number))
        .thenReturn(Optional.of(phoneNumberResponse));

    mvc.perform(MockMvcRequestBuilders.get("/validate/{number}", number)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.country").value(Matchers.is("United States")))
        .andExpect(jsonPath("$.number").value(Matchers.is(number)));
  }

  @Test
  void getPhoneNumbersListFromEndpoint() throws Exception {
    Set<String> numbers = new HashSet<>();
    numbers.add("14154582468");
    numbers.add("573015261679");

    PhoneNumberResponse phoneNumberResponse = new PhoneNumberResponse();
    phoneNumberResponse.setIsValid(Boolean.TRUE);
    phoneNumberResponse.setCountry("United States");
    phoneNumberResponse.setCountryCode("US");
    phoneNumberResponse.setCountryPrefix("+1");
    phoneNumberResponse.setNumber("14154582468");

    PhoneNumberResponse anotherPhoneNumberResponse = new PhoneNumberResponse();
    anotherPhoneNumberResponse.setIsValid(Boolean.TRUE);
    anotherPhoneNumberResponse.setCountry("Colombia");
    anotherPhoneNumberResponse.setCountryCode("CO");
    anotherPhoneNumberResponse.setCountryPrefix("+57");
    anotherPhoneNumberResponse.setNumber("573015261679");

    List<PhoneNumberResponse> responses = new ArrayList<>();
    responses.add(phoneNumberResponse);
    responses.add(anotherPhoneNumberResponse);

    when(phoneNumberValidationService.validatePhoneNumbers(numbers))
        .thenReturn(responses);

    mvc.perform(MockMvcRequestBuilders.get("/validate/")
        .param("numbers", String.join(",", numbers))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].country").value(Matchers.is("United States")))
        .andExpect(jsonPath("$[0].number").value(Matchers.is("14154582468")))
        .andExpect(jsonPath("$[1].country").value(Matchers.is("Colombia")))
        .andExpect(jsonPath("$[1].number").value(Matchers.is("573015261679")));
  }
}
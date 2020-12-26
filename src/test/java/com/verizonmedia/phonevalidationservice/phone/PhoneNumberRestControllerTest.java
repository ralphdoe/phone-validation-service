package com.verizonmedia.phonevalidationservice.phone;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PhoneNumberRestController.class)
class PhoneNumberRestControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private PhoneNumberValidationService phoneNumberValidationService;

  @Test
  void validatePhoneNumber() throws Exception {

    String number = "14154582468";

    PhoneNumberResponse phoneNumberResponse = new PhoneNumberResponse();
    phoneNumberResponse.setIsValid(Boolean.TRUE);
    phoneNumberResponse.setCountry("United States");
    phoneNumberResponse.setCountryCode("");
    phoneNumberResponse.setCountryPrefix("");

    when(phoneNumberValidationService.validatePhoneNumber(number))
        .thenReturn(Optional.of(phoneNumberResponse));

    /*mvc.perform(get("/validate")
        .param("number", number)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].country", is("United States")));*/
  }

  @Test
  void validatePhoneNumbers() {
  }
}
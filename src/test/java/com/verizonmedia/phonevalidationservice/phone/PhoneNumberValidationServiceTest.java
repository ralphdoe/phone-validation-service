package com.verizonmedia.phonevalidationservice.phone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.verizonmedia.phonevalidationservice.phone.client.FeignService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.reactive.function.client.WebClientException;

@ExtendWith(MockitoExtension.class)
class PhoneNumberValidationServiceTest {

  public static final String NUMBER = "14154582468";
  public static final String NUMBER_2 = "573015261679";
  public static final String NUMBER_3 = "573015261679";
  public static final int ACTUAL = 2;

  @Mock
  private PhoneNumberJDBCRepository phoneNumberJDBCRepository;
  @Mock
  private FeignService feignService;

  @InjectMocks
  private PhoneNumberValidationService phoneNumberValidationService;

  @Test
  public void obtainResponseWhenInfoComesFromDatabase() throws EmptyResultDataAccessException {
    PhoneNumber phoneNumber = new PhoneNumber();
    phoneNumber.setNumber("14154582468");
    phoneNumber.setCountryName("United States");
    phoneNumber.setCountryPrefix("US");
    phoneNumber.setCountryCode("+1");
    phoneNumber.setIsValid(Boolean.TRUE);
    when(phoneNumberJDBCRepository.findByNumber(NUMBER)).thenReturn(Optional.of(phoneNumber));
    Optional<PhoneNumberResponse> phoneNumberResponse = phoneNumberValidationService
        .validatePhoneNumber(NUMBER);
    assertTrue(phoneNumberResponse.isPresent());
    assertEquals(phoneNumberResponse.get().getNumber(), NUMBER);

  }

  @Test
  public void obtainResponseWhenInfoIsNotInDatabase()
      throws EmptyResultDataAccessException, WebClientException {
    PhoneNumber phoneNumber = new PhoneNumber();
    phoneNumber.setNumber("14154582468");
    phoneNumber.setCountryName("United States");
    phoneNumber.setIsValid(Boolean.TRUE);
    when(phoneNumberJDBCRepository.findByNumber(NUMBER)).thenReturn(Optional.empty());
    when(feignService.getPhoneNumber(NUMBER))
        .thenReturn(Optional.of(phoneNumber));

    Optional<PhoneNumberResponse> phoneNumberResponse = phoneNumberValidationService
        .validatePhoneNumber(NUMBER);
    assertTrue(phoneNumberResponse.isPresent());
    assertEquals(phoneNumberResponse.get().getNumber(), NUMBER);

  }

  @Test
  public void obtainEmptyResponseWhenInfoIsNotInDatabaseNeitherExternalAPI()
      throws EmptyResultDataAccessException, WebClientException {
    PhoneNumber phoneNumber = new PhoneNumber();
    phoneNumber.setNumber("14154582468");
    phoneNumber.setCountryName("United States");
    phoneNumber.setIsValid(Boolean.TRUE);
    when(phoneNumberJDBCRepository.findByNumber(NUMBER)).thenReturn(Optional.empty());
    when(feignService.getPhoneNumber(NUMBER))
        .thenReturn(Optional.empty());

    Optional<PhoneNumberResponse> phoneNumberResponse = phoneNumberValidationService
        .validatePhoneNumber(NUMBER);
    assertTrue(phoneNumberResponse.isEmpty());

  }

  @Test
  public void obtainResponsesListWhenInfoComesFromDatabaseAndEndpoint() {
    PhoneNumber phoneNumber = new PhoneNumber();
    phoneNumber.setNumber("14154582468");
    phoneNumber.setCountryName("United States");
    phoneNumber.setIsValid(Boolean.TRUE);

    PhoneNumber phoneNumber2 = new PhoneNumber();
    phoneNumber.setNumber("573015261679");
    phoneNumber.setCountryName("United States");
    phoneNumber.setIsValid(Boolean.TRUE);

    List<PhoneNumber> phoneNumberList = new ArrayList<>();
    phoneNumberList.add(phoneNumber);
    phoneNumberList.add(phoneNumber2);

    Set<String> numbers = new HashSet<>();
    numbers.add(NUMBER);
    numbers.add(NUMBER_2);
    numbers.add(NUMBER_3);

    when(phoneNumberJDBCRepository.findByListOfNumbers(numbers)).thenReturn(phoneNumberList);

    List<PhoneNumberResponse> phoneNumberResponses = phoneNumberValidationService
        .validatePhoneNumbers(numbers);

    assertEquals(phoneNumberResponses.size(), ACTUAL);
  }

}
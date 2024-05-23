package com.verizonmedia.phonevalidationservice.phonenumber;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.verizonmedia.phonevalidationservice.phonenumber.client.PhoneNumberFeignService;
import com.verizonmedia.phonevalidationservice.phonenumber.client.PhoneNumberException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

@ExtendWith(MockitoExtension.class)
class PhoneNumberValidationServiceTest {

  public static final String NUMBER = "14154582468";
  public static final String NUMBER_2 = "573015261679";
  public static final String NUMBER_3 = "573015261679";
  public static final int ACTUAL = 2;

  @Mock
  private PhoneNumberJDBCRepository phoneNumberJDBCRepository;

  @Mock
  private PhoneNumberFeignService phoneNumberFeignService;

  @InjectMocks
  private PhoneNumberValidationService phoneNumberValidationService;

  private PhoneNumber phoneNumber;

  @BeforeEach
  public void setUp() {
    phoneNumber = new PhoneNumber();
    phoneNumber.setNumber("14154582468");
    phoneNumber.setCountryName("United States");
    phoneNumber.setCountryPrefix("US");
    phoneNumber.setCountryCode("+1");
    phoneNumber.setIsValid(Boolean.TRUE);
  }

  @Test
  public void obtainResponseWhenInfoComesFromDatabase() throws EmptyResultDataAccessException {
    when(phoneNumberJDBCRepository.findByNumber(NUMBER)).thenReturn(Optional.of(phoneNumber));
    Optional<PhoneNumberResponse> phoneNumberResponse = phoneNumberValidationService
        .validatePhoneNumber(NUMBER);
    assertTrue(phoneNumberResponse.isPresent());
    assertEquals(phoneNumberResponse.get().getNumber(), NUMBER);

  }

  @Test
  public void obtainResponseWhenInfoIsNotInDatabase()
      throws EmptyResultDataAccessException, PhoneNumberException {
    when(phoneNumberJDBCRepository.findByNumber(NUMBER)).thenReturn(Optional.empty());
    when(phoneNumberFeignService.getPhoneNumber(NUMBER))
        .thenReturn(Optional.of(phoneNumber));

    Optional<PhoneNumberResponse> phoneNumberResponse = phoneNumberValidationService
        .validatePhoneNumber(NUMBER);
    assertTrue(phoneNumberResponse.isPresent());
    assertEquals(phoneNumberResponse.get().getNumber(), NUMBER);

  }

  @Test
  public void obtainEmptyResponseWhenInfoIsNotInDatabaseNeitherExternalAPI()
      throws EmptyResultDataAccessException, PhoneNumberException {
    when(phoneNumberJDBCRepository.findByNumber(NUMBER)).thenReturn(Optional.empty());
    when(phoneNumberFeignService.getPhoneNumber(NUMBER))
        .thenReturn(Optional.empty());

    Optional<PhoneNumberResponse> phoneNumberResponse = phoneNumberValidationService
        .validatePhoneNumber(NUMBER);
    assertTrue(phoneNumberResponse.isEmpty());
  }

  @Test
  public void obtainResponsesListWhenInfoComesFromDatabaseAndEndpoint() {
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

    when(phoneNumberJDBCRepository.findBySetOfNumbers(numbers)).thenReturn(phoneNumberList);

    List<PhoneNumberResponse> phoneNumberResponses = phoneNumberValidationService
        .validatePhoneNumbers(numbers);

    assertEquals(phoneNumberResponses.size(), ACTUAL);
  }
}
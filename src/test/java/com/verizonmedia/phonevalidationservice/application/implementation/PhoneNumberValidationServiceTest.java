package com.verizonmedia.phonevalidationservice.application.implementation;

import static org.junit.jupiter.api.Assertions.*;

import com.verizonmedia.phonevalidationservice.models.PhoneNumber;
import com.verizonmedia.phonevalidationservice.models.PhoneNumberResponse;
import com.verizonmedia.phonevalidationservice.consumers.PhoneNumberClient;
import com.verizonmedia.phonevalidationservice.repository.PhoneNumberRepository;
import com.verizonmedia.phonevalidationservice.services.PhoneNumberValidationService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PhoneNumberValidationServiceTest {

  public static final String NUMBER = "14154582468";

  @Mock
  private PhoneNumberRepository phoneNumberJDBCRepository;
  @Mock
  private PhoneNumberClient phoneNumberClient;

  @InjectMocks
  private PhoneNumberValidationService phoneNumberValidationService;

  @Test
  public void obtainResponseWhenInfoComesFromDatabase() {
    PhoneNumber phoneNumber = new PhoneNumber();
    phoneNumber.setNumber("14154582468");
    phoneNumber.setCountryName("United States");
    phoneNumber.setIsValid(Boolean.TRUE);
    when(phoneNumberJDBCRepository.findByNumber(NUMBER)).thenReturn(Optional.of(phoneNumber));

    Optional<PhoneNumberResponse> phoneNumberResponse = phoneNumberValidationService
        .validatePhoneNumber(NUMBER);
    assertTrue(phoneNumberResponse.isPresent());
    assertEquals(phoneNumberResponse.get().getNumber(), NUMBER);

  }

  @Test
  public void obtainResponseWhenInfoIsNotInDatabase() {
    PhoneNumber phoneNumber = new PhoneNumber();
    phoneNumber.setNumber("14154582468");
    phoneNumber.setCountryName("United States");
    phoneNumber.setIsValid(Boolean.TRUE);
    when(phoneNumberJDBCRepository.findByNumber(NUMBER)).thenReturn(Optional.empty());
    when(phoneNumberClient.getPhoneNumber(NUMBER)).thenReturn(Optional.of(phoneNumber));

    Optional<PhoneNumberResponse> phoneNumberResponse = phoneNumberValidationService
        .validatePhoneNumber(NUMBER);
    assertTrue(phoneNumberResponse.isPresent());
    assertEquals(phoneNumberResponse.get().getNumber(), NUMBER);

  }

  @Test
  public void obtainEmptyResponseWhenInfoIsNotInDatabaseNeitherExternalAPI() {
    PhoneNumber phoneNumber = new PhoneNumber();
    phoneNumber.setNumber("14154582468");
    phoneNumber.setCountryName("United States");
    phoneNumber.setIsValid(Boolean.TRUE);
    when(phoneNumberJDBCRepository.findByNumber(NUMBER)).thenReturn(Optional.empty());
    when(phoneNumberClient.getPhoneNumber(NUMBER)).thenReturn(Optional.empty());

    Optional<PhoneNumberResponse> phoneNumberResponse = phoneNumberValidationService
        .validatePhoneNumber(NUMBER);
    assertTrue(phoneNumberResponse.isEmpty());

  }

}
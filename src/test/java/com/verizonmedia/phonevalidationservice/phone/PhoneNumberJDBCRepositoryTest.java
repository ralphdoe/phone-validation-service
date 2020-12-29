package com.verizonmedia.phonevalidationservice.phone;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PhoneNumberJDBCRepositoryTest {

  @InjectMocks
  private PhoneNumberJDBCRepository phoneNumberJDBCRepository;

  @Test
  public void generateInClauseFromNumber() {
    int size = 5;
    String expected = "(?,?,?,?,?);";
    String result = phoneNumberJDBCRepository.generateInClauseFromNumber(size);
    assertEquals(expected, result);
  }
}
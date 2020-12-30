package com.verizonmedia.phonevalidationservice.phonenumber;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class PhoneNumberJDBCRepositoryTest {

  private static final String PHONE_NUMBER_SELECT_QUERY =
      "SELECT * FROM phone_numbers WHERE phone_number = ?;";

  private static final String PHONE_NUMBER_SELECT_IN_QUERY =
      "SELECT * FROM phone_numbers WHERE phone_number = IN('14154582468');";

  @Mock
  JdbcTemplate jdbcTemplate;

  @InjectMocks
  private PhoneNumberJDBCRepository phoneNumberJDBCRepository;

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
  public void whenMockJdbcTemplate_thenReturnPhoneNumber() {
    when(jdbcTemplate
        .queryForObject(anyString(), any(PhoneNumberRowMapper.class),
            anyString()))
        .thenReturn(phoneNumber);
    Optional<PhoneNumber> numberResult = phoneNumberJDBCRepository
        .findByNumber(phoneNumber.getNumber());
    assertTrue(numberResult.isPresent());
  }

  @Test
  public void whenMockJdbcTemplate_thenReturnEmptyPhoneNumberList() {
    Set<String> numbers = new HashSet<>();
    numbers.add("14154582468");
    List<PhoneNumber> phoneNumberList = new ArrayList<>();
    phoneNumberList.add(phoneNumber);
    ReflectionTestUtils.setField(phoneNumberJDBCRepository, "jdbcTemplate", jdbcTemplate);
    when(jdbcTemplate.query(anyString(), any(PhoneNumberRowMapper.class), any()))
        .thenReturn(phoneNumberList);
    List<PhoneNumber> phoneNumberListResult = phoneNumberJDBCRepository
        .findBySetOfNumbers(numbers);
    assertEquals(1, phoneNumberListResult.size());
  }

  @Test
  public void generateInClauseFromNumber() {
    int size = 5;
    String expected = "(?,?,?,?,?);";
    String result = phoneNumberJDBCRepository.generateInClauseFromNumber(size);
    assertEquals(expected, result);
  }
}
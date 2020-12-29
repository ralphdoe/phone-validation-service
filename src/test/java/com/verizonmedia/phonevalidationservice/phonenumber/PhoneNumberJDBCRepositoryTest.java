package com.verizonmedia.phonevalidationservice.phonenumber;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PhoneNumberJDBCRepositoryTest {

  @InjectMocks
  private PhoneNumberJDBCRepository
      phoneNumberJDBCRepository;

  private static final String PHONE_NUMBER_SELECT_QUERY =
      "SELECT * FROM phone_numbers WHERE phone_number = '14154582468';";

  private static final String PHONE_NUMBER_SELECT_IN_QUERY =
      "SELECT * FROM phone_numbers WHERE phone_number = IN('14154582468');";

  @Mock
  JdbcTemplate jdbcTemplate;

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
  public void whenMockJdbcTemplate_thenReturnEmptyPhoneNumber() {
    ReflectionTestUtils.setField(phoneNumberJDBCRepository, "jdbcTemplate", jdbcTemplate);
    Mockito.when(jdbcTemplate
        .queryForObject(PHONE_NUMBER_SELECT_QUERY, new PhoneNumberRowMapper(), PhoneNumber.class))
        .thenReturn(phoneNumber);
    Optional<PhoneNumber> numberResult = phoneNumberJDBCRepository
        .findByNumber(phoneNumber.getNumber());
    assertFalse(numberResult.isPresent());
  }

  @Test
  public void whenMockJdbcTemplate_thenReturnEmptyPhoneNumberList() {
    Set<String> numbers = new HashSet<>();
    numbers.add("14154582468");
    List<PhoneNumber> phoneNumberList = new ArrayList<>();
    phoneNumberList.add(phoneNumber);
    ReflectionTestUtils.setField(phoneNumberJDBCRepository, "jdbcTemplate", jdbcTemplate);
    Mockito.when(jdbcTemplate
        .query(PHONE_NUMBER_SELECT_IN_QUERY, new PhoneNumberRowMapper(), numbers.toArray()))
        .thenReturn(phoneNumberList);
    List<PhoneNumber> phoneNumberListResult = phoneNumberJDBCRepository
        .findBySetOfNumbers(numbers);
    assertTrue(phoneNumberListResult.size() == 0);
  }

  @Test
  public void generateInClauseFromNumber() {
    int size = 5;
    String expected = "(?,?,?,?,?);";
    String result = phoneNumberJDBCRepository.generateInClauseFromNumber(size);
    assertEquals(expected, result);
  }
}
package com.verizonmedia.phonevalidationservice.repository;

import com.verizonmedia.phonevalidationservice.models.PhoneNumber;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PhoneNumberJDBCRepository implements PhoneNumberRepository {

  private static final String PHONE_NUMBER_SELECT_QUERY =
      "SELECT * FROM phone_numbers WHERE phone_number = ?;";
  private static final String PHONE_NUMBER_INSERT_QUERY = "INSERT INTO phone_numbers "
      + "(phone_number, is_valid, country_name, "
      + "country_code, country_prefix) "
      + "VALUES (?,?,?,?,?)";

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public Optional<PhoneNumber> findByNumber(String number) {
    try {
      PhoneNumber phoneNumber = jdbcTemplate
          .queryForObject(PHONE_NUMBER_SELECT_QUERY, new PhoneNumberRowMapper(), number);
      return Optional.ofNullable(phoneNumber);
    } catch (EmptyResultDataAccessException ex) {
      return Optional.empty();
    }
  }

  public void createPhoneNumber(PhoneNumber phoneNumber) {
    jdbcTemplate
        .update(PHONE_NUMBER_INSERT_QUERY,
            phoneNumber.getNumber(), phoneNumber.getIsValid(),
            phoneNumber.getCountryName(), phoneNumber.getCountryCode(),
            phoneNumber.getCountryPrefix());
  }

}

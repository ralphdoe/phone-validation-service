package com.verizonmedia.phonevalidationservice.infrastructure.repository;

import com.verizonmedia.phonevalidationservice.domain.models.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PhoneNumberJDBCRepository {

  private static final String PHONE_NUMBER_SELECT_QUERY =
      "SELECT * FROM phone_numbers WHERE phone_number = ?;";
  private static final String PHONE_NUMBER_INSERT_QUERY = "INSERT INTO phone_numbers "
      + "(phone_number, is_valid, local_format, international_format, country_name, "
      + "country_code, country_prefix, registered_location, carrier, lineType ) "
      + "VALUES (?,?,?,?,?,?,?,?,?,?)";

  @Autowired
  JdbcTemplate jdbcTemplate;

  public PhoneNumber findByNumber(String number) {
    try {
      return jdbcTemplate
          .queryForObject(PHONE_NUMBER_SELECT_QUERY, new PhoneNumberRowMapper(), number);
    } catch (EmptyResultDataAccessException ex) {
      return null;
    }
  }

  public int createPhoneNumber(PhoneNumber phoneNumber) {
    return jdbcTemplate
        .update(PHONE_NUMBER_INSERT_QUERY,
            phoneNumber.getNumber(), phoneNumber.getIsValid(),
            phoneNumber.getLocalFormat(), phoneNumber.getInternationalFormat(),
            phoneNumber.getCountryName(), phoneNumber.getCountryCode(),
            phoneNumber.getCountryPrefix(), phoneNumber.getRegisteredLocation(),
            phoneNumber.getCarrier(), phoneNumber.getLineType());
  }

}

package com.verizonmedia.phonevalidationservice.repository;

import com.verizonmedia.phonevalidationservice.models.PhoneNumber;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class PhoneNumberJDBCRepository implements PhoneNumberRepository {

  Logger logger = LoggerFactory.getLogger(PhoneNumberJDBCRepository.class);

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
      logger.error("Object not found in the database.");
      return Optional.empty();
    }
  }

  public void createPhoneNumber(PhoneNumber phoneNumber) {
    jdbcTemplate
        .update(PHONE_NUMBER_INSERT_QUERY,
            phoneNumber.getNumber(), phoneNumber.getIsValid(),
            phoneNumber.getCountryName(), phoneNumber.getCountryCode(),
            phoneNumber.getCountryPrefix());
    logger.info("Phone number created!");
  }

}

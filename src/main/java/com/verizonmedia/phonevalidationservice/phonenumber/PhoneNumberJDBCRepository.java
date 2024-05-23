package com.verizonmedia.phonevalidationservice.phonenumber;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class PhoneNumberJDBCRepository {

  private static final String PHONE_NUMBER_SELECT_QUERY =
      "SELECT * FROM phone_numbers WHERE phone_number = ?;";
  private static final String PHONE_NUMBER_SELECT_IN_QUERY =
      "SELECT * FROM phone_numbers WHERE phone_number IN ";
  private static final String PHONE_NUMBER_INSERT_QUERY = "INSERT INTO phone_numbers "
      + "(phone_number, is_valid, country_name, "
      + "country_code, country_prefix) "
      + "VALUES (?,?,?,?,?)";

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public Optional<PhoneNumber> findByNumber(String number) throws EmptyResultDataAccessException {
    PhoneNumber phoneNumber = jdbcTemplate
        .queryForObject(PHONE_NUMBER_SELECT_QUERY, new PhoneNumberRowMapper(), number);
    return Optional.ofNullable(phoneNumber);
  }

  public List<PhoneNumber> findBySetOfNumbers(Set<String> numbers) {
    List<PhoneNumber> phoneNumberList = jdbcTemplate
        .query(PHONE_NUMBER_SELECT_IN_QUERY + generateInClauseFromNumber(numbers.size()),
            new PhoneNumberRowMapper(), numbers.toArray());
    return phoneNumberList;
  }

  public void createPhoneNumber(PhoneNumber phoneNumber) {
    log.info("Creating Phone Number -> {}", jdbcTemplate
        .update(PHONE_NUMBER_INSERT_QUERY,
            phoneNumber.getNumber(), phoneNumber.getIsValid(),
            phoneNumber.getCountryName(), phoneNumber.getCountryCode(),
            phoneNumber.getCountryPrefix()));
  }

  public void createPhoneNumbersBatchMode(List<PhoneNumber> phoneNumbers) {
    log.info("Creating Phone Number List -> {}", jdbcTemplate
        .batchUpdate(PHONE_NUMBER_INSERT_QUERY, new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            PhoneNumber phoneNumber = phoneNumbers.get(i);
            ps.setString(1, phoneNumber.getNumber());
            ps.setBoolean(2, phoneNumber.getIsValid());
            ps.setString(3, phoneNumber.getCountryName());
            ps.setString(4, phoneNumber.getCountryCode());
            ps.setString(5, phoneNumber.getCountryPrefix());
          }

          @Override
          public int getBatchSize() {
            return phoneNumbers.size();
          }
        }));
  }

  public String generateInClauseFromNumber(int size) {
    StringBuilder inClause = new StringBuilder();
    inClause.append("(");
    for (int i = 0; i < size; i++) {
      if (i == size - 1) {
        inClause.append("?);");
        continue;
      }
      inClause.append("?,");
    }
    return inClause.toString();
  }
}

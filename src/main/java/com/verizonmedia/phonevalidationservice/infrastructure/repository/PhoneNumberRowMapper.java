package com.verizonmedia.phonevalidationservice.infrastructure.repository;

import com.verizonmedia.phonevalidationservice.domain.models.PhoneNumber;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class PhoneNumberRowMapper implements RowMapper<PhoneNumber> {

  @Override
  public PhoneNumber mapRow(ResultSet rs, int rowNum) throws SQLException {
    PhoneNumber phoneNumber = new PhoneNumber();
    phoneNumber.setIsValid(rs.getBoolean("is_valid"));
    phoneNumber.setCountryCode(rs.getString("country_code"));
    phoneNumber.setCountryName(rs.getString("country_name"));
    phoneNumber.setNumber(rs.getString("phone_number"));
    return phoneNumber;
  }
}

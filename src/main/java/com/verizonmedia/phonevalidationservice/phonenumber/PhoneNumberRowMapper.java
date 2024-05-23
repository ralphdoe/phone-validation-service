package com.verizonmedia.phonevalidationservice.phonenumber;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class PhoneNumberRowMapper implements RowMapper<PhoneNumber> {

  public static final String IS_VALID = "is_valid";
  public static final String COUNTRY_CODE = "country_code";
  public static final String COUNTRY_NAME = "country_name";
  public static final String PHONE_NUMBER = "phone_number";
  public static final String COUNTRY_PREFIX = "country_prefix";

  @Override
  public PhoneNumber mapRow(ResultSet rs, int rowNum) throws SQLException {
    PhoneNumber phoneNumber = new PhoneNumber();
    phoneNumber.setIsValid(rs.getBoolean(IS_VALID));
    phoneNumber.setCountryCode(rs.getString(COUNTRY_CODE));
    phoneNumber.setCountryName(rs.getString(COUNTRY_NAME));
    phoneNumber.setNumber(rs.getString(PHONE_NUMBER));
    phoneNumber.setCountryPrefix(rs.getString(COUNTRY_PREFIX));
    return phoneNumber;
  }
}

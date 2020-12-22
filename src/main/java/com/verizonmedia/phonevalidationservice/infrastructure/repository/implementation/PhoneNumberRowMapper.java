package com.verizonmedia.phonevalidationservice.infrastructure.repository.implementation;

import com.verizonmedia.phonevalidationservice.domain.models.PhoneNumber;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class PhoneNumberRowMapper implements RowMapper<PhoneNumber> {

  public static final String IS_VALID = "is_valid";
  public static final String COUNTRY_CODE = "country_code";
  public static final String COUNTRY_NAME = "country_name";
  public static final String PHONE_NUMBER = "phone_number";
  public static final String REGISTERED_LOCATION = "registered_location";
  public static final String INTERNATIONAL_FORMAT = "international_format";
  public static final String COUNTRY_PREFIX = "country_prefix";
  public static final String LOCAL_FORMAT = "local_format";
  public static final String CARRIER = "carrier";
  public static final String LINE_TYPE = "line_type";

  @Override
  public PhoneNumber mapRow(ResultSet rs, int rowNum) throws SQLException {
    PhoneNumber phoneNumber = new PhoneNumber();
    phoneNumber.setIsValid(rs.getBoolean(IS_VALID));
    phoneNumber.setCountryCode(rs.getString(COUNTRY_CODE));
    phoneNumber.setCountryName(rs.getString(COUNTRY_NAME));
    phoneNumber.setNumber(rs.getString(PHONE_NUMBER));
    phoneNumber.setRegisteredLocation(rs.getString(REGISTERED_LOCATION));
    phoneNumber.setInternationalFormat(rs.getString(INTERNATIONAL_FORMAT));
    phoneNumber.setCountryPrefix(rs.getString(COUNTRY_PREFIX));
    phoneNumber.setLocalFormat(rs.getLong(LOCAL_FORMAT));
    phoneNumber.setCarrier(rs.getString(CARRIER));
    phoneNumber.setLineType(rs.getString(LINE_TYPE));
    return phoneNumber;
  }
}

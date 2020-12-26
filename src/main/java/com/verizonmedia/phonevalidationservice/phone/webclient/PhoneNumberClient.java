package com.verizonmedia.phonevalidationservice.phone.webclient;

import com.verizonmedia.phonevalidationservice.phone.PhoneNumber;
import java.util.Optional;

/**
 * Class to communicate with Third Party Services to Obtain Phone Number Data.
 *
 * @author Rafael López
 */
public interface PhoneNumberClient {

  String NUMBER_NAME = "number";

  /**
   * Obtain the PhoneNumber by the Number.
   *
   * @param number the number we are goint to search in external service.
   * @return Optional PhoneNumber obtained from third party service.
   */
  Optional<PhoneNumber> getPhoneNumber(String number);

}

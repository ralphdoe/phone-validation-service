package com.verizonmedia.phonevalidationservice.consumers;

import com.verizonmedia.phonevalidationservice.models.PhoneNumber;
import java.util.Optional;

/**
 * Class to communicate with Third Party Services to Obtain Phone Number Data.
 * @author Rafael López
 */
public interface PhoneNumberClient {

  /**
   * Obtain the PhoneNumber by the Number.
   *
   * @param number the number we are goint to search in external service.
   * @return Optional PhoneNumber obtained from third party service.
   */
  Optional<PhoneNumber> getPhoneNumber(String number);

}

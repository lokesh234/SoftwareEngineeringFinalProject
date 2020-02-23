package edu.wpi.cs3733.c20.teamU.Administration;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.warrenstrange.googleauth.GoogleAuthenticator;

public class TwoFactorSecurity {

  private final String ACCOUNT_SID = "AC6ca7d0c7bce631c61f076828d87819cc";
  private final String AUTH_TOKEN = "4c3eafeee2bd390eeaa8b1f89b272f2e";
  private GoogleAuthenticator authenticator;
  private int code;
  private String number;
  private String userName;

  // currently setup for single person use at a time...


  public TwoFactorSecurity(String userName, String number) {
    this.userName = userName;
    this.number = number;
    authenticator = new GoogleAuthenticator();
  }

  /**
   * function sends out verification code through SMS
   * @return false if user is empty, true if user exists
   */
  public boolean sendOutSMS() {
    if (userName.isEmpty()) {
      return false;
    }
    code = authenticator.createCredentials().getVerificationCode();
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    Message message = Message.creator(new PhoneNumber(number),
        new PhoneNumber("+18635786438"),
        String.valueOf(code)).create();
//      System.out.println(message.getSid());
    return true;
  }

  /**
   * not so secure way of checking credentials...will be removed later
   * @param userKey
   * @return
   */
  public boolean checkKey(String userKey) {
    if (userKey.equals(String.valueOf(code))) {
      return true;
    } else {
      return false;
    }
  }
  /**
   * create keys specific to user
   * @param string
   */
  public void createUserAccount(String string) {

  }
}

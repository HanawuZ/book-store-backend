package com.backend.app.shared.libraries.validator;
import java.util.regex.Pattern;
public class PatternMatch {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    private PatternMatch() {}

    public static Boolean isEmailValid(String email) {
        return patternMatches(email, EMAIL_PATTERN);
    }

    // -------+ Private Helper Methods +-------------------------------
    private static Boolean patternMatches(String text, String regexPattern) {
        return Pattern.compile(regexPattern)
          .matcher(text)
          .matches();
    }


}

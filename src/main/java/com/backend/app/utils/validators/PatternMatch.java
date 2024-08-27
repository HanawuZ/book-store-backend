package com.backend.app.utils.validators;
import java.util.regex.Pattern;

public class PatternMatch {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    private PatternMatch() {}

    private static Boolean patternMatches(String text, String regexPattern) {
        return Pattern.compile(regexPattern)
          .matcher(text)
          .matches();
    }

    public static Boolean validEmail(String email) {
        return patternMatches(email, EMAIL_PATTERN);
    }
}

package com.backend.app.shared.libraries.validator;

public class ValidateValue {
  
  private ValidateValue() {}

  // Check empty value for all primitive type
  public static boolean isEmpty(Object value) {
    if (value == null) {
      return true;
    }
    if (value instanceof String) {
      return ((String) value).isEmpty();
    }
    return false;
  }
  
}

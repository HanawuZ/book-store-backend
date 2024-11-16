package com.backend.app.shared.error;

public class ErrorMessage {

  public static String getErrorMessage(Exception exception) {
    return String.format("Internal server error: %s", exception.getMessage());
  }
}

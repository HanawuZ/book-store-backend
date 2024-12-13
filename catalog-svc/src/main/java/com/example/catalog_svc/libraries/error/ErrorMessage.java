package com.example.catalog_svc.libraries.error;

public class ErrorMessage {

  public static String getErrorMessage(Exception exception) {
    return String.format("Internal server error: %s", exception.getMessage());
  }
}
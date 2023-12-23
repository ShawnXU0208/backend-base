package com.shawn.backendbase.configuration;

/**
 * Regex patterns used for user input validation.
 */
public class RegexPatterns {

  public static final String USERNAME = "^[a-zA-Z0-9_-]{5,30}$";

  public static final String PASSWORD =
      "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,30}$";
}

package com.shawn.backendbase.controller.Exception;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * Customised Exception for Duplicated key Exception when inserting new record.
 */
public class DuplicatedKeyException extends RuntimeException {

  private static final Pattern MSG_PAT = Pattern.compile("Duplicate entry '(\\w+)' for key '(\\w+).(\\w+)_index'");

  public DuplicatedKeyException(final String errorMsg) {
    super(errorMsg);
  }

  /**
   * Refactor the original error message
   *
   * @param exception DataIntegrityViolationException
   * @return refactored error message
   */
  public static String refactoredMsg(final DataIntegrityViolationException exception) {
    final Matcher matcher = MSG_PAT.matcher(exception.getMostSpecificCause().getMessage());
    if (!matcher.find()) {
      return "Failed to add a duplicated data record";
    }
    return String.format("Failed to add a new %s with duplicated %s - '%s'", matcher.group(2), matcher.group(3), matcher.group(1));
  }

}

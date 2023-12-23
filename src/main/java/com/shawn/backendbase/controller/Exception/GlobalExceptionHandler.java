package com.shawn.backendbase.controller.Exception;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Global Exception Handler.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * When the received request is not valid, can't be converted to the object.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Object> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex) {
    Map<String, List<String>> body = new HashMap<>();

    List<String> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.toList());

    body.put("errors", errors);
    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }


  /**
   * When the data with duplicated key is trying to be inserted.
   */
  @ExceptionHandler(DuplicatedKeyException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseEntity<Object> handleDuplicatedKeyException(final DuplicatedKeyException ex) {
    Map<String, List<String>> body = new HashMap<>();

    body.put("errors", Collections.singletonList(ex.getMessage()));
    return new ResponseEntity<>(body, HttpStatus.CONFLICT);
  }
}

package com.asr.example.feature.hub.example.exception;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @SuppressWarnings("NullableProblems")
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {
    Map<String, Object> errorResponse = new HashMap<>();
    HashSet<Map<String, String>> issues = new HashSet<>();
    for (FieldError fieldError : ex.getFieldErrors()) {
      issues.add(Map.of("field", fieldError.getField(),
                        "message", String.format(
              "Error: %s `%s`, provided : `%s`", fieldError.getField(),
              fieldError.getDefaultMessage(), fieldError.getRejectedValue())
      ));
    }
    errorResponse.put("issues", issues);
    errorResponse.put("message", "Request Validation failed");
    errorResponse.put("timestamp", OffsetDateTime.now());
    errorResponse.put("detailedMessage", ex.getMessage());
    errorResponse.put("requestDescription", request.getDescription(false));
    return ResponseEntity.status(status).body(errorResponse);
  }

  @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
  @ExceptionHandler(DisabledFeatureException.class)
  Object handleDisabledFeature(
      DisabledFeatureException ex, WebRequest request) {
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("message", ex.getMessage());
    errorResponse.put("timestamp", OffsetDateTime.now());
    errorResponse.put("detailedMessage", ex.getMessage());
    errorResponse.put("requestDescription", request.getDescription(false));
    return errorResponse;
  }
}

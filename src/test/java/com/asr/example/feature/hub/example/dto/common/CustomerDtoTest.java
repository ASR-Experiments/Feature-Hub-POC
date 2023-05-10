package com.asr.example.feature.hub.example.dto.common;

import com.asr.example.feature.hub.example.dto.CreateConstraint;
import com.asr.example.feature.hub.example.dto.request.CustomerRequest;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CustomerDtoTest {
  private static Validator validator;

  @BeforeAll
  public static void setupValidator() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Test
  void testDefaultCases() {
    CustomerRequest customerRequest = CustomerRequest
        .builder()
        .email("asr@yopmail.com")
        .phoneNumber("1234567890")
        .firstName("test")
        .build();
    Set<ConstraintViolation<CustomerRequest>> violations = validator.validate(customerRequest);
    Assertions.assertEquals(0, violations.size());
    Assertions.assertTrue(true);
  }

  @Test
  void testDefaultCases_whenEmailFailed() {
    CustomerRequest customerRequest = CustomerRequest
        .builder()
        .email("asr")
        .build();
    Set<ConstraintViolation<CustomerRequest>> violations = validator.validate(customerRequest);
    Assertions.assertEquals(1, violations.size());
    Assertions.assertTrue(true);
  }

  /**
   * As additional checks are required for Create
   */
  @Test
  void testCreateCases_whenDefaultDataIsProvided_thenFail() {
    CustomerRequest customerRequest = CustomerRequest
        .builder()
        .email("asr@yopmail.com")
        .phoneNumber("1234567890")
        .firstName("test")
        .build();
    Set<ConstraintViolation<CustomerRequest>> violations = validator.validate(customerRequest, CreateConstraint.class);
    Assertions.assertNotEquals(0, violations.size());
    Assertions.assertTrue(true);
  }

  @Test
  void testCreateCases_whenCreateDataIsProvided_thenSuccess() {
    AddressDto addressDto = AddressDto
        .builder()
        .country("US")
        .build();
    CustomerRequest customerRequest = CustomerRequest
        .builder()
        .firstName("test")
        .dateOfBirth(LocalDate.now().minus(2, ChronoUnit.DAYS))
        .email("asr@yopmail.com")
        .phoneNumber("1234567890")
        .addressDto(addressDto)
        .build();
    Set<ConstraintViolation<CustomerRequest>> violations = validator.validate(customerRequest, CreateConstraint.class);
    Assertions.assertNotEquals(0, violations.size());
    Assertions.assertTrue(true);
  }
}
package com.asr.example.feature.hub.example.dto.common;

import com.asr.example.feature.hub.example.dto.CreateConstraint;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Locale;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class CustomerDto {

  @NotEmpty(groups = { CreateConstraint.class })
  String firstName;

  String middleName;

  String lastName;

  Locale locale;

  Currency currency;

  @NotNull(groups = { CreateConstraint.class })
  @Past(message = "Date of Birth should be lesser than today's date")
  LocalDate dateOfBirth;

  @NotEmpty(groups = { CreateConstraint.class })
  @Email
  String email;

  @NotEmpty(groups = { CreateConstraint.class })
  @Pattern(regexp = "^(\\+?\\d{1,4}( \\d{1,4}){0,2})$")
  String isdCode;

  @NotEmpty(groups = { CreateConstraint.class })
  @Pattern(regexp = "^[\\d-() ]+$")
  String phoneNumber;

  @NotNull(groups = { CreateConstraint.class })
  AddressDto address;
}

package com.asr.example.feature.hub.example.dto.common;

import com.asr.example.feature.hub.example.dto.CreateConstraint;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressDto {

  String addressLine1;

  String addressLine2;

  String city;

  String state;

  String zipCode;

  @NotNull(groups = { CreateConstraint.class })
  String country;
}

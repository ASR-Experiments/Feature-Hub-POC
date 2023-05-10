package com.asr.example.feature.hub.example.dto.response;

import com.asr.example.feature.hub.example.dto.common.CustomerDto;
import java.util.Date;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResponse extends CustomerDto {

  UUID id;

  Integer version;

  Date createdDate;

  Date lastModifiedDate;

  String createdBy;

  String modifiedBy;
}

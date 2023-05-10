package com.asr.example.feature.hub.example.mapper.entity;

import com.asr.example.feature.hub.example.dto.common.AddressDto;
import com.asr.example.feature.hub.example.dto.request.CustomerRequest;
import com.asr.example.feature.hub.example.entity.Customer;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.util.StringUtils;

@Mapper
public interface CustomerMapper {

  @interface MapAddress { }

  @Mapping(target = "zipCode", ignore = true)
  @Mapping(target = "state", ignore = true)
  @Mapping(target = "country", ignore = true)
  @Mapping(target = "city", ignore = true)
  @Mapping(target = "addressLine2", ignore = true)
  @Mapping(target = "addressLine1", ignore = true)
  @Mapping(target = "phoneNumberWithIsd", source = ".", qualifiedByName = { "mapPhone" })
  Customer mapEntity(CustomerRequest request);

  @Mapping(target = "phoneNumberWithIsd", ignore = true)
  @Mapping(target = "middleName", ignore = true)
  @Mapping(target = "locale", ignore = true)
  @Mapping(target = "lastName", ignore = true)
  @Mapping(target = "firstName", ignore = true)
  @Mapping(target = "email", ignore = true)
  @Mapping(target = "dateOfBirth", ignore = true)
  @Mapping(target = "currency", ignore = true)
  @MapAddress
  void mapAddress(@MappingTarget Customer.CustomerBuilder customer, AddressDto address);

  @AfterMapping
  default void customerAfterMapping(@MappingTarget Customer.CustomerBuilder customer, CustomerRequest request) {
    if (request == null) {
      return;
    }

    mapAddress(customer, request.getAddress());
  }

  @Named("mapPhone")
  default String mapPhone(String isdCode, String phoneNumber) {
    if (StringUtils.hasText(isdCode)) {
      return isdCode + "-" + phoneNumber;
    }
    return phoneNumber;
  }

  @Named("mapPhone")
  default String mapPhone(CustomerRequest request) {
    if (request == null) {
      return null;
    }
    return mapPhone(request.getIsdCode(), request.getPhoneNumber());
  }

}

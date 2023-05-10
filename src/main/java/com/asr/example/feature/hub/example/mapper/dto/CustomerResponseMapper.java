package com.asr.example.feature.hub.example.mapper.dto;

import com.asr.example.feature.hub.example.dto.response.CustomerResponse;
import com.asr.example.feature.hub.example.entity.Customer;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.util.StringUtils;

@Mapper
public interface CustomerResponseMapper {

  @Mapping(target = "address", source = ".")
  @Mapping(target = ".", source = ".", qualifiedByName = { "mapResponsePhone" })
  CustomerResponse mapResponse(Customer entity);

  @Named("mapResponsePhone")
  default void mapPhone(@MappingTarget CustomerResponse.CustomerResponseBuilder<?, ?> customer,
                        Customer entity) {
    if (entity == null) {
      return;
    }
    String isdCodeWithPhoneNumber = entity.getPhoneNumberWithIsd();
    if (isdCodeWithPhoneNumber.startsWith("+") && isdCodeWithPhoneNumber.contains("-")) {
      int lastHyphen = isdCodeWithPhoneNumber.lastIndexOf("-");
      String isdCode = isdCodeWithPhoneNumber.substring(0, lastHyphen);
      if (StringUtils.hasText(isdCode)) {
        customer.isdCode(isdCode);
      }
      String phone = isdCodeWithPhoneNumber.substring(lastHyphen + 1);
      if (StringUtils.hasText(phone)) {
        customer.phoneNumber(phone);
      }
    }
  }

  @AfterMapping
  default void customerResponseAfterMapping(
      @MappingTarget CustomerResponse.CustomerResponseBuilder<?, ?> customer,
      Customer entity) {
    mapPhone(customer, entity);
  }

}

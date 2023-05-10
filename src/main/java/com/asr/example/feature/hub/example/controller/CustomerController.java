package com.asr.example.feature.hub.example.controller;

import com.asr.example.feature.hub.example.dto.CreateConstraint;
import com.asr.example.feature.hub.example.dto.request.CustomerRequest;
import com.asr.example.feature.hub.example.dto.response.CustomerResponse;
import com.asr.example.feature.hub.example.service.CustomerService;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/v1/customer")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CustomerController {

  CustomerService customerService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CustomerResponse createCustomer(
      @RequestBody @Validated(CreateConstraint.class) CustomerRequest request) {
    return customerService.createCustomer(request);
  }

  @GetMapping("/{customerId}")
  public ResponseEntity<CustomerResponse> getCustomer(
      @PathVariable @Valid @NotNull UUID customerId
  ) {
    Optional<CustomerResponse> customer = customerService.getCustomer(customerId);
    return customer
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}

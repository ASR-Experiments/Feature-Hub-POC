package com.asr.example.feature.hub.example.service.impl;

import com.asr.example.feature.hub.example.dto.request.CustomerRequest;
import com.asr.example.feature.hub.example.dto.response.CustomerResponse;
import com.asr.example.feature.hub.example.entity.Customer;
import com.asr.example.feature.hub.example.mapper.dto.CustomerResponseMapper;
import com.asr.example.feature.hub.example.mapper.entity.CustomerMapper;
import com.asr.example.feature.hub.example.repository.CustomerRepository;
import com.asr.example.feature.hub.example.service.CustomerService;
import java.util.Optional;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

  CustomerRepository customerRepository;

  CustomerMapper customerMapper;

  CustomerResponseMapper customerResponseMapper;

  @Override
  public CustomerResponse createCustomer(CustomerRequest request) {
    Customer customer = customerMapper.mapEntity(request);
    Customer savedCustomer = customerRepository.save(customer);
    return customerResponseMapper.mapResponse(savedCustomer);
  }

  @Override
  public Optional<CustomerResponse> getCustomer(UUID customerId) {

    return customerRepository
        .findById(customerId)
        .map(customerResponseMapper::mapResponse);

  }

}

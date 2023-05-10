package com.asr.example.feature.hub.example.service;

import com.asr.example.feature.hub.example.dto.request.CustomerRequest;
import com.asr.example.feature.hub.example.dto.response.CustomerResponse;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
  CustomerResponse createCustomer(CustomerRequest request);

  Optional<CustomerResponse> getCustomer(UUID customerId);
}

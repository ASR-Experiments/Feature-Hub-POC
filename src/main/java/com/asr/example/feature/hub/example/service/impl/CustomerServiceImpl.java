package com.asr.example.feature.hub.example.service.impl;

import com.asr.example.feature.hub.example.dto.request.CustomerRequest;
import com.asr.example.feature.hub.example.dto.response.CustomerResponse;
import com.asr.example.feature.hub.example.entity.Customer;
import com.asr.example.feature.hub.example.exception.DisabledFeatureException;
import com.asr.example.feature.hub.example.mapper.dto.CustomerResponseMapper;
import com.asr.example.feature.hub.example.mapper.entity.CustomerMapper;
import com.asr.example.feature.hub.example.repository.CustomerRepository;
import com.asr.example.feature.hub.example.service.CustomerService;
import io.featurehub.client.ClientContext;
import io.featurehub.client.FeatureState;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.inject.Provider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

  CustomerRepository customerRepository;

  CustomerMapper customerMapper;

  CustomerResponseMapper customerResponseMapper;

  Provider<ClientContext> ctxProvider;

  @Override
  public CustomerResponse createCustomer(CustomerRequest request) {
    Customer customer = customerMapper.mapEntity(request);
    Customer savedCustomer = customerRepository.save(customer);
    return customerResponseMapper.mapResponse(savedCustomer);
  }

  @Override
  public Optional<CustomerResponse> getCustomer(UUID customerId) {

    return customerRepository.findById(customerId).map(customerResponseMapper::mapResponse);

  }

  @Override
  public Optional<CustomerResponse> deleteCustomer(UUID customerId) {

    ClientContext clientContext = ctxProvider.get();
    FeatureState deletionEnabled = clientContext.feature(() -> "DELETION_ENABLED");
//    FeatureState experimentalFeatureEnabled = clientContext.feature(() -> "EXPERIMENTAL_FEATURE_ENABLED");

    if (Boolean.FALSE.equals((Optional.ofNullable(deletionEnabled)
                  .map(FeatureState::getBoolean)
                  .orElse(Boolean.FALSE)
//              || Optional.ofNullable(experimentalFeatureEnabled)
//                         .map(FeatureState::getBoolean)
//                         .orElse(Boolean.FALSE)
    ))) {
      throw new DisabledFeatureException("DELETION_ENABLED");
    }

    Optional<Customer> customer = customerRepository.findById(customerId);
    if (customer.isEmpty()) {
      return Optional.empty();
    }
    customerRepository.delete(customer.get());
    return Optional.ofNullable(customerResponseMapper.mapResponse(customer.get()));
  }

  @Override
  public List<CustomerResponse> listCustomer(Pageable pageRequest) {
    return customerRepository
        .findAll(pageRequest)
        .stream().map(customerResponseMapper::mapResponse)
        .collect(Collectors.toList());
  }

}

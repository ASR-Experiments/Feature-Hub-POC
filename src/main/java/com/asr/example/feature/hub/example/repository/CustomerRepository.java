package com.asr.example.feature.hub.example.repository;

import com.asr.example.feature.hub.example.entity.Customer;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
package com.kunal_vats.customer_service.repository;

import com.kunal_vats.customer_service.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> { }

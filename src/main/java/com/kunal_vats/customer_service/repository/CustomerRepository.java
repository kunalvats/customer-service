package com.kunal_vats.customer_service.repository;

import com.kunal_vats.customer_service.entity.Customer;
import org.springframework.data.repository.CrudRepository;


public interface CustomerRepository extends CrudRepository<Customer, Long> { }

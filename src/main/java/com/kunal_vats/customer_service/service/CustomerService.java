package com.kunal_vats.customer_service.service;

import com.kunal_vats.customer_service.entity.Customer;
import com.kunal_vats.customer_service.enums.BillStatus;

import java.util.List;

public interface CustomerService {

    void createCustomer(Customer customerInfo);

    Customer getCustomer(Long id);

    List<Customer> getAllCustomers();

    List<Customer> getCustomersByBillStatus(BillStatus status);
    
}

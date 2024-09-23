package com.kunal_vats.customer_service.controller;


import com.kunal_vats.customer_service.entity.Customer;
import com.kunal_vats.customer_service.enums.BillStatus;
import com.kunal_vats.customer_service.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/")
    public ResponseEntity<String> createCustomer(@RequestBody Customer customerBody) {
        customerService.createCustomer(customerBody);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long customerId) {
        Customer customerInfo = customerService.getCustomer(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(customerInfo);
    }

    @GetMapping("/")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customerInfo = customerService.getAllCustomers();
        return ResponseEntity.status(HttpStatus.OK).body(customerInfo);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Customer>> getCustomersByStatus(@PathVariable("status") BillStatus status) {
        List<Customer> customerInfo = customerService.getCustomersByBillStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(customerInfo);
    }


}

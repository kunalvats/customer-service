package com.kunal_vats.customer_service.service.impl;

import com.kunal_vats.customer_service.entity.Customer;
import com.kunal_vats.customer_service.entity.response.Bill;
import com.kunal_vats.customer_service.enums.BillStatus;
import com.kunal_vats.customer_service.repository.CustomerRepository;
import com.kunal_vats.customer_service.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.kunal_vats.customer_service.constants.Constants.BillingServiceURI;


@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerServiceImpl implements CustomerService {

    private final RestTemplate restTemplate;
    private final CustomerRepository customerRepository;

    @Override
    public void createCustomer(Customer customerInfo) {
        customerRepository.save(customerInfo);
    }

    @Override
    public Customer getCustomer(Long id) {
        Optional<Customer> optionalCustomer =customerRepository.findById(id);
        if (optionalCustomer.isEmpty()) {
            log.error("Not Found");
            throw new NoSuchElementException(String.format("Customer not found for Id: %s", id));
        }
        return optionalCustomer.get();
    }

    @Override
    public List<Customer> getAllCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }

    @Override
    public List<Customer> getCustomersByBillStatus(BillStatus status) {
        List<Long> customerIds = new ArrayList<>();
        try {
            ResponseEntity<List<Bill>> responseEntity = restTemplate.exchange(String.format(BillingServiceURI, status.name()),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Bill>>() {
                    });


            customerIds = responseEntity.getBody().stream().map(Bill::getCustomerId).distinct().toList();
        } catch (NullPointerException e) {
            throw new NoSuchElementException(e);
        }
        return (List<Customer>) customerRepository.findAllById(customerIds);
    }
}

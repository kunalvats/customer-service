package com.kunal_vats.customer_service.service;

import com.kunal_vats.customer_service.entity.Customer;
import com.kunal_vats.customer_service.entity.response.Bill;
import com.kunal_vats.customer_service.enums.BillStatus;
import com.kunal_vats.customer_service.repository.CustomerRepository;
import com.kunal_vats.customer_service.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceImplTests {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCustomer_savesCustomer() {
        Customer customer = new Customer();
        customerService.createCustomer(customer);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void getCustomer_returnsCustomer_whenFound() {
        Customer customer = new Customer();
        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.of(customer));
        Customer result = customerService.getCustomer(1L);
        assertEquals(customer, result);
    }

    @Test
    void getCustomer_throwsException_whenNotFound() {
        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> customerService.getCustomer(1L));
    }

    @Test
    void getAllCustomers_returnsAllCustomers() {
        List<Customer> customers = Arrays.asList(new Customer(), new Customer());
        when(customerRepository.findAll()).thenReturn(customers);
        List<Customer> result = customerService.getAllCustomers();
        assertEquals(customers, result);
    }

    @Test
    void getCustomersByBillStatus_returnsCustomers_whenBillsFound() {
        Bill bill1 = new Bill();
        bill1.setCustomerId(1L);
        Bill bill2 = new Bill();
        bill2.setCustomerId(2L);
        List<Bill> bills = Arrays.asList(bill1, bill2);
        when(restTemplate.exchange(anyString(), any(), any(), any(ParameterizedTypeReference.class)))
                .thenReturn(new ResponseEntity<>(bills, HttpStatus.OK));
        List<Customer> customers = Arrays.asList(new Customer(), new Customer());
        when(customerRepository.findAllById(anyList())).thenReturn(customers);
        List<Customer> result = customerService.getCustomersByBillStatus(BillStatus.PAID);
        assertEquals(customers, result);
    }

    @Test
    void getCustomersByBillStatus_throwsException_whenNullPointerException() {
        when(restTemplate.exchange(anyString(), any(), any(), any(ParameterizedTypeReference.class)))
                .thenThrow(new NullPointerException());
        assertThrows(NoSuchElementException.class, () -> customerService.getCustomersByBillStatus(BillStatus.PAID));
    }
}
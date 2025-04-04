package com.example.batam1spa.customer.service;

import com.example.batam1spa.customer.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    void seedCustomer();
    List<Customer> getAllCustomer();
    Customer editCustomerNationality(UUID customerId, boolean isLocal);
}

package com.example.batam1spa.customer.service;

import com.example.batam1spa.customer.model.Customer;

import java.util.List;

public interface CustomerService {
    void seedCustomer();
    List<Customer> getAllCustomer();
}

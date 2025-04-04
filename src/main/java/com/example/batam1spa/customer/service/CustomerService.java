package com.example.batam1spa.customer.service;

import com.example.batam1spa.customer.dto.CustomerDTO;
import com.example.batam1spa.customer.model.Customer;
import com.example.batam1spa.user.model.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    void seedCustomer();
    List<Customer> getAllCustomer();
    Customer editCustomerNationality(UUID customerId, boolean isLocal);
    Page<CustomerDTO> getCustomersByPage(User user, int amountPerPage, int page);
}

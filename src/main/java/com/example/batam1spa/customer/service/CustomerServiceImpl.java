package com.example.batam1spa.customer.service;

import com.example.batam1spa.customer.model.Customer;
import com.example.batam1spa.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public void seedCustomer() {
        createCustomerIfNotExists("Angel", "12345678", "angel@gmail.com", true);
        createCustomerIfNotExists("Bill", "87654321", "bill@gmail.com", false);
    }

    private void createCustomerIfNotExists(String fullName, String phoneNumber, String email, boolean isLocal) {
        boolean customerExists = customerRepository.existsByPhoneNumber(phoneNumber);

        if (customerExists) {
            log.info("{} already exists in the system", phoneNumber);
            return;
        }

        Customer customer = Customer.builder()
                .fullName(fullName)
                .phoneNumber(phoneNumber)
                .email(email)
                .isLocal(isLocal)
                .build();

        customerRepository.save(customer);
        log.info("{} has been added to the system", fullName);
    }
}

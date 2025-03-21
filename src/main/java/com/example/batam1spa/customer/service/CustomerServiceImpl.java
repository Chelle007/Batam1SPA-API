package com.example.batam1spa.customer.service;

import com.example.batam1spa.customer.dto.EditCustomerRequest;
import com.example.batam1spa.customer.model.Customer;
import com.example.batam1spa.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public void seedCustomer() {
        createCustomerIfNotExists("Angel", "12345678", "angel@gmail.com", true, true);
        createCustomerIfNotExists("Bill", "87654321", "bill@gmail.com", false, false);
    }

    private void createCustomerIfNotExists(String fullName, String phoneNumber, String email, boolean isLocal, boolean isSubscribed) {
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
                .isSubscribed(isSubscribed)
                .build();

        customerRepository.save(customer);
        log.info("{} has been added to the system", fullName);
    }

    // Get all customer
    @Override
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    // Edit existing customer nationality (local/tourist)
    @Override
    public Customer editCustomerNationality(UUID customerId, EditCustomerRequest editCustomerRequestDTO) {
        // Find the existing customer record
        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));

        // Update the customer nationality
        modelMapper.map(editCustomerRequestDTO, existingCustomer);

        // Save the updated customer info
        return customerRepository.save(existingCustomer);
    }
}
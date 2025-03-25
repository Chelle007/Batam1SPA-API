package com.example.batam1spa.customer.service;

import com.example.batam1spa.customer.dto.CustomerDTO;
import com.example.batam1spa.customer.dto.EditCustomerRequest;
import com.example.batam1spa.customer.exception.CustomerExceptions;
import com.example.batam1spa.customer.model.Customer;
import com.example.batam1spa.customer.repository.CustomerRepository;
import com.example.batam1spa.security.service.RoleSecurityService;
import com.example.batam1spa.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final RoleSecurityService roleSecurityService;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final RoleSecurityService roleSecurityService;

    @Override
    public void seedCustomer() {
        createCustomerIfNotExists("Angel", "12345678", "angel@gmail.com", true, true);
        createCustomerIfNotExists("Bill", "87654321", "bill@gmail.com", false, false);
    }

    private void createCustomerIfNotExists(String fullName, String phoneNumber, String email, boolean isLocal, boolean isSubscribed) {
        boolean customerExists = customerRepository.existsByPhoneNumber(phoneNumber);

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

    @Override
    public Page<CustomerDTO> getCustomersByPage(User user, int amountPerPage, int page) {
        roleSecurityService.checkRole(user, "ROLE_ADMIN");
        Pageable pageable = PageRequest.of(page, amountPerPage);
        Page<Customer> customerPage = customerRepository.findAll(pageable);

        List<CustomerDTO> customerDTOList = customerPage.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(customerDTOList, pageable, customerPage.getTotalElements());
    }

    // Helper method to convert Customer to CustomerDTO
    private CustomerDTO convertToDTO(Customer customer) {
        return CustomerDTO.builder()
                .customerId(customer.getId())
                .fullName(customer.getFullName())
                .phoneNumber(customer.getPhoneNumber())
                .email(customer.getEmail())
                .isLocal(customer.isLocal())
                .isSubscribed(customer.isSubscribed())
                .build();
    }

    // Edit existing customer nationality (local/tourist)
    @Override
    public Customer editCustomerNationality(UUID customerId, EditCustomerRequest editCustomerRequestDTO) {
        // Find the existing customer record
        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerExceptions.CustomerNotFound("Customer not found with id: " + customerId));

        // Update the customer nationality
        modelMapper.map(editCustomerRequestDTO, existingCustomer);

        // Save the updated customer info
        return customerRepository.save(existingCustomer);
    }
}
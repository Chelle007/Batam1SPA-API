package com.example.batam1spa.customer.controller;

import com.example.batam1spa.common.dto.BaseResponse;
import com.example.batam1spa.customer.service.CustomerService;
import com.example.batam1spa.customer.model.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private CustomerService customerService;

    // Get all customer members Full URI: /api/customer/get-all-customer
    @GetMapping("/get-all-customer")
    public ResponseEntity<BaseResponse<List<Customer>>> getAllCustomer() {
        List<Customer> allCustomer = customerService.getAllCustomer();

        // Wrap the response in BaseResponse
        BaseResponse<List<Customer>> response = BaseResponse.success(
                HttpStatus.OK, allCustomer, "Fetched all customers successfully");

        return ResponseEntity.ok(response);
    }

    // Edit an existing customer member Full URI: /api/v1/customer/edit/{customerId}
    @PutMapping("/edit/{customerId}")
    public ResponseEntity<BaseResponse<Customer>> editCustomerNationality(@PathVariable UUID customerId, @RequestParam boolean isLocal) {
        Customer updatedCustomer = customerService.editCustomerNationality(customerId, isLocal);

        // Wrap the response in BaseResponse
        BaseResponse<Customer> response = BaseResponse.success(
                HttpStatus.OK, updatedCustomer, "Customer edited successfully");

        return ResponseEntity.ok(response);
    }
}

package com.example.batam1spa.customer.controller;

import com.example.batam1spa.common.dto.BaseResponse;
import com.example.batam1spa.customer.dto.CustomerDTO;
import com.example.batam1spa.customer.dto.EditCustomerRequest;
import com.example.batam1spa.customer.service.CustomerService;
import com.example.batam1spa.customer.model.Customer;
import com.example.batam1spa.user.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    // Get all customer members Full URI: /api/customer/get-all-customer
    @GetMapping("/get-all-customer")
    public ResponseEntity<BaseResponse<List<Customer>>> getAllCustomer() {
        List<Customer> allCustomer = customerService.getAllCustomer();

        // Wrap the response in BaseResponse
        BaseResponse<List<Customer>> response = BaseResponse.success(
                HttpStatus.OK, allCustomer, "Fetched all customers successfully");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-customer-page")
    public ResponseEntity<BaseResponse<Page<CustomerDTO>>> getCustomersByPage(
            @RequestParam int page,
            @RequestParam int amountPerPage,
            @AuthenticationPrincipal User user){
        Page<CustomerDTO> customerPage = customerService.getCustomersByPage(user, amountPerPage, page);

        BaseResponse<Page<CustomerDTO>> response = BaseResponse.success(
                HttpStatus.OK, customerPage, "Customer fetched successfully"
        );

        return ResponseEntity.ok(response);
    }

    // Edit an existing customer member Full URI: /api/v1/customer/edit/{customerId}
    @PutMapping("/edit/{customerId}")
    public ResponseEntity<BaseResponse<Customer>> editCustomerNationality(@PathVariable UUID customerId, @RequestBody EditCustomerRequest editCustomerRequestDTO) {
        Customer updatedCustomer = customerService.editCustomerNationality(customerId, editCustomerRequestDTO);

        // Wrap the response in BaseResponse
        BaseResponse<Customer> response = BaseResponse.success(
                HttpStatus.OK, updatedCustomer, "Customer edited successfully");

        return ResponseEntity.ok(response);
    }
}

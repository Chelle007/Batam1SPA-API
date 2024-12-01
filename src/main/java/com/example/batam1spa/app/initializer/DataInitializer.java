package com.example.batam1spa.app.initializer;

import com.example.batam1spa.customer.service.CustomerService;
import com.example.batam1spa.order.service.OrderService;
import com.example.batam1spa.service.service.ServiceDescriptionService;
import com.example.batam1spa.service.service.ServicePriceService;
import com.example.batam1spa.service.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.batam1spa.user.service.UserService;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final CustomerService customerService;
    private final OrderService orderService;
    private final ServiceService serviceService;
    private final ServiceDescriptionService serviceDescriptionService;
    private final ServicePriceService servicePriceService;

    @Override
    public void run(String... args) {
        userService.seedUser();
        customerService.seedCustomer();
        orderService.seedOrder();
        serviceService.seedService();
        serviceDescriptionService.seedServiceDescription();
        servicePriceService.seedServicePrice();
    }
}

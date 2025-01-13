package com.example.batam1spa.order.service;

import com.example.batam1spa.availability.model.Time;
import com.example.batam1spa.order.model.Order;
import com.example.batam1spa.order.model.OrderDetail;
import com.example.batam1spa.order.repository.OrderDetailRepository;
import com.example.batam1spa.order.repository.OrderRepository;
import com.example.batam1spa.service.model.Service;
import com.example.batam1spa.service.repository.ServiceRepository;
import com.example.batam1spa.staff.model.Staff;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Slf4j
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ServiceRepository serviceRepository;

    @Override
    public void seedOrderDetail() {
        Service service1 = serviceRepository.findByName("Head Massage").orElse(null);
        Service service2 = serviceRepository.findByName("Body Massage").orElse(null);
        Service service3 = serviceRepository.findByName("Foot Massage").orElse(null);
        Service service4 = serviceRepository.findByName("Manipedi").orElse(null);

    }

    private void createOrderDetailIfNotExists(Order order, Service service, Staff staff, Time startTime, Time endTime, boolean isCompleted) {
        boolean orderDetailExists = orderDetailRepository.existsByOrderAndService(order, service);

        if (orderDetailExists) {
            log.info("order detail with order {} and service {} already exists in the system", order, service);
            return;
        }

        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .service(service)
                .staff(staff)
                .startTime(startTime)
                .endTime(endTime)
                .isCompleted(isCompleted)
                .build();

        orderDetailRepository.save(orderDetail);
        log.info("order detail with order {} and service {} has been added to the system", order, service);
    }
}

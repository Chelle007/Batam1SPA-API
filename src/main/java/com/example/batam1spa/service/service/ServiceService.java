package com.example.batam1spa.service.service;

import com.example.batam1spa.service.dto.ServiceRequest;

import java.util.List;

public interface ServiceService {
    void seedService();
    List<ServiceRequest> getAllService();
}

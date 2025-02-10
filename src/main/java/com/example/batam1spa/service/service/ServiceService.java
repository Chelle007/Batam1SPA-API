package com.example.batam1spa.service.service;

import com.example.batam1spa.service.dto.CreateServiceRequest;
import com.example.batam1spa.service.dto.ServiceRequest;
import com.example.batam1spa.service.dto.EditServiceRequest;
import com.example.batam1spa.service.model.Service;

import java.util.List;
import java.util.UUID;

public interface ServiceService {
    void seedService();
    List<ServiceRequest> getAllService();
    Service addService(CreateServiceRequest createServiceRequest);
    Service editService(UUID serviceId, EditServiceRequest editServiceRequest);
}
package com.example.batam1spa.service.service;

import com.example.batam1spa.service.dto.CreateServiceRequest;
import com.example.batam1spa.service.dto.ServiceRequest;
import com.example.batam1spa.service.dto.EditServiceRequest;
import com.example.batam1spa.service.model.Service;
import com.example.batam1spa.user.model.User;

import java.util.List;
import java.util.UUID;

public interface ServiceService {
    void seedService();
    List<ServiceRequest> getAllService(User user);
    Service addService(User user, CreateServiceRequest createServiceRequest);
    Service editService(User user, UUID serviceId, EditServiceRequest editServiceRequest);
}
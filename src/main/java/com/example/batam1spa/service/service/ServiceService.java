package com.example.batam1spa.service.service;

import com.example.batam1spa.service.dto.*;
import com.example.batam1spa.service.model.Service;
import com.example.batam1spa.user.model.User;

import java.util.List;
import java.util.UUID;

public interface ServiceService {
    void seedService();
    List<ServiceRequest> getAllService(User user);
    Service addService(User user, CreateServiceRequest createServiceRequest);
    Service editService(User user, UUID serviceId, EditServiceRequest editServiceRequest);
    Service toggleServiceStatus(User user, UUID serviceId);

    // Customer side
    ServiceDetailsDTO getServiceDetails(UUID serviceId, String lang);
    GetServicesPaginationResponse getServicesByPage(int amountPerPage, int page);
}
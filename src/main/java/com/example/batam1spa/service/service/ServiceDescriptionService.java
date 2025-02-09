package com.example.batam1spa.service.service;

import com.example.batam1spa.service.model.ServiceDescription;

import java.util.List;

public interface ServiceDescriptionService {
    void seedServiceDescription();
    List<ServiceDescription> getAllServiceDescriptions();
}

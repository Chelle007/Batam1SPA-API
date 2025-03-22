package com.example.batam1spa.service.service;

import com.example.batam1spa.service.model.ServiceDescription;
import com.example.batam1spa.user.model.User;

import java.util.List;

public interface ServiceDescriptionService {
    void seedServiceDescription();
    List<ServiceDescription> getAllServiceDescriptions(User user);
}

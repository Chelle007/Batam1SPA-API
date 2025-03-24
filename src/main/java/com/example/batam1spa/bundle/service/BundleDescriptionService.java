package com.example.batam1spa.bundle.service;

import com.example.batam1spa.bundle.model.BundleDescription;
import com.example.batam1spa.user.model.User;

import java.util.List;

public interface BundleDescriptionService {
    void seedBundleDescription();
    List<BundleDescription> getAllBundleDescriptions(User user);
}

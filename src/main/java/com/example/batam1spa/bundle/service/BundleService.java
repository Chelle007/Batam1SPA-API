package com.example.batam1spa.bundle.service;

import com.example.batam1spa.bundle.dto.BundleDTO;
import com.example.batam1spa.bundle.dto.CreateBundleDTO;
import com.example.batam1spa.user.model.User;

import java.util.List;

public interface BundleService {
    void seedBundle();
    List<BundleDTO> getAllBundle(User user);
//    BundleDTO addBundle(User user, CreateBundleDTO createBundleDTO);
}

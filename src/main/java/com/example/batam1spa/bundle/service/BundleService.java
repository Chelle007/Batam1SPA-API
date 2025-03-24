package com.example.batam1spa.bundle.service;

import com.example.batam1spa.bundle.dto.*;
import com.example.batam1spa.bundle.model.Bundle;
import com.example.batam1spa.user.model.User;

import java.util.List;
import java.util.UUID;

public interface BundleService {
    void seedBundle();
    List<BundleDTO> getAllBundle(User user);
    BundleDTO addBundle(User user, CreateBundleDTO createBundleDTO);
    BundleDTO editBundle(User user, UUID bundleId, EditBundleDTO editBundleDTO);
    Bundle toggleBundleStatus(User user, UUID bundleId);
    GetBundlesPaginationResponse getBundlesByPage(int amountPerPage, int page, String searchQuery);
}

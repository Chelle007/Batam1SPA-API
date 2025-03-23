package com.example.batam1spa.bundle.service;

import com.example.batam1spa.bundle.dto.BundleContentDTO;
import com.example.batam1spa.bundle.dto.BundleDTO;
import com.example.batam1spa.bundle.model.Bundle;
import com.example.batam1spa.bundle.model.BundleDetail;
import com.example.batam1spa.bundle.repository.BundleDetailRepository;
import com.example.batam1spa.bundle.repository.BundleRepository;
import com.example.batam1spa.security.service.RoleSecurityService;
import com.example.batam1spa.service.model.ServicePrice;
import com.example.batam1spa.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BundleServiceImpl implements BundleService {
    private final RoleSecurityService roleSecurityService;
    private final BundleRepository bundleRepository;
    private final BundleDetailRepository bundleDetailRepository;

    @Override
    public void seedBundle() {
        createBundleIfNotExists("Head & Body - 2h", "headAndBody.png", 750000, 800000, true);
        createBundleIfNotExists("Foot & Body - 2h", "footAndBody.png", 950000, 1000000, true);
        createBundleIfNotExists("Full Body Relax - 1.5h", "fullBodyRelax.png", 650000, 700000, true);
        createBundleIfNotExists("Hot Stone Therapy - 2h", "hotStoneTherapy.png", 1200000, 1300000, true);
        createBundleIfNotExists("Aromatherapy Massage - 1h", "aromatherapy.png", 550000, 600000, false);
        createBundleIfNotExists("Deep Tissue Massage - 1.5h", "deepTissue.png", 850000, 900000, true);
        createBundleIfNotExists("Foot Reflexology - 1h", "footReflexology.png", 500000, 550000, false);
    }

    private void createBundleIfNotExists(String name, String imgUrl, int localPrice, int touristPrice, boolean isPublished) {
        boolean bundleExists = bundleRepository.existsByName(name);

        if (bundleExists) {
            log.info("{} already exists in the system", name);
            return;
        }

        Bundle bundle = Bundle.builder()
                .name(name)
                .imgUrl(imgUrl)
                .localPrice(localPrice)
                .touristPrice(touristPrice)
                .isPublished(isPublished)
                .build();

        bundleRepository.save(bundle);
        log.info("{} has been added to the system", name);
    }

    @Override
    public List<BundleDTO> getAllBundle(User user) {
        // Ensure only admins can access this API
        roleSecurityService.checkRole(user, "ROLE_ADMIN");

        // Fetch all bundles
        List<Bundle> bundles = bundleRepository.findAll();

        return bundles.stream().map(bundle -> {
            // Fetch all details for this bundle
            List<BundleDetail> bundleDetails = bundleDetailRepository.findByBundle(bundle);

            // Compute total duration & bundle content
            int totalDuration = 0;
            Map<String, BundleContentDTO> bundleContent = new LinkedHashMap<>(); // Preserve order

            for (BundleDetail detail : bundleDetails) {
                ServicePrice servicePrice = detail.getServicePrice();
                String serviceName = servicePrice.getService().getName();
                int duration = servicePrice.getDuration();
                int quantity = detail.getQuantity();

                totalDuration += duration;

                // Store duration & quantity using BundleContentDTO
                bundleContent.put(serviceName, BundleContentDTO.builder()
                        .duration(duration)
                        .quantity(quantity)
                        .build());
            }

            return BundleDTO.builder()
                    .bundleId(bundle.getId())
                    .bundleName(bundle.getName())
                    .totalDuration(totalDuration)
                    .bundleContent(bundleContent)
                    .imgUrl(bundle.getImgUrl())
                    .localPrice(bundle.getLocalPrice())
                    .touristPrice(bundle.getTouristPrice())
                    .isPublished(bundle.isPublished())
                    .build();
        }).collect(Collectors.toList());
    }

}

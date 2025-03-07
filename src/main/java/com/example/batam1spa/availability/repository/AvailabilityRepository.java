package com.example.batam1spa.availability.repository;

import com.example.batam1spa.availability.model.Availability;
import com.example.batam1spa.availability.model.TimeSlot;
import com.example.batam1spa.service.model.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, UUID> {
    boolean existsByDateAndTimeSlotAndServiceType(LocalDate date, TimeSlot timeSlot, ServiceType serviceType);
    Optional<Availability> findByDateAndTimeSlotAndServiceType(LocalDate date, TimeSlot timeSlot, ServiceType serviceType);
}

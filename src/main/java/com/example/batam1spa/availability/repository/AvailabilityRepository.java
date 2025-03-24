package com.example.batam1spa.availability.repository;

import com.example.batam1spa.availability.model.Availability;
import com.example.batam1spa.availability.model.TimeSlot;
import com.example.batam1spa.service.model.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, UUID> {

    boolean existsByDateAndTimeSlotAndServiceType(LocalDate date, TimeSlot timeSlot, ServiceType serviceType);

    Optional<Availability> findByDateAndTimeSlotAndServiceType(LocalDate date, TimeSlot timeSlot, ServiceType serviceType);

    @Transactional
    @Modifying
    @Query("DELETE FROM Availability a WHERE a.date < :date")
    void deleteByDateBefore(@Param("date") LocalDate date);

    List<Availability> findByDateAndServiceType(LocalDate serviceDate, ServiceType serviceType);
}

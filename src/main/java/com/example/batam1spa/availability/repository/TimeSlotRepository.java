package com.example.batam1spa.availability.repository;

import com.example.batam1spa.availability.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.UUID;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, UUID> {
    // for testing purpose (seeder)
    Boolean existsByLocalTime(LocalTime localTime);
    TimeSlot findByLocalTime(LocalTime localTime);
}

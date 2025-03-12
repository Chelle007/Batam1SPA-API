package com.example.batam1spa.availability.repository;

import com.example.batam1spa.availability.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, UUID> {
    // for testing purpose (seeder)
    Boolean existsByLocalTime(LocalTime localTime);

    TimeSlot findByLocalTime(LocalTime localTime);

    @Query("SELECT t FROM TimeSlot t WHERE t.localTime BETWEEN :startTime AND :endTime ORDER BY t.localTime ASC")
    List<TimeSlot> findTimeSlotsBetween(@Param("startTime") LocalTime startTime, @Param("endTime") LocalTime endTime);
}

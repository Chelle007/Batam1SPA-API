package com.example.batam1spa.availability.service;

import com.example.batam1spa.availability.model.TimeSlot;
import com.example.batam1spa.availability.repository.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TimeSlotServiceImpl implements TimeSlotService {
    private final TimeSlotRepository timeSlotRepository;

    @Override
    public void seedTimeSlot() {
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(21, 0);
        while (!startTime.isAfter(endTime)) {
            createTimeSlotIfNotExists(startTime);
            startTime = startTime.plusMinutes(30);
        }
    }

    private void createTimeSlotIfNotExists(LocalTime localTime) {
        boolean timeSlotExisted = timeSlotRepository.existsByLocalTime(localTime);

        if (timeSlotExisted) {
            log.info("Time slot {} already exists.", localTime);
            return;
        }

        TimeSlot timeSlot = TimeSlot.builder()
                .localTime(localTime)
                .build();

        timeSlotRepository.save(timeSlot);
        log.info("Time slot {} has been added to the system.", localTime);

    }
}

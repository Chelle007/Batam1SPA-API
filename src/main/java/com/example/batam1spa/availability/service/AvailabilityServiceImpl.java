package com.example.batam1spa.availability.service;

import com.example.batam1spa.availability.dto.GetServiceAvailabilityRequest;
import com.example.batam1spa.availability.exception.AvailabilityExceptions;
import com.example.batam1spa.availability.model.Availability;
import com.example.batam1spa.availability.model.TimeSlot;
import com.example.batam1spa.availability.repository.AvailabilityRepository;
import com.example.batam1spa.availability.repository.TimeSlotRepository;
import com.example.batam1spa.service.model.Service;
import com.example.batam1spa.service.model.ServiceType;
import com.example.batam1spa.service.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Slf4j
public class AvailabilityServiceImpl implements AvailabilityService {
    private final AvailabilityRepository availabilityRepository;
    private final ServiceRepository serviceRepository;
    private final TimeSlotRepository timeSlotRepository;

    @Override
    public void generateAvailabilityForNextTwoWeeks() {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusWeeks(2);

        availabilityRepository.deleteByServiceDateBefore(today);

        List<TimeSlot> timeSlots = timeSlotRepository.findAll();

        for (LocalDate date = today; !date.isAfter(endDate); date = date.plusDays(1)) {
            for (TimeSlot timeSlot : timeSlots) {
                boolean manipediExists = availabilityRepository.existsByDateAndTimeSlotAndServiceType(date, timeSlot, ServiceType.MANIPEDI);
                if (!manipediExists) {
                    Availability availability = Availability.builder()
                            .date(date)
                            .timeSlot(timeSlot)
                            .serviceType(ServiceType.MANIPEDI)
                            .count(30)
                            .build();

                    availabilityRepository.save(availability);
                }

                boolean massageExists = availabilityRepository.existsByDateAndTimeSlotAndServiceType(date, timeSlot, ServiceType.MASSAGE);
                if (!massageExists) {
                    Availability availability = Availability.builder()
                            .date(date)
                            .timeSlot(timeSlot)
                            .serviceType(ServiceType.MASSAGE)
                            .count(50)
                            .build();

                    availabilityRepository.save(availability);
                }
            }
        }

    }

    @Scheduled(cron = "0 0 0 * * ?") // generate every day at 12 am
    public void scheduleAvailabilityGeneration() {
        generateAvailabilityForNextTwoWeeks();
    }

    @Override
    public Boolean getServiceAvailability(GetServiceAvailabilityRequest getServiceAvailabilityRequest) {
        Service service = serviceRepository.findById(getServiceAvailabilityRequest.getServiceId()).orElseThrow(() -> new AvailabilityExceptions.OrderNotFound("Service with ID: " + getServiceAvailabilityRequest.getServiceId() + " not found."));
        TimeSlot timeSlot = timeSlotRepository.findById(getServiceAvailabilityRequest.getTimeId()).orElseThrow(() -> new AvailabilityExceptions.TimeSlotNotFound("TimeSlot with ID: " + getServiceAvailabilityRequest.getTimeId() + " not found."));
        Availability availability = availabilityRepository.findByDateAndTimeSlotAndServiceType(getServiceAvailabilityRequest.getServiceDate(), timeSlot, service.getServiceType()).orElseThrow(() -> new AvailabilityExceptions.AvailabilityNotFound("Availability not found."));
        return availability.getCount() > 0;
    }
}

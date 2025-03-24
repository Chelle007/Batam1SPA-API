package com.example.batam1spa.availability.service;

import com.example.batam1spa.availability.dto.GetServiceAvailabileDateRequest;
import com.example.batam1spa.availability.dto.GetServiceAvailabileTimeSlotRequest;
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
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Slf4j
public class AvailabilityServiceImpl implements AvailabilityService {
    private final AvailabilityRepository availabilityRepository;
    private final ServiceRepository serviceRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final ModelMapper modelMapper;

    @Override
    public void generateAvailabilityForNextTwoWeeks() {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusWeeks(2);

        availabilityRepository.deleteByDateBefore(today);

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
    public int getServiceAvailability(GetServiceAvailabilityRequest getServiceAvailabilityRequest) {
        Service service = serviceRepository.findById(getServiceAvailabilityRequest.getServiceId()).orElseThrow(() -> new AvailabilityExceptions.OrderNotFound("Service with ID: " + getServiceAvailabilityRequest.getServiceId() + " not found."));
        TimeSlot timeSlot = timeSlotRepository.findById(getServiceAvailabilityRequest.getTimeId()).orElseThrow(() -> new AvailabilityExceptions.TimeSlotNotFound("TimeSlot with ID: " + getServiceAvailabilityRequest.getTimeId() + " not found."));
        Availability availability = availabilityRepository.findByDateAndTimeSlotAndServiceType(getServiceAvailabilityRequest.getServiceDate(), timeSlot, service.getServiceType()).orElseThrow(() -> new AvailabilityExceptions.AvailabilityNotFound("Availability not found."));
        return availability.getCount();
    }

    @Override
    public List<LocalDate> getServiceAvailabileDate(GetServiceAvailabileDateRequest getServiceAvailabileDateRequest) {
        GetServiceAvailabileTimeSlotRequest getServiceAvailabileTimeSlotRequest = modelMapper.map(getServiceAvailabileDateRequest, GetServiceAvailabileTimeSlotRequest.class);
        List<LocalDate> availableDates = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate twoWeeksLater = today.plusWeeks(2);

        for (LocalDate date = today; !date.isAfter(twoWeeksLater); date = date.plusDays(1)) {
            getServiceAvailabileTimeSlotRequest.setServiceDate(date);
            List<TimeSlot> availableTimeSlots = getServiceAvailabileTimeSlot(getServiceAvailabileTimeSlotRequest);

            if (!availableTimeSlots.isEmpty()) {
                availableDates.add(date);
            }
        }

        return availableDates;
    }

    @Override
    public List<TimeSlot> getServiceAvailabileTimeSlot(GetServiceAvailabileTimeSlotRequest getServiceAvailabileTimeSlotRequest) {
        List<TimeSlot> timeSlots = timeSlotRepository.findAll();
        Service service = serviceRepository.findById(getServiceAvailabileTimeSlotRequest.getServiceId()).orElseThrow(() -> new AvailabilityExceptions.OrderNotFound("Service with ID: " + getServiceAvailabileTimeSlotRequest.getServiceId() + " not found."));
        List<Availability> availabilities = availabilityRepository.findByDateAndServiceType(getServiceAvailabileTimeSlotRequest.getServiceDate(), service.getServiceType());
        Map<TimeSlot, Availability> availabilityMap = availabilities.stream()
                .collect(Collectors.toMap(Availability::getTimeSlot, availability -> availability));

        int pax = getServiceAvailabileTimeSlotRequest.getPax();
        int duration = getServiceAvailabileTimeSlotRequest.getDuration();
        int timeSlotCount = duration/30;
        List<TimeSlot> availableTimeSlots = new ArrayList<>();

        // VALIDATION
        if (pax <= 0 || pax > 4) {
            throw new AvailabilityExceptions.InvalidPax("Pax must be between 1 to 4: " + pax);
        }
        if (duration != 60 && duration != 90 && duration != 120) {
            throw new AvailabilityExceptions.InvalidDuration("Duration must be 60/90/120: " + duration);
        }

        // CHECK AVAILABLE TIME SLOT
        for (int i = 0; i < timeSlots.size() - timeSlotCount; i++) {
            boolean allAvailable = true;
            for (int j = 0; j < timeSlotCount; j++) {
                TimeSlot timeSlot = timeSlots.get(i+j);
                Availability availability = availabilityMap.get(timeSlot);
                if (availability == null || availability.getCount() <= pax) {
                    allAvailable = false;
                    break;
                }
            }
            if (allAvailable) {
                availableTimeSlots.add(timeSlots.get(i));
            }
        }

        return availableTimeSlots;
    }
}

package com.example.batam1spa.staff.model;

import com.example.batam1spa.service.model.ServiceType;
import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long staffId;

    private String fullName;
    private String gender;

    @Enumerated(EnumType.STRING) // Map enum to a string in the database
    private ServiceType serviceType;

    private LocalTime startTimeId;
    private LocalTime endTimeId;

    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;

    private int workCount;
    private boolean isActive;

    // Constructor
//    public Staff() {
//    }

    public Staff(Long staffId, String fullName, String gender, ServiceType serviceType, LocalTime startTimeId, LocalTime endTimeId, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday, int workCount, boolean isActive) {
        this.staffId = staffId;
        this.fullName = fullName;
        this.gender = gender;
        this.serviceType = serviceType;
        this.startTimeId = startTimeId;
        this.endTimeId = endTimeId;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.workCount = workCount;
        this.isActive = isActive;
    }

//    // Getters and Setters
//    public Long getStaffId() {
//        return staffId;
//    }
//
//    public void setStaffId(Long staffId) {
//        this.staffId = staffId;
//    }
//
//    public String getFullName() {
//        return fullName;
//    }
//
//    public void setFullName(String fullName) {
//        this.fullName = fullName;
//    }
//
//    public String getGender() {
//        return gender;
//    }
//
//    public void setGender(String gender) {
//        this.gender = gender;
//    }
//
//    public ServiceType getServiceType() {
//        return serviceType;
//    }
//
//    public void setServiceType(ServiceType serviceType) {
//        this.serviceType = serviceType;
//    }
//
//    public LocalTime getStartTimeId() {
//        return startTimeId;
//    }
//
//    public void setStartTimeId(LocalTime startTimeId) {
//        this.startTimeId = startTimeId;
//    }
//
//    public LocalTime getEndTimeId() {
//        return endTimeId;
//    }
//
//    public void setEndTimeId(LocalTime endTimeId) {
//        this.endTimeId = endTimeId;
//    }
//
//    public boolean isMonday() {
//        return monday;
//    }
//
//    public void setMonday(boolean monday) {
//        this.monday = monday;
//    }
//
//    public boolean isTuesday() {
//        return tuesday;
//    }
//
//    public void setTuesday(boolean tuesday) {
//        this.tuesday = tuesday;
//    }
//
//    public boolean isWednesday() {
//        return wednesday;
//    }
//
//    public void setWednesday(boolean wednesday) {
//        this.wednesday = wednesday;
//    }
//
//    public boolean isThursday() {
//        return thursday;
//    }
//
//    public void setThursday(boolean thursday) {
//        this.thursday = thursday;
//    }
//
//    public boolean isFriday() {
//        return friday;
//    }
//
//    public void setFriday(boolean friday) {
//        this.friday = friday;
//    }
//
//    public boolean isSaturday() {
//        return saturday;
//    }
//
//    public void setSaturday(boolean saturday) {
//        this.saturday = saturday;
//    }
//
//    public boolean isSunday() {
//        return sunday;
//    }
//
//    public void setSunday(boolean sunday) {
//        this.sunday = sunday;
//    }
//
//    public int getWorkCount() {
//        return workCount;
//    }
//
//    public void setWorkCount(int workCount) {
//        this.workCount = workCount;
//    }
//
//    public boolean isActive() {
//        return isActive;
//    }
//
//    public void setActive(boolean active) {
//        isActive = active;
//    }
}
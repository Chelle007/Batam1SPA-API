package com.example.batam1spa.staff.model;

import com.example.batam1spa.availability.model.TimeSlot;
import com.example.batam1spa.common.model.Auditable;
import com.example.batam1spa.common.model.Gender;
import com.example.batam1spa.service.model.ServiceType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_staffs")
public class Staff extends Auditable {
//    TODO
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(
//            name = "shift_id",
//            nullable = false,
//            referencedColumnName = "id"
//    )
//    private Shift shift

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private ServiceType serviceType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "start_time_slot_id",
            nullable = false,
            referencedColumnName = "id"
    )
    private TimeSlot startTimeSlot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "end_time_slot_id",
            nullable = false,
            referencedColumnName = "id"
    )
    private TimeSlot endTimeSlot;

    @Column(
            nullable = false,
            columnDefinition = "boolean default false"
    )
    private boolean isMonday;

    @Column(
            nullable = false,
            columnDefinition = "boolean default false"
    )
    private boolean isTuesday;

    @Column(
            nullable = false,
            columnDefinition = "boolean default false"
    )
    private boolean isWednesday;

    @Column(
            nullable = false,
            columnDefinition = "boolean default false"
    )
    private boolean isThursday;

    @Column(
            nullable = false,
            columnDefinition = "boolean default false"
    )
    private boolean isFriday;

    @Column(
            nullable = false,
            columnDefinition = "boolean default false"
    )
    private boolean isSaturday;

    @Column(
            nullable = false,
            columnDefinition = "boolean default false"
    )
    private boolean isSunday;

    @Column(
            nullable = false,
            columnDefinition = "boolean default true"
    )
    private boolean isActive;

    // Getters and Setters

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public boolean isMonday() {
        return isMonday;
    }

    public void setMonday(boolean monday) {
        isMonday = monday;
    }

    public boolean isTuesday() {
        return isTuesday;
    }

    public void setTuesday(boolean tuesday) {
        isTuesday = tuesday;
    }

    public boolean isWednesday() {
        return isWednesday;
    }

    public void setWednesday(boolean wednesday) {
        isWednesday = wednesday;
    }

    public boolean isThursday() {
        return isThursday;
    }

    public void setThursday(boolean thursday) {
        isThursday = thursday;
    }

    public boolean isFriday() {
        return isFriday;
    }

    public void setFriday(boolean friday) {
        isFriday = friday;
    }

    public boolean isSaturday() {
        return isSaturday;
    }

    public void setSaturday(boolean saturday) {
        isSaturday = saturday;
    }

    public boolean isSunday() {
        return isSunday;
    }

    public void setSunday(boolean sunday) {
        isSunday = sunday;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

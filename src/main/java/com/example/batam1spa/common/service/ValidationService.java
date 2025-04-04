package com.example.batam1spa.common.service;

import java.time.LocalDate;

public interface ValidationService {
    boolean validatePagination(int page, int amountPerPage);
    boolean validateStartEndDate(LocalDate startDate, LocalDate endDate, boolean dateCanBeforeToday);
    boolean validatePrice(int localPrice, int touristPrice);
    boolean validateDuration(int duration);
}

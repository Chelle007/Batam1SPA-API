package com.example.batam1spa.common.service;

import java.time.LocalDate;

public interface CommonService {
    boolean validatePagination(int page, int amountPerPage);
    boolean validateStartEndDate(LocalDate startDate, LocalDate endDate, boolean dateCanBeforeToday);
}

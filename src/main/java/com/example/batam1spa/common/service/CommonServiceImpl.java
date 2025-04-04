package com.example.batam1spa.common.service;

import com.example.batam1spa.common.exception.CommonExceptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommonServiceImpl implements CommonService {
    @Override
    public boolean validatePagination(int page, int amountPerPage) {
        if (page < 0) {
            throw new CommonExceptions.InvalidNumber("Page shouldn't be < 0: " + page);
        }
        if (amountPerPage <= 0) {
            throw new CommonExceptions.InvalidNumber("Size shouldn't be <= 0: " + amountPerPage);
        }
        if (page >= amountPerPage) {
            throw new CommonExceptions.InvalidNumber("Page shouldn't be >= amountPerPage: " + page + " / " + amountPerPage);
        }
        return true;
    }

    @Override
    public boolean validateStartEndDate(LocalDate startDate, LocalDate endDate, boolean dateCanBeforeToday) {
        if (!dateCanBeforeToday && startDate.isBefore(LocalDate.now())) {
            throw new CommonExceptions.InvalidDate("Start date cannot be before today: " + startDate);
        }
        if (endDate.isBefore(startDate)) {
            throw new CommonExceptions.InvalidDate("End date cannot be before start date: " + endDate);
        }
        return true;
    }
}

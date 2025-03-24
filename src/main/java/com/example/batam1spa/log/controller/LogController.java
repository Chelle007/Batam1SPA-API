package com.example.batam1spa.log.controller;

import com.example.batam1spa.common.dto.BaseResponse;
import com.example.batam1spa.log.service.LogService;
import com.example.batam1spa.order.dto.GetOrderDetailPaginationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.ByteArrayInputStream;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/log")
public class LogController {
    private final LogService logService;

    @GetMapping("/generate-log")
    public ResponseEntity<Resource> generateLog() {
        ByteArrayInputStream csvData = logService.generateLogCsv();

        InputStreamResource resource = new InputStreamResource(csvData);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=admin_logs.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(resource);
    }
}

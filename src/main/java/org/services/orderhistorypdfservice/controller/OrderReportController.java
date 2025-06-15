package org.services.orderhistorypdfservice.controller;

import jakarta.validation.Valid;
import org.services.orderhistorypdfservice.dto.OrderRequestDto;
import org.services.orderhistorypdfservice.service.interfaces.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderReportController {

    private static final Logger logger = LoggerFactory.getLogger(OrderReportController.class);

    private final OrderService orderService;

    public OrderReportController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(value = "/report", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateReport(@RequestBody @Valid OrderRequestDto requestDto) {
        logger.info("Received report generation request for customerId={}, from={}, to={}",
                requestDto.getCustomerId(), requestDto.getFromDate(), requestDto.getToDate());

        byte[] pdf = orderService.generateOrderReport(requestDto);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=order-report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}

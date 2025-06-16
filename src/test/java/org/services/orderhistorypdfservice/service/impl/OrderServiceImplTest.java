package org.services.orderhistorypdfservice.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.services.orderhistorypdfservice.dto.OrderRequestDto;
import org.services.orderhistorypdfservice.entity.Order;
import org.services.orderhistorypdfservice.exception.ResourceNotFoundException;
import org.services.orderhistorypdfservice.repository.OrderRepository;
import org.services.orderhistorypdfservice.util.PdfGeneratorUtil;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PdfGeneratorUtil pdfGenerator;

    @InjectMocks
    private OrderServiceImpl orderService;

    private LocalDate fromDate;
    private LocalDate toDate;

    @BeforeEach
    void setUp() {
        fromDate = LocalDate.of(2023, 1, 1);
        toDate = LocalDate.of(2023, 12, 31);
    }

    @Test
    void testGeneratePdf_throwsException_returnsError() {
        when(orderRepository.findByCustomerIdAndOrderDateBetween(any(), any(), any()))
                .thenReturn(List.of(new Order("CUST001", "Mouse", 100.0, LocalDate.now())));

        when(pdfGenerator.generateOrderPdf(any())).thenThrow(new RuntimeException("PDF Error"));


        OrderRequestDto request = new OrderRequestDto();
        request.setCustomerId("CUST001");
        request.setFromDate(fromDate);
        request.setToDate(toDate);

        assertThrows(RuntimeException.class, () -> orderService.generateOrderReport(request));
    }

    @Test
    void testNoOrdersFound_throwsResourceNotFound() {
        when(orderRepository.findByCustomerIdAndOrderDateBetween(any(), any(), any()))
                .thenReturn(Collections.emptyList());

        OrderRequestDto request = new OrderRequestDto();
        request.setCustomerId("CUST002");
        request.setFromDate(fromDate);
        request.setToDate(toDate);

        assertThrows(ResourceNotFoundException.class, () -> orderService.generateOrderReport(request));
    }
}

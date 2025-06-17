package org.services.orderhistorypdfservice.service.impl;

import org.services.orderhistorypdfservice.dto.OrderRequestDto;
import org.services.orderhistorypdfservice.entity.Order;
import org.services.orderhistorypdfservice.exception.ResourceNotFoundException;
import org.services.orderhistorypdfservice.repository.OrderRepository;
import org.services.orderhistorypdfservice.service.interfaces.OrderService;
import org.services.orderhistorypdfservice.util.PdfGeneratorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final PdfGeneratorUtil pdfGeneratorUtil;

    public OrderServiceImpl(OrderRepository orderRepository, PdfGeneratorUtil pdfGeneratorUtil) {
        this.orderRepository = orderRepository;
        this.pdfGeneratorUtil = pdfGeneratorUtil;
    }

    @Override
    public byte[] generateOrderReport(OrderRequestDto requestDto) {
        logger.info("Fetching orders for customerId={}, from={}, to={}",
                requestDto.getCustomerId(), requestDto.getFromDate(), requestDto.getToDate());

        // 🔐 Add date validation before querying the database
        if (requestDto.getFromDate().isAfter(requestDto.getToDate())) {
            throw new IllegalArgumentException("From date cannot be after To date");
        }

        List<Order> orders = orderRepository.findByCustomerIdAndOrderDateBetween(
                requestDto.getCustomerId(),
                requestDto.getFromDate(),
                requestDto.getToDate()
        );

        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("No orders found for given customer and date range.");
        }

        List<Order> sortedOrders = orders.stream()
                .sorted((a, b) -> a.getOrderDate().compareTo(b.getOrderDate()))
                .collect(Collectors.toList());

        logger.info("Found {} orders. Generating PDF report...", sortedOrders.size());

        return pdfGeneratorUtil.generateOrderPdf(sortedOrders);
    }
}

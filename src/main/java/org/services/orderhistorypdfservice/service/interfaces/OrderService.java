package org.services.orderhistorypdfservice.service.interfaces;

import org.services.orderhistorypdfservice.dto.OrderRequestDto;

public interface OrderService {
    byte[] generateOrderReport(OrderRequestDto requestDto);
}

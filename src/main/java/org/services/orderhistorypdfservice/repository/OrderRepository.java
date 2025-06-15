package org.services.orderhistorypdfservice.repository;

import org.services.orderhistorypdfservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerIdAndOrderDateBetween(String customerId, LocalDate fromDate, LocalDate toDate);
}

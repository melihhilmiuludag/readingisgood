package com.mhu.core.order.repository;

import com.mhu.core.order.domain.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findAll();

    List<Orders> findByPurchaseDateBetween(LocalDate startDate, LocalDate endDate);

    Page<Orders> findByCustomerId(Long customerId, Pageable pageable);

    @Query("SELECT bo FROM Orders bo WHERE bo.customerId = :customerId AND YEAR(bo.purchaseDate) = :year ORDER BY bo.purchaseDate ASC")
    List<Orders> findByCustomerIdAndYear(@Param("customerId") Long customerId, @Param("year") int year);
}

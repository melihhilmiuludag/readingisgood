package com.mhu.core.order.domain.entity;

import com.mhu.core.order.service.OrderLogListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(OrderLogListener.class)
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookId;

    private String bookTitle;

    private Double purchasedAmount;

    private Integer purchasedBookCount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate purchaseDate;

    private LocalTime purchaseTime;

    private Long customerId;

}

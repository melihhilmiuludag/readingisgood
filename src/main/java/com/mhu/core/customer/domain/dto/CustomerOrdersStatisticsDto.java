package com.mhu.core.customer.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerOrdersStatisticsDto {

    private String month;
    private int totalOrderCount;
    private int totalBookCount;
    private double totalPurchasedAmount;
}

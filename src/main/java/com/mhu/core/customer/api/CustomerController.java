package com.mhu.core.customer.api;


import com.mhu.core.customer.domain.dto.CustomerOrdersStatisticsDto;
import com.mhu.core.customer.service.CustomerService;
import com.mhu.core.order.domain.dto.OrderByCustomerPageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/service")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/customer/orders")
    @Operation(tags = "Customer Service", security = @SecurityRequirement(name = "Bearer Auth"))
    public ResponseEntity<OrderByCustomerPageDto> getCustomerOrders(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return ResponseEntity.ok(customerService.getCustomerOrders(pageable.getPageNumber(), pageable.getPageSize()));
    }

    @GetMapping("/customer/orders/statistics")
    @Operation(tags = "Customer Service",security = @SecurityRequirement(name = "Bearer Auth"))
    public List<CustomerOrdersStatisticsDto> statisticsByMonth(@RequestParam("year") int year){
        return customerService.customerOrdersStatics(year);
    }
}

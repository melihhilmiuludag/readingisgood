package com.mhu.core.order.api;

import com.mhu.core.order.domain.dto.OrderDto;
import com.mhu.core.order.domain.dto.PlaceOrderRequestDto;
import com.mhu.core.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "/order")
    @Operation(tags = "Order Service", security = @SecurityRequirement(name = "Bearer Auth"))
    public ResponseEntity<OrderDto> orderBook(@Validated @RequestBody PlaceOrderRequestDto requestDto) {
        return ResponseEntity.ok(orderService.placeAnOrder(requestDto));
    }

    @GetMapping(value = "/orders")
    @Operation(tags = "Order Service", security = @SecurityRequirement(name = "Bearer Auth"))
    public ResponseEntity<List<OrderDto>> getOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping(value = "/orders/between_two_dates")
    @Operation(tags = "Order Service", security = @SecurityRequirement(name = "Bearer Auth"))
    public ResponseEntity<List<OrderDto>> getOrdersByDateInterval(@RequestParam(value = "startDate", required = true) String startDate,
                                                                  @RequestParam(value = "endDate", required = true) String endDate) {

        return ResponseEntity.ok(orderService.getAllOrdersBetween(startDate, endDate));
    }
}

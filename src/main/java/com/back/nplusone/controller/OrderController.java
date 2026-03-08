package com.back.nplusone.controller;

import com.back.nplusone.service.OrderQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderQueryService orderQueryService;

    public OrderController(OrderQueryService orderQueryService) {
        this.orderQueryService = orderQueryService;
    }

    /**
     * N+1 발생 API.
     * 호출 후 콘솔 로그에서 쿼리 개수 확인:
     * - select orders 1번
     * - select products 가 주문 개수만큼 추가로 실행됨
     */
    @GetMapping
    public List<OrderQueryService.OrderDto> getOrders() {
        return orderQueryService.getOrdersWithProducts();
    }
}

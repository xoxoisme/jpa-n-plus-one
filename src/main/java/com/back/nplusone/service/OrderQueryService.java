package com.back.nplusone.service;

import com.back.nplusone.domain.Order;
import com.back.nplusone.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * N+1이 발생하는 전형적인 코드.
 * findAll() → 1번의 SELECT orders
 * 각 order.getProducts() 접근 시 → 주문 수만큼 추가 SELECT products (N번)
 * → 총 1 + N 번 쿼리 (N+1)
 */
@Service
public class OrderQueryService {

    private final OrderRepository orderRepository;

    public OrderQueryService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public List<OrderDto> getOrdersWithProducts() {
        // 1) 주문만 조회 (products는 LAZY라서 여기선 안 가져옴)
        List<Order> orders = orderRepository.findAllByOrderByIdAsc();

        // 2) 각 주문마다 products에 접근 → N+1 발생 지점
        return orders.stream()
                .map(order -> {
                    String productSummary = order.getProducts().stream()  // 여기서 각 주문별로 SELECT products 실행
                            .map(p -> p.getName() + " " + p.getPrice() + "원")
                            .collect(Collectors.joining(", "));
                    return new OrderDto(order.getId(), order.getOrderNumber(), productSummary);
                })
                .toList();
    }

    public record OrderDto(Long id, String orderNumber, String productSummary) {}
}

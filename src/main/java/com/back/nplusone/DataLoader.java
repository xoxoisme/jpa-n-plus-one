package com.back.nplusone;

import com.back.nplusone.domain.Order;
import com.back.nplusone.domain.Product;
import com.back.nplusone.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataLoader implements CommandLineRunner {

    private final OrderRepository orderRepository;

    public DataLoader(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (orderRepository.count() > 0) return;

        Order order1 = new Order("ORD-001");
        order1.addProduct(new Product("노트북", 1_500_000));
        order1.addProduct(new Product("마우스", 35_000));

        Order order2 = new Order("ORD-002");
        order2.addProduct(new Product("키보드", 120_000));
        order2.addProduct(new Product("모니터", 300_000));
        order2.addProduct(new Product("헤드셋", 80_000));

        Order order3 = new Order("ORD-003");
        order3.addProduct(new Product("USB허브", 25_000));
        order3.addProduct(new Product("웹캠", 95_000));

        orderRepository.saveAll(java.util.List.of(order1, order2, order3));
    }
}

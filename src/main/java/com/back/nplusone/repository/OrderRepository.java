package com.back.nplusone.repository;

import com.back.nplusone.domain.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 1) fetch join 예제
    @Query("select distinct o from Order o left join fetch o.products order by o.id asc")
    List<Order> findAllWithProductsOrderByIdAsc();

    // 2) EntityGraph 예제 (메서드 이름은 단순 파생쿼리 형태로)
    @EntityGraph(attributePaths = "products")
    List<Order> findAllByOrderByIdAsc();
    
}

package com.naveen.ecommerce_backend.repository;

import com.naveen.ecommerce_backend.model.order.Order;
import com.naveen.ecommerce_backend.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {

    Page<Order> findByUser(User user, Pageable pageable);

}

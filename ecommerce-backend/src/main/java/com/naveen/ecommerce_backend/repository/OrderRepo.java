package com.naveen.ecommerce_backend.repository;

import com.naveen.ecommerce_backend.model.Order.Order;
import com.naveen.ecommerce_backend.model.User.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {

    Page<Order> findByUser(User user, Pageable pageable);

}

package com.hrafty.web_app.Repository;

import com.hrafty.web_app.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order ,Long> {
}

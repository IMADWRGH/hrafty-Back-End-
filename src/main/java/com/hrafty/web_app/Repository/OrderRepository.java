package com.hrafty.web_app.Repository;

import com.hrafty.web_app.entities.Customer;
import com.hrafty.web_app.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order ,Long> {
    List<Order> findByCustomer(Customer customer);
}

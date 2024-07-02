package com.hrafty.web_app.services.ServicesImpl;

import com.hrafty.web_app.Repository.CustomerRepository;
import com.hrafty.web_app.Repository.OrderRepository;
import com.hrafty.web_app.Repository.ProductRepository;
import com.hrafty.web_app.dto.OrderDTO;
import com.hrafty.web_app.dto.OrderItemDTO;
import com.hrafty.web_app.dto.ServiceDTO;
import com.hrafty.web_app.entities.Customer;
import com.hrafty.web_app.entities.OrderItem;
import com.hrafty.web_app.entities.Product;
import com.hrafty.web_app.mapper.OrderItemMapper;
import com.hrafty.web_app.mapper.OrderMapper;
import com.hrafty.web_app.services.Order;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderImpl implements Order {
    private final CustomerRepository customerRepository;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderImpl(CustomerRepository customerRepository, OrderMapper orderMapper, OrderRepository orderRepository, ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public OrderDTO create(Long customerId, List<OrderItemDTO> orderItemsDTO) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        List<OrderItem> orderItems = new ArrayList<>();
        double totalPrice = 0;
        for (OrderItemDTO dto : orderItemsDTO) {
            Product product = productRepository.findById(dto.getProductId()).orElseThrow(() -> new EntityNotFoundException("Product not found"));
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(dto.getQuantity());
            orderItem.setPrice(product.getPrice() * dto.getQuantity());
            totalPrice += orderItem.getPrice();
            orderItems.add(orderItem);
        }
        com.hrafty.web_app.entities.Order order = new com.hrafty.web_app.entities.Order();
        order.setCustomer(customer);
        order.setOrderItems(orderItems);
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);
        return orderMapper.toDTO(order);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return null;
    }

    @Override
    public List<OrderDTO> getAllOrders(Long id) {
        return null;
    }

    @Override
    public void updateOrders(Long id, OrderDTO orderDTO) {

    }

    @Override
    public OrderDTO getOrder(Long id) {
        return null;
    }

    @Override
    public void deleteOrder(Long id) {

    }
}

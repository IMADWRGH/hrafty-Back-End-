//package com.hrafty.web_app.services.ServicesImpl;
//
//import com.hrafty.web_app.Repository.CustomerRepository;
//import com.hrafty.web_app.Repository.OrderRepository;
//import com.hrafty.web_app.Repository.ProductRepository;
//import com.hrafty.web_app.dto.OrderDTO;
//import com.hrafty.web_app.dto.OrderItemDTO;
//import com.hrafty.web_app.dto.common.PageResponseDTO;
//import com.hrafty.web_app.dto.request.OrderRequestDTO;
//import com.hrafty.web_app.dto.response.OrderResponseDTO;
//import com.hrafty.web_app.entities.Customer;
//import com.hrafty.web_app.entities.OrderItem;
//import com.hrafty.web_app.entities.Product;
//import com.hrafty.web_app.entities.enums.OrderStatus;
//import com.hrafty.web_app.mapper.OrderMapper;
//import com.hrafty.web_app.services.OrderService;
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class OrderImpl implements OrderService {
//    private final CustomerRepository customerRepository;
//    private final OrderMapper orderMapper;
//    private final OrderRepository orderRepository;
//    private final ProductRepository productRepository;
//
//    public OrderImpl(CustomerRepository customerRepository, OrderMapper orderMapper, OrderRepository orderRepository, ProductRepository productRepository) {
//        this.customerRepository = customerRepository;
//        this.orderMapper = orderMapper;
//        this.orderRepository = orderRepository;
//        this.productRepository = productRepository;
//    }
//
//    @Override
//    @Transactional
//    public OrderDTO create(Long customerId, List<OrderItemDTO> orderItemsDTO) {
//        Customer customer = customerRepository.findById(customerId)
//                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
//        List<OrderItem> orderItems = new ArrayList<>();
//        com.hrafty.web_app.entities.Order order = new com.hrafty.web_app.entities.Order();
//        double totalPrice = 0;
//        for (OrderItemDTO dto : orderItemsDTO) {
//            Product product = productRepository.findById(dto.productId())
//                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));
//            OrderItem orderItem = new OrderItem();
//            orderItem.setOrder(order);
//            orderItem.setProduct(product);
//            orderItem.setQuantity(dto.quantity());
//            orderItem.setTotalPrice(product.getPrice() * dto.quantity());
//            totalPrice += orderItem.getTotalPrice();
//            orderItems.add(orderItem);
//        }
//        order.setCustomer(customer);
//        order.setOrderItems(orderItems);
//        order.setTotalPrice(totalPrice);
//        orderRepository.save(order);
//
//        return orderMapper.toDTO(order);
//    }
//
//    @Override
//    public OrderResponseDTO create(Long customerId, OrderRequestDTO orderRequestDTO) throws Exception {
//        return null;
//    }
//
//    @Override
//    public List<OrderDTO> getAllOrders() {
//        List<com.hrafty.web_app.entities.Order> orders = orderRepository.findAll();
//        List<OrderDTO> orderDTOList=new ArrayList<>();
//        for (com.hrafty.web_app.entities.Order order : orders) {
//            OrderDTO orderDTO = orderMapper.toDTO(order);
//            orderDTOList.add(orderDTO);
//        }
//        return orderDTOList;
//    }
//
//    @Override
//    public PageResponseDTO<OrderResponseDTO> getAllOrders(Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public List<OrderResponseDTO> getOrdersByCustomerId(Long customerId) {
//        return List.of();
//    }
//
//    @Override
//    public PageResponseDTO<OrderResponseDTO> getOrdersByCustomerId(Long customerId, Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public List<OrderDTO> getAllOrders(Long customerId) {
//        Customer customer = customerRepository.findById(customerId)
//                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
//        return orderRepository.findByCustomer(customer).stream()
//                .map(orderMapper::toDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public void updateOrders(Long id, OrderDTO orderDTO) {
//        com.hrafty.web_app.entities.Order order = orderRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
//
//        List<OrderItem> orderItems = new ArrayList<>();
//        double totalPrice = 0;
//
//        for (OrderItemDTO dto : orderDTO.orderItems()) {
//            Product product = productRepository.findById(dto.productId())
//                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));
//            OrderItem orderItem = new OrderItem();
//            orderItem.setProduct(product);
//            orderItem.setQuantity(dto.quantity());
//            orderItem.setTotalPrice(product.getPrice() * dto.quantity());
//            totalPrice += orderItem.getTotalPrice();
//            orderItems.add(orderItem);
//        }
//
//        order.setOrderItems(orderItems);
//        order.setTotalPrice(totalPrice);
//        orderRepository.save(order);
//    }
//
//    @Override
//    public OrderDTO getOrder(Long id) {
//        com.hrafty.web_app.entities.Order order = orderRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
//        return orderMapper.toDTO(order);
//    }
//
//    @Override
//    public OrderResponseDTO updateOrder(Long id, OrderRequestDTO orderRequestDTO) {
//        return null;
//    }
//
//    @Override
//    public OrderResponseDTO updateOrderStatus(Long id, OrderStatus status) {
//        return null;
//    }
//
//    @Override
//    public void deleteOrder(Long id) {
//        com.hrafty.web_app.entities.Order order = orderRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
//        orderRepository.delete(order);
//    }
//
//    @Override
//    public List<OrderResponseDTO> getOrdersByStatus(OrderStatus status) {
//        return List.of();
//    }
//
//    @Override
//    public PageResponseDTO<OrderResponseDTO> getOrdersByStatus(OrderStatus status, Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public double calculateTotalRevenue() {
//        return 0;
//    }
//
//    @Override
//    public long countOrders() {
//        return 0;
//    }
//
//    @Override
//    public long countOrdersByStatus(OrderStatus status) {
//        return 0;
//    }
//
//    @Override
//    public boolean existsById(Long id) {
//        return false;
//    }
//}

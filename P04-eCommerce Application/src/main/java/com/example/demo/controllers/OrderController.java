package com.example.demo.controllers;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {
  private final Logger logger = LoggerFactory.getLogger(OrderController.class.getName());

  @Autowired private UserRepository userRepository;

  @Autowired private OrderRepository orderRepository;

  @PostMapping("/submit/{username}")
  public ResponseEntity<UserOrder> submit(@PathVariable String username) {
    logger.info("order submit");
    User user = userRepository.findByUsername(username);
    if (user == null) {
      logger.warn("order submit, user not found");
      return ResponseEntity.notFound().build();
    }
    UserOrder order = UserOrder.createFromCart(user.getCart());
    orderRepository.save(order);
    return ResponseEntity.ok(order);
  }

  @GetMapping("/history/{username}")
  public ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String username) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(orderRepository.findByUser(user));
  }
}

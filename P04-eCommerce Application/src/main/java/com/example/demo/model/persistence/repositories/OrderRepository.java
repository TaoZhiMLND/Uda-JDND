package com.example.demo.model.persistence.repositories;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<UserOrder, Long> {
  List<UserOrder> findByUser(User user);
}

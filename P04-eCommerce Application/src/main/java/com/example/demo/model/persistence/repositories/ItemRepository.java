package com.example.demo.model.persistence.repositories;

import com.example.demo.model.persistence.Item;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
  public List<Item> findByName(String name);
}

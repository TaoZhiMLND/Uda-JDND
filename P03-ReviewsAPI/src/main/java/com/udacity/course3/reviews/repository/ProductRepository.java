package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {

}

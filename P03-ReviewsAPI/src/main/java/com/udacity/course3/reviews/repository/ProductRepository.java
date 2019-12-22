package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Product Repository Layer.
 *
 * @author TaoZhi
 * @version 1.0
 * @date 2019/12/22 12:03
 **/
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}

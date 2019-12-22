package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Review Repository Layer.
 *
 * @author TaoZhi
 * @version 1.0
 * @date 2019/12/22 12:03
 **/
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
  List<Review> findByProductId(Long productId);
}

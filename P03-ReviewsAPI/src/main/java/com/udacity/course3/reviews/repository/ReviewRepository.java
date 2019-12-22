package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

  List<Review> findByProductId(Long productId);
}

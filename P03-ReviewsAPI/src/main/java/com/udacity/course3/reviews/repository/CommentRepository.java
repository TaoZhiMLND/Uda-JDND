package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

  List<Comment> findByReviewId(Long reviewId);
}

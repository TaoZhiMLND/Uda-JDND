package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.entity.mongo.ReviewDoc;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import com.udacity.course3.reviews.repository.mongo.ReviewMongoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring REST controller for working with comment entity.
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {

  // DONE: Wire needed JPA repositories here
  private final CommentRepository commentRepo;
  private final ReviewRepository reviewRepo;
  private final ReviewMongoRepository reviewMongoRepo;

  public CommentsController(
      CommentRepository commentRepo, ReviewRepository reviewRepo,
      ReviewMongoRepository reviewMongoRepo) {
    this.commentRepo = commentRepo;
    this.reviewRepo = reviewRepo;
    this.reviewMongoRepo = reviewMongoRepo;
  }

  /**
   * Creates a comment for a review.
   *
   * 1. Add argument for comment entity. Use {@link RequestBody} annotation. 2. Check for existence
   * of review. 3. If review not found, return NOT_FOUND. 4. If found, save comment.
   *
   * @param reviewId The id of the review.
   */
  @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.POST)
  public ResponseEntity<?> createCommentForReview(@PathVariable("reviewId") Long reviewId,
      @RequestBody Comment comment) {
    Optional<Review> review = reviewRepo.findById(reviewId);
    if (!review.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    comment.setReviewId(review.get().getId());
    commentRepo.save(comment);

    Optional<ReviewDoc> optionalReviewDoc = reviewMongoRepo.findById(reviewId.toString());
    if (optionalReviewDoc.isPresent()) {
        ReviewDoc reviewDoc = optionalReviewDoc.get();
        List<String> comments = reviewDoc.getComments();
        if (CollectionUtils.isEmpty(comments)) {
            comments = new ArrayList<>();
            comments.add(comment.getContent());
            reviewDoc.setComments(comments);
        } else {
            comments.add(comment.getContent());
        }
        reviewMongoRepo.save(reviewDoc);
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(comment);
  }

  /**
   * List comments for a review.
   *
   * 2. Check for existence of review. 3. If review not found, return NOT_FOUND. 4. If found, return
   * list of comments.
   *
   * @param reviewId The id of the review.
   */
  @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.GET)
  public List<?> listCommentsForReview(@PathVariable("reviewId") Long reviewId) {
    List<Comment> comments = commentRepo.findByReviewId(reviewId);
    return comments;
  }
}
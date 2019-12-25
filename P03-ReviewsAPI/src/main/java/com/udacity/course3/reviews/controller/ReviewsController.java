package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.entity.mongo.ReviewDoc;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import com.udacity.course3.reviews.repository.mongo.ReviewMongoRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring REST controller for working with review entity.
 */
@RestController
public class ReviewsController {

  // DONE: Wire JPA repositories here
  private final ProductRepository productRepo;
  private final ReviewRepository reviewRepo;
  private final CommentRepository commentRepo;
  private final ReviewMongoRepository reviewMongoRepo;

  public ReviewsController(ProductRepository productRepo,
      ReviewRepository reviewRepo,
      ReviewMongoRepository reviewMongoRepo,
      CommentRepository commentRepo) {
    this.productRepo = productRepo;
    this.reviewRepo = reviewRepo;
    this.reviewMongoRepo = reviewMongoRepo;
    this.commentRepo = commentRepo;
  }

  /**
   * Creates a review for a product.
   * <p>
   * 1. Add argument for review entity. Use {@link RequestBody} annotation. 2. Check for existence
   * of product. 3. If product not found, return NOT_FOUND. 4. If found, save review.
   *
   * @param productId The id of the product.
   * @return The created review or 404 if product id is not found.
   */
  @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.POST)
  public ResponseEntity<?> createReviewForProduct(@PathVariable("productId") Long productId,
      @RequestBody ReviewDoc reviewDoc) {
    Optional<Product> product = productRepo.findById(productId);
    if (!product.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    Review review = new Review();
    review.setProductId(productId);
    review.setContent(reviewDoc.getContent());
    reviewRepo.save(review);
    List<String> comments = reviewDoc.getComments();
    if (!CollectionUtils.isEmpty(comments)) {
      comments.stream().forEach(x -> {
        Comment comment = new Comment();
        comment.setReviewId(review.getId());
        comment.setContent(x);
        commentRepo.save(comment);
      });
    }
    reviewDoc.setId(review.getId().toString());
    reviewMongoRepo.save(reviewDoc);
    return ResponseEntity.status(HttpStatus.CREATED).body(reviewDoc);
  }

  /**
   * Lists reviews by product.
   *
   * @param productId The id of the product.
   * @return The list of reviews.
   */
  @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.GET)
  public ResponseEntity<List<?>> listReviewsForProduct(@PathVariable("productId") Long productId) {
    List<Review> reviews = reviewRepo.findByProductId(productId);
    if (CollectionUtils.isEmpty(reviews)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    List<String> reviewIds = reviews.stream().map(Review::getId).map(Objects::toString)
        .collect(Collectors.toList());
    List<ReviewDoc> reviewDocs = reviewMongoRepo.findByIdIn(reviewIds);
    return ResponseEntity.ok(reviewDocs);
  }
}
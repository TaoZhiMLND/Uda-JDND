package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  public ReviewsController(ProductRepository productRepo,
      ReviewRepository reviewRepo) {
    this.productRepo = productRepo;
    this.reviewRepo = reviewRepo;
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
      @RequestBody Review review) {
    Optional<Product> product = productRepo.findById(productId);
    if (!product.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    reviewRepo.save(review);
    return ResponseEntity.status(HttpStatus.CREATED).body(review);
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
    return ResponseEntity.ok(reviews);
  }
}
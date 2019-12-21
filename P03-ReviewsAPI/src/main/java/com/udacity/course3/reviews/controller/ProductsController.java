package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring REST controller for working with product entity.
 */
@RestController
@RequestMapping("/products")
public class ProductsController {

  // DONE: Wire JPA repositories here
  private final ProductRepository productRepo;

  public ProductsController(ProductRepository productRepo) {
    this.productRepo = productRepo;
  }

  /**
   * Creates a product.
   *
   * 1. Accept product as argument. Use {@link RequestBody} annotation. 2. Save product.
   */
  @RequestMapping(value = "/", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.CREATED)
  public void createProduct(@RequestBody Product product) {
    productRepo.save(product);
  }

  /**
   * Finds a product by id.
   *
   * @param id The id of the product.
   * @return The product if found, or a 404 not found.
   */
  @RequestMapping(value = "/{id}")
  public ResponseEntity<?> findById(@PathVariable("id") Long id) {
    Optional<Product> product = productRepo.findById(id);
    return ResponseEntity.of(product);
  }

  /**
   * Lists all products.
   *
   * @return The list of products.
   */
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public List<?> listProducts() {
    return productRepo.findAll();
  }
}
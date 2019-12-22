package com.udacity.course3.reviews.repository;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import java.util.List;
import java.util.Optional;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * TESTS FOR `ReviewRepository`,`CommentRepository`,`ProductRepository`.
 *
 * @author TaoZhi
 * @version 1.0
 * @date 2019/12/22 12:03
 **/
@DataJpaTest
@RunWith(SpringRunner.class)
public class ReviewRepositoryTest {

  @Autowired private ReviewRepository reviewRepository;
  @Autowired private CommentRepository commentRepository;
  @Autowired private ProductRepository productRepository;

  @Before
  public void init() {
    Review review = new Review();
    review.setContent("TaoZhi");
    review.setProductId(1L);
    reviewRepository.save(review);

    Comment comment = new Comment();
    comment.setContent("Test");
    comment.setReviewId(review.getId());
    commentRepository.save(comment);
  }

  @Test
  public void injectedComponentsAreNotNull(){
    assertThat(reviewRepository, CoreMatchers.notNullValue());
    assertThat(commentRepository, CoreMatchers.notNullValue());
    assertThat(productRepository, CoreMatchers.notNullValue());
  }

  @Test
  public void findReviewsByProductId() {
    List<Review> reviews = reviewRepository.findByProductId(1L);
    Assert.assertNotNull(reviews);
  }

  @Test
  public void findCommentsByReviewId() {
    List<Comment> comments = commentRepository.findByReviewId(1L);
    Assert.assertNotNull(comments);

  }

  @Test
  public void findProductById() {
    Optional<Product> product = productRepository.findById(1L);
    Assert.assertTrue(product.isPresent());
    Assert.assertThat(product.get().getName(), is("SpringBoot GuideBook"));
  }
}
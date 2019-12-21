package com.udacity.course3.reviews.repository;


import static org.junit.Assert.assertThat;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Review;
import java.util.List;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
public class ReviewRepositoryTest {

  @Autowired private DataSource dataSource;
  @Autowired private JdbcTemplate jdbcTemplate;
  @Autowired private EntityManager entityManager;
  @Autowired private TestEntityManager testEntityManager;
  @Autowired private ReviewRepository reviewRepository;
  @Autowired private CommentRepository commentRepository;

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
    assertThat(dataSource, CoreMatchers.notNullValue());
    assertThat(jdbcTemplate, CoreMatchers.notNullValue());
    assertThat(entityManager, CoreMatchers.notNullValue());
    assertThat(testEntityManager, CoreMatchers.notNullValue());
    assertThat(reviewRepository, CoreMatchers.notNullValue());
    assertThat(commentRepository, CoreMatchers.notNullValue());
  }

  @Test
  public void findByProductId() {
    List<Review> reviews = reviewRepository.findByProductId(1L);
    Assert.assertNotNull(reviews);
  }



  @Test
  public void findByReviewId() {
    List<Comment> comments = commentRepository.findByReviewId(1L);
    Assert.assertNotNull(comments);
  }
}
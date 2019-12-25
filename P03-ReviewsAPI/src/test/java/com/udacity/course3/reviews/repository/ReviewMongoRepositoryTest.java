package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.mongo.ReviewDoc;
import com.udacity.course3.reviews.repository.mongo.ReviewMongoRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * TESTS FOR `ReviewMongoRepository`
 *
 * @author TaoZhi
 * @version 1.0
 * @date 2019/12/22 23:49
 **/
@DataMongoTest
@RunWith(SpringRunner.class)
public class ReviewMongoRepositoryTest {

  @Autowired
  ReviewMongoRepository reviewMongoRepository;

  @Test
  public void simpleTest() {

    ReviewDoc reviewDoc1 = new ReviewDoc();
    reviewDoc1.setContent("TaoZhi");
    reviewMongoRepository.save(reviewDoc1);

    ReviewDoc reviewDoc2 = new ReviewDoc();
    reviewDoc2.setContent("TaoZhi2");
    reviewMongoRepository.save(reviewDoc2);

    List<ReviewDoc> reviewDocList = reviewMongoRepository
        .findByIdIn(Arrays.asList(reviewDoc1.getId(), reviewDoc2.getId()));

    Assert.assertEquals(reviewDocList.size(), 2);
  }
}

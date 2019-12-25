package com.udacity.course3.reviews.repository.mongo;

import com.udacity.course3.reviews.entity.mongo.ReviewDoc;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Review Mongo Repository Layer.
 *
 * @author TaoZhi
 * @version 1.0
 * @date 2019/12/22 18:47
 **/
@Repository
public interface ReviewMongoRepository extends MongoRepository<ReviewDoc, String> {

  List<ReviewDoc> findByIdIn(List<String> reviewIds);
}

package com.udacity.course3.reviews.entity.mongo;

import java.util.List;
import javax.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * MongoDb collection `Review`.
 *
 * @author TaoZhi
 * @version 1.0
 * @date 2019/12/22 20:01
 **/
@Data
@Document(collection = "review")
public class ReviewDoc {

  @Id
  private String id;

  private String content;

  private List<String> comments;
}
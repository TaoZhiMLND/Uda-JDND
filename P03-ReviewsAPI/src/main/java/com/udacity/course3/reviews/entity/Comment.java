package com.udacity.course3.reviews.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
@Table(name = "comment")
public class Comment {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "content")
  private String content;

  @Column(name = "review_id")
  private Long reviewId;

  @OneToOne
  @JoinColumn(name = "review_id", referencedColumnName = "id", insertable=false, updatable=false)
  private Review review;

  @CreationTimestamp
  @Column(name = "created_time")
  private Date createdTime;

}

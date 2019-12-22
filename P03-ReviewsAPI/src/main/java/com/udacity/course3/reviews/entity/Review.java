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

/**
 * Review Entity.
 *
 * @author TaoZhi
 * @version 1.0
 * @date 2019/12/22 12:03
 **/
@Data
@Entity
@Table(name = "review")
public class Review {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "content")
  private String content;

  @Column(name = "product_id")
  private Long productId;

  @OneToOne
  @JoinColumn(name = "product_id", referencedColumnName = "id", insertable=false, updatable=false)
  private Product product;

  @CreationTimestamp
  @Column(name = "created_time")
  private Date createdTime;
}

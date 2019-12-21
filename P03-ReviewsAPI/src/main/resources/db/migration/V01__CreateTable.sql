CREATE TABLE `jdnd-p3`.`product` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL,
  `created_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `jdnd-p3`.`review` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `content` VARCHAR(200) NOT NULL,
  `product_id` BIGINT(20) NOT NULL,
  `created_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_product_id_idx` (`product_id` ASC),
  CONSTRAINT `fk_product_id`
    FOREIGN KEY (`product_id`)
    REFERENCES `jdnd-p3`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `jdnd-p3`.`comment` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(200) NOT NULL,
  `review_id` BIGINT(20) NOT NULL,
  `created_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_review_id_idx` (`review_id` ASC),
  CONSTRAINT `fk_review_id`
    FOREIGN KEY (`review_id`)
    REFERENCES `jdnd-p3`.`review` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

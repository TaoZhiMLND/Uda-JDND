CREATE TABLE `product` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL,
  `created_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `review` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `content` VARCHAR(200) NOT NULL,
  `product_id` BIGINT(20) NOT NULL,
  `created_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `comment` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(200) NOT NULL,
  `review_id` BIGINT(20) NOT NULL,
  `created_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`));

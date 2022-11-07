CREATE TABLE `coffee` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `acidity` decimal(19,2) DEFAULT NULL,
  `bean_type` varchar(255) DEFAULT NULL,
  `fermentation` varchar(255) DEFAULT NULL,
  `origin` varchar(255) DEFAULT NULL,
  `price_per_pound` decimal(19,2) DEFAULT NULL,
  `roast` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

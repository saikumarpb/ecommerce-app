CREATE TABLE `user` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `created_at` timestamp,
  `password` varchar(255) NOT NULL,
  `updated_at` timestamp
);
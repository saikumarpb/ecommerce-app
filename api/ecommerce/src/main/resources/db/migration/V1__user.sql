CREATE TABLE `user` (
  `id` int PRIMARY KEY,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL ,
  `password` varchar(255) NOT NULL,
  `updated_at` timestamp NOT NULL
);
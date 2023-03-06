CREATE TABLE `users` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `created_at` timestamp,
  `password` varchar(255),
  `updated_at` timestamp
);

CREATE TABLE `address` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int,
  `type` ENUM ('work', 'home'),
  `address_line_1` varchar(255) NOT NULL,
  `address_line_2` varchar(255),
  `district` varchar(255) NOT NULL,
  `state_code` int,
  `pincode` varchar(255) NOT NULL,
  `country_code` int,
  `mobile_number` varchar(255),
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `payment_instruments` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int,
  `type` ENUM ('credit_card', 'debit_card'),
  `card_number` varchar(255),
  `cvv` int,
  `created_at` timestamp,
  `updated_at` timestamp
);

CREATE TABLE `countries` (
  `id` int PRIMARY KEY,
  `name` varchar(255),
  `continent_name` varchar(255)
);

CREATE TABLE `states` (
  `id` int PRIMARY KEY,
  `country_code` int,
  `name` varchar(255)
);

CREATE TABLE `products` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `category` int,
  `created_at` timestamp,
  `updated_at` timestamp,
  `description` varchar(255)
);

CREATE TABLE `product_categories` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255)
);

CREATE TABLE `product_variants` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `price` float NOT NULL,
  `stock` int NOT NULL,
  `image` varchar(255),
  `variant_description` varchar(255)
);

CREATE TABLE `orders` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int,
  `cart_id` int,
  `total_amount` float NOT NULL,
  `shipping_address` int NOT NULL,
  `payment_instrument_id` int,
  `payment_status` ENUM ('pending', 'success', 'failed'),
  `created_at` datetime,
  `updated_at` datetime
);

CREATE TABLE `order_items` (
  `order_id` int,
  `product_variant_id` int,
  `quantity` int,
  `price` float NOT NULL,
  `created_at` datetime,
  PRIMARY KEY (`order_id`, `product_variant_id`)
);

CREATE TABLE `cart` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int,
  `status` ENUM ('live', 'cleared'),
  `created_at` datetime,
  `updated_at` datetime
);

CREATE TABLE `cart_items` (
  `cart_id` int,
  `product_variant_id` int,
  `quantity` int,
  `price` float NOT NULL,
  `created_at` datetime,
  PRIMARY KEY (`cart_id`, `product_variant_id`)
);

ALTER TABLE `address` ADD FOREIGN KEY (`state_code`) REFERENCES `states` (`id`);

ALTER TABLE `payment_instruments` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `states` ADD FOREIGN KEY (`country_code`) REFERENCES `countries` (`id`);

ALTER TABLE `orders` ADD FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`);

ALTER TABLE `orders` ADD FOREIGN KEY (`payment_instrument_id`) REFERENCES `payment_instruments` (`id`);

ALTER TABLE `cart` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `cart_items` ADD FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`);

ALTER TABLE `cart_items` ADD FOREIGN KEY (`product_variant_id`) REFERENCES `product_variants` (`id`);

ALTER TABLE `address` ADD FOREIGN KEY (`country_code`) REFERENCES `countries` (`id`);

ALTER TABLE `address` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `orders` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `order_items` ADD FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);

ALTER TABLE `order_items` ADD FOREIGN KEY (`product_variant_id`) REFERENCES `product_variants` (`id`);

ALTER TABLE `orders` ADD FOREIGN KEY (`shipping_address`) REFERENCES `address` (`id`);

ALTER TABLE `products` ADD FOREIGN KEY (`category`) REFERENCES `product_categories` (`id`);

ALTER TABLE `product_variants` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

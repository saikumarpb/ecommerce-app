CREATE TABLE `product`
(
    `id`          int PRIMARY KEY,
    `name`        varchar(255) NOT NULL,
    `category_id` int          NOT NULL,
    `price`       float        NOT NULL,
    `stock`       int          NOT NULL,
    `image`       varchar(255),
    `description` varchar(255),
    `created_at`  timestamp    NOT NULL,
    `updated_at`  timestamp    NOT NULL
);

CREATE TABLE `product_category`
(
    `id`          int PRIMARY KEY,
    `name`        varchar(255) NOT NULL,
    `description` varchar(255)
);

ALTER TABLE `product`
    ADD FOREIGN KEY (`category_id`) REFERENCES `product_category` (`id`);

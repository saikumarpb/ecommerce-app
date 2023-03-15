CREATE TABLE `cart`
(
    `id`         int PRIMARY KEY AUTO_INCREMENT,
    `user_id`    int      NOT NULL UNIQUE,
    `created_at` datetime NOT NULL,
    `updated_at` datetime NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE `cart_item`
(
    `id`         int PRIMARY KEY AUTO_INCREMENT,
    `cart_id`    int      NOT NULL,
    `product_id` int      NOT NULL,
    `quantity`   int      NOT NULL,
    `created_at` datetime NOT NULL,
    `updated_at` datetime NOT NULL,
    FOREIGN KEY (cart_id) REFERENCES cart (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);

CREATE TABLE `order`
(
    `id`                    int PRIMARY KEY AUTO_INCREMENT,
    `user_id`               int                                   NOT NULL,
    `amount`                double                                NOT NULL,
    `address_id`            int                                   NOT NULL,
    `created_at`            datetime                              NOT NULL,
    `updated_at`            datetime                              NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (address_id) REFERENCES address (id)
);

CREATE TABLE `order_item`
(
    `id`         int PRIMARY KEY AUTO_INCREMENT,
    `order_id`   int      NOT NULL,
    `product_id` int      NOT NULL,
    `quantity`   int      NOT NULL,
    `price`      double    NOT NULL,
    `created_at` datetime NOT NULL,
    `updated_at` datetime NOT NULL,
    FOREIGN KEY (order_id) REFERENCES `order` (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);
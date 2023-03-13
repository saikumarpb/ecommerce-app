CREATE TABLE `country`
(
    `id`         int PRIMARY KEY AUTO_INCREMENT,
    `name`       varchar(255),
    `created_at` timestamp NOT NULL,
    `updated_at` timestamp NOT NULL
);

CREATE TABLE `state`
(
    `id`         int PRIMARY KEY AUTO_INCREMENT,
    `name`       varchar(255) NOT NULL,
    `country_id` int          NOT NULL,
    `created_at` timestamp    NOT NULL,
    `updated_at` timestamp    NOT NULL,
    FOREIGN KEY (country_id) REFERENCES country (id) ON DELETE CASCADE
);

CREATE TABLE `address`
(
    `id`             int PRIMARY KEY AUTO_INCREMENT,
    `user_id`        int          NOT NULL,
    `type`           ENUM ('WORK', 'HOME'),
    `address_line_1` varchar(255) NOT NULL,
    `address_line_2` varchar(255),
    `district`       varchar(255) NOT NULL,
    `state_id`       int          NOT NULL,
    `country_id`     int          NOT NULL,
    `pincode`        varchar(255) NOT NULL,
    `mobile_number`  varchar(255) NOT NULL,
    `created_at`     timestamp    NOT NULL,
    `updated_at`     timestamp    NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (state_id) REFERENCES state (id) ON DELETE CASCADE,
    FOREIGN KEY (country_id) REFERENCES country (id) ON DELETE CASCADE
);
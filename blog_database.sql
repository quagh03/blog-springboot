-- Database
DROP DATABASE IF EXISTS `blog`;

CREATE SCHEMA `blog` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- User Table
CREATE TABLE `blog`.`users` (
    `id` BINARY(16) DEFAULT (UUID_TO_BIN(UUID())),
    `first_name` VARCHAR(50) NULL DEFAULT '',
    `last_name` VARCHAR(50) NULL DEFAULT '',
    `username` VARCHAR(50) UNIQUE NOT NULL,
    `phone_number` VARCHAR(15) NOT NULL,
    `email` VARCHAR(50) NOT NULL,
    `password_hash` VARCHAR(80) NOT NULL,
    `registered_at` DATETIME,
    `last_login` DATETIME,
    `intro` TINYTEXT,
    `profile` TEXT,
    `role` ENUM('ROLE_ADMIN', 'ROLE_AUTHOR', 'ROLE_GUEST') NOT NULL DEFAULT 'ROLE_GUEST',
    `is_active` TINYINT(1) NOT NULL DEFAULT 0,
    `facebook_account_id` INT DEFAULT 0,
    `google_account_id` INT DEFAULT 0,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uq_phone_number` (`phone_number` ASC),
    UNIQUE INDEX `uq_email` (`email` ASC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Token Table
CREATE TABLE `blog`.`tokens`(
	`id` BINARY(16) DEFAULT (UUID_TO_BIN(UUID())),
    `token` VARCHAR(255) UNIQUE NOT NULL,
    `token_type` VARCHAR(10) NOT NULL,
    `expiration_date` DATETIME,
    `revoked` TINYINT(1) NOT NULL,
    `user_id` BINARY(16),
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `blog`.`users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Social Table
CREATE TABLE `blog`.`social_accounts`(
	`id` BINARY(16) DEFAULT (UUID_TO_BIN(UUID())),
    `provider` VARCHAR(20) NOT NULL COMMENT 'Social network provider',
    `provider_id` VARCHAR(20) NOT NULL,
    `email` VARCHAR(150) NOT NULL COMMENT 'Account Email',
    `name` VARCHAR(100) NOT NULL COMMENT 'User Name',
    `user_id` BINARY(16),
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `blog`.`users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- Post Table 
CREATE TABLE `blog`.`posts`(
    `id` BINARY(16) DEFAULT (UUID_TO_BIN(UUID())),
    `author_id` BINARY(16) NOT NULL,
    `title` VARCHAR(75) NOT NULL,
    `meta_title` VARCHAR(100) NULL,
    `slug` VARCHAR(100) NOT NULL,
    `summary` TINYTEXT NULL,
    `published` TINYINT(1) NOT NULL DEFAULT 0,
    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME NULL DEFAULT NULL,
    `published_at` DATETIME NULL DEFAULT NULL,
    `thumbnail` TEXT,
    `views` INT DEFAULT 0,
    `content` TEXT NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uq_slug` (`slug` ASC),
    INDEX `idx_post_user` (`author_id` ASC),
    CONSTRAINT `fk_post_user`
        FOREIGN KEY (`author_id`)
        REFERENCES `blog`.`users` (`id`)
        ON DELETE CASCADE
        ON UPDATE NO ACTION
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Image Table
CREATE TABLE `blog`.`post_images` (
    `id` BINARY(16) DEFAULT (UUID_TO_BIN(UUID())),
    `post_id` BINARY(16) DEFAULT NULL,
    `url` TEXT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `idx_image_post` (`post_id` ASC),
    CONSTRAINT `fk_image_post`
        FOREIGN KEY (`post_id`)
            REFERENCES `blog`.`posts` (`id`)
            ON DELETE CASCADE
            ON UPDATE NO ACTION
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Post Comment Table
CREATE TABLE `blog`.`post_comments` (
    `id` BINARY(16) DEFAULT (UUID_TO_BIN(UUID())),
    `post_id` BINARY(16) NOT NULL,
    `parent_id` BINARY(16) NULL DEFAULT NULL,
    `title` VARCHAR(100) NOT NULL,
    `published` TINYINT(1) NOT NULL DEFAULT 0,
    `created_at` DATETIME NOT NULL,
    `published_at` DATETIME NULL DEFAULT NULL,
    `content` TEXT NULL DEFAULT NULL,
    `user_id` BINARY(16) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `idx_comment_post` (`post_id` ASC),
    INDEX `idx_comment_user` (`user_id` ASC),
    CONSTRAINT `fk_comment_post`
        FOREIGN KEY (`post_id`)
            REFERENCES `blog`.`posts` (`id`)
            ON DELETE CASCADE
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_comment_user`
        FOREIGN KEY (`user_id`)
            REFERENCES `blog`.`users` (`id`)
            ON DELETE CASCADE
            ON UPDATE NO ACTION
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Post Comment Table - Additional Index and Constraint
ALTER TABLE `blog`.`post_comments`
    ADD INDEX `idx_comment_parent` (`parent_id` ASC);
ALTER TABLE `blog`.`post_comments`
    ADD CONSTRAINT `fk_comment_parent`
        FOREIGN KEY (`parent_id`)
            REFERENCES `blog`.`post_comments` (`id`)
            ON DELETE CASCADE
            ON UPDATE NO ACTION;
ALTER TABLE `blog`.`post_comments`
    ADD INDEX `idx_comment_published` (`published` ASC);


-- Category Table
CREATE TABLE `blog`.`categories` (
    `id` BINARY(16) DEFAULT (UUID_TO_BIN(UUID())),
    `parent_id` BINARY(16) NULL DEFAULT NULL,
    `title` VARCHAR(75) NOT NULL,
    `meta_title` VARCHAR(100) NULL DEFAULT NULL,
    `slug` VARCHAR(100) NOT NULL,
    `content` TEXT NULL DEFAULT NULL,
    `number_of_posts` INT DEFAULT 0,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Category Table - Additional Index and Constraint
ALTER TABLE `blog`.`categories`
    ADD INDEX `idx_category_parent` (`parent_id` ASC);
ALTER TABLE `blog`.`categories`
    ADD CONSTRAINT `fk_category_parent`
        FOREIGN KEY (`parent_id`)
            REFERENCES `blog`.`categories` (`id`)
            ON DELETE CASCADE
            ON UPDATE NO ACTION;

-- Post Category Table
CREATE TABLE `blog`.`post_categories` (
    `post_id` BINARY(16) NOT NULL,
    `category_id` BINARY(16) NOT NULL,
    PRIMARY KEY (`post_id`, `category_id`),
    INDEX `idx_pc_category` (`category_id` ASC),
    INDEX `idx_pc_post` (`post_id` ASC),
    CONSTRAINT `fk_pc_post`
        FOREIGN KEY (`post_id`)
            REFERENCES `blog`.`posts` (`id`)
            ON DELETE CASCADE
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_pc_category`
        FOREIGN KEY (`category_id`)
            REFERENCES `blog`.`categories` (`id`)
            ON DELETE CASCADE
            ON UPDATE NO ACTION
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tag Table
CREATE TABLE `blog`.`tags` (
    `id` BINARY(16) DEFAULT (UUID_TO_BIN(UUID())),
    `title` VARCHAR(75) NOT NULL,
    `meta_title` VARCHAR(100) DEFAULT NULL,
    `slug` VARCHAR(100) NOT NULL,
    `content` TEXT,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Post Tag Table
CREATE TABLE `blog`.`post_tags` (
    `post_id` BINARY(16) NOT NULL,
    `tag_id` BINARY(16) NOT NULL,
    PRIMARY KEY (`post_id`,`tag_id`),
    INDEX `idx_pt_tag` (`tag_id`),
    INDEX `idx_pt_post` (`post_id`),
    CONSTRAINT `fk_pt_post`
        FOREIGN KEY (`post_id`)
            REFERENCES `blog`.`posts` (`id`),
    CONSTRAINT `fk_pt_tag`
        FOREIGN KEY (`tag_id`)
            REFERENCES `blog`.`tags` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

USE blog;
-- Trigger for post count per category
DELIMITER //
CREATE TRIGGER increase_post_count
    AFTER INSERT ON post_categories
    FOR EACH ROW
BEGIN
    UPDATE categories
    SET number_of_posts = number_of_posts + 1
    WHERE id = NEW.category_id;
END //
DELIMITER ;

DELIMITER //
CREATE TRIGGER decrease_post_count
    AFTER DELETE ON post_categories
    FOR EACH ROW
BEGIN
    UPDATE categories
    SET number_of_posts = number_of_posts - 1
    WHERE id = OLD.category_id;
END //
DELIMITER ;

-- Trigger for last_login user
DELIMITER //
CREATE TRIGGER before_user_update_last_login
    BEFORE UPDATE ON blog.users
    FOR EACH ROW
BEGIN
    SET NEW.last_login = NOW();
END;
//
DELIMITER ;

-- Trigger for created_at post
DELIMITER //
CREATE TRIGGER before_post_insert_created_at
    BEFORE INSERT ON blog.posts
    FOR EACH ROW
BEGIN
    SET NEW.created_at = NOW();
END;
//
DELIMITER ;

-- Trigger for updated_at post
DELIMITER //
CREATE TRIGGER before_post_update_updated_at
    BEFORE UPDATE ON blog.posts
    FOR EACH ROW
BEGIN
    SET NEW.updated_at = NOW();
END;
//
DELIMITER ;

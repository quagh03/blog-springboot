-- Database
DROP DATABASE IF EXISTS `blog`;

CREATE SCHEMA `blog` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- User Table
CREATE TABLE `blog`.`users` (
                                `id` BIGINT AUTO_INCREMENT,
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
                                `role` ENUM('ROLE_ADMIN', 'ROLE_AUTHOR', 'ROLE_GUEST') DEFAULT 'ROLE_GUEST',
                                `is_active` TINYINT(1) NOT NULL DEFAULT 0,
                                `facebook_account_id` INT DEFAULT 0,
                                `google_account_id` INT DEFAULT 0,
                                PRIMARY KEY (`id`),
                                UNIQUE INDEX `uq_phone_number` (`phone_number` ASC),
                                UNIQUE INDEX `uq_email` (`email` ASC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Token Table
CREATE TABLE `blog`.`tokens` (
                                 `id` BIGINT AUTO_INCREMENT,
                                 `token` VARCHAR(255) UNIQUE NOT NULL,
                                 `token_type` VARCHAR(10) NOT NULL,
                                 `expiration_date` DATETIME,
                                 `revoked` TINYINT(1) NOT NULL,
                                 `user_id` BIGINT,
                                 PRIMARY KEY (`id`),
                                 FOREIGN KEY (`user_id`) REFERENCES `blog`.`users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Social Table
CREATE TABLE `blog`.`social_accounts` (
                                          `id` BIGINT AUTO_INCREMENT,
                                          `provider` VARCHAR(20) NOT NULL COMMENT 'Social network provider',
                                          `provider_id` VARCHAR(20) NOT NULL,
                                          `email` VARCHAR(150) NOT NULL COMMENT 'Account Email',
                                          `name` VARCHAR(100) NOT NULL COMMENT 'User Name',
                                          `user_id` BIGINT,
                                          PRIMARY KEY (`id`),
                                          FOREIGN KEY (`user_id`) REFERENCES `blog`.`users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Post Table
CREATE TABLE `blog`.`posts` (
                                `id` BIGINT AUTO_INCREMENT,
                                `author_id` BIGINT NOT NULL,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Image Table
CREATE TABLE `blog`.`post_images` (
                                      `id` BIGINT AUTO_INCREMENT,
                                      `post_id` BIGINT DEFAULT NULL,
                                      `url` TEXT NOT NULL,
                                      PRIMARY KEY (`id`),
                                      INDEX `idx_image_post` (`post_id` ASC),
                                      CONSTRAINT `fk_image_post`
                                          FOREIGN KEY (`post_id`)
                                              REFERENCES `blog`.`posts` (`id`)
                                              ON DELETE CASCADE
                                              ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Post Comment Table
-- Post Comment Table
-- Post Comment Table
CREATE TABLE `blog`.`post_comments` (
                                        `id` BIGINT AUTO_INCREMENT,
                                        `post_id` BIGINT NOT NULL,
                                        `parent_id` BIGINT NULL DEFAULT NULL,
                                        `title` VARCHAR(100) NOT NULL,
                                        `published` TINYINT(1) NOT NULL DEFAULT 0,
                                        `created_at` DATETIME NOT NULL,
                                        `published_at` DATETIME NULL DEFAULT NULL,
                                        `content` TEXT NULL DEFAULT NULL,
                                        `user_id` BIGINT NOT NULL,
                                        PRIMARY KEY (`id`),
                                        INDEX `idx_comment_post` (`post_id` ASC),
                                        INDEX `idx_comment_user` (`user_id` ASC),
                                        INDEX `idx_comment_parent` (`parent_id` ASC),
                                        INDEX `idx_comment_published` (`published` ASC),
                                        CONSTRAINT `fk_comment_post`
                                            FOREIGN KEY (`post_id`)
                                                REFERENCES `blog`.`posts` (`id`)
                                                ON DELETE CASCADE
                                                ON UPDATE NO ACTION,
                                        CONSTRAINT `fk_comment_user`
                                            FOREIGN KEY (`user_id`)
                                                REFERENCES `blog`.`users` (`id`)
                                                ON DELETE CASCADE
                                                ON UPDATE NO ACTION,
                                        CONSTRAINT `fk_comment_parent`
                                            FOREIGN KEY (`parent_id`)
                                                REFERENCES `blog`.`post_comments` (`id`)
                                                ON DELETE CASCADE
                                                ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Category Table
CREATE TABLE `blog`.`categories` (
                                     `id` BIGINT AUTO_INCREMENT,
                                     `parent_id` BIGINT NULL DEFAULT NULL,
                                     `title` VARCHAR(75) NOT NULL,
                                     `meta_title` VARCHAR(100) NULL DEFAULT NULL,
                                     `slug` VARCHAR(100) NOT NULL,
                                     `content` TEXT NULL DEFAULT NULL,
                                     `number_of_posts` INT DEFAULT 0,
                                     PRIMARY KEY (`id`),
                                     INDEX `idx_category_parent` (`parent_id` ASC),
                                     CONSTRAINT `fk_category_parent`
                                         FOREIGN KEY (`parent_id`)
                                             REFERENCES `blog`.`categories` (`id`)
                                             ON DELETE CASCADE
                                             ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Post Category Table
CREATE TABLE `blog`.`post_categories` (
                                          `post_id` BIGINT NOT NULL,
                                          `category_id` BIGINT NOT NULL AUTO_INCREMENT,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tag Table
CREATE TABLE `blog`.`tags` (
                               `id` BIGINT AUTO_INCREMENT,
                               `title` VARCHAR(75) NOT NULL,
                               `meta_title` VARCHAR(100) DEFAULT NULL,
                               `slug` VARCHAR(100) NOT NULL,
                               `number_of_posts` INT DEFAULT 0,
                               `content` TEXT,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Post Tag Table
CREATE TABLE `blog`.`post_tags` (
                                    `post_id` BIGINT NOT NULL,
                                    `tag_id` BIGINT NOT NULL,
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
    BEFORE INSERT ON post_categories
    FOR EACH ROW
BEGIN
    SET NEW.category_id = (SELECT id FROM categories WHERE id = NEW.category_id AND number_of_posts = number_of_posts + 1);
END //
DELIMITER ;

DELIMITER //
CREATE TRIGGER decrease_post_count
    BEFORE DELETE ON post_categories
    FOR EACH ROW
BEGIN
    UPDATE categories
    SET number_of_posts = number_of_posts - 1
    WHERE id = OLD.category_id;
END //
DELIMITER ;

-- Trigger for increasing post count per tag
DELIMITER //
CREATE TRIGGER increase_post_tag_count
    BEFORE INSERT ON post_tags
    FOR EACH ROW
BEGIN
    UPDATE tags
    SET number_of_posts = number_of_posts + 1
    WHERE id = NEW.tag_id;
END //
DELIMITER ;

-- Trigger for decreasing post count per tag
DELIMITER //
CREATE TRIGGER decrease_post_tag_count
    BEFORE DELETE ON post_tags
    FOR EACH ROW
BEGIN
    UPDATE tags
    SET number_of_posts = number_of_posts - 1
    WHERE id = OLD.tag_id;
END //
DELIMITER ;


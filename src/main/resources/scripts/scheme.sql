DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users_roles;

CREATE TABLE users
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(250) NOT NULL,
    password VARCHAR(250) NOT NULL
);

CREATE TABLE roles
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL
);

CREATE TABLE users_roles
(
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE posts
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    caption         VARCHAR(250) NOT NULL,
    image           VARCHAR(250) NOT NULL,
    user_id         VARCHAR(250) NOT NULL,
    number_of_likes INT DEFAULT 0
);

CREATE TABLE users_like_posts
(
    user_id INT NOT NULL,
    post_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (post_id) REFERENCES posts (id)
);

CREATE UNIQUE index post_postid_user_userid ON users_like_posts (
    user_id,
    post_id
);
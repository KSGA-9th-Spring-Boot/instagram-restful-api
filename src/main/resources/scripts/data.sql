INSERT INTO roles (name)
VALUES ('ROLE_USER');

INSERT INTO users (fullname, username, password)
VALUES ('ksga', 'ksga', '$2a$10$RZBk0j2RTzjv3IJB84u25OLw5NjX4xlu/EbG.91vWsJJ1pR0DkIvu');

INSERT INTO users (fullname, username, password)
VALUES ('haha', 'ksga', '$2a$10$RZBk0j2RTzjv3IJB84u25OLw5NjX4xlu/EbG.91vWsJJ1pR0DkIvu');

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1);

INSERT INTO posts (caption, image, number_of_likes, user_id)
VALUES ('1', '1', 1, 1);

INSERT INTO posts (caption, image, number_of_likes, user_id)
VALUES ('1', '1', 1, 2);

INSERT INTO posts (caption, image, number_of_likes, user_id)
VALUES ('1', '1', 1, 1);

INSERT INTO comments (content, parent_id, user_id, post_id)
VALUES ('1123', null , 1, 1);

INSERT INTO comments (content, parent_id, user_id, post_id)
VALUES ('213123123123', 1 , 1, 1);
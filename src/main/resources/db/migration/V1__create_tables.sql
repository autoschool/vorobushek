CREATE TABLE posts (
    id BIGINT DEFAULT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    body  TEXT NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    user_id INT

);

CREATE TABLE comments (
    id BIGINT DEFAULT NULL AUTO_INCREMENT PRIMARY KEY,
    body  TEXT NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    post_id INT,
    user_id INT
);

CREATE TABLE users (
  id BIGINT DEFAULT NULL AUTO_INCREMENT PRIMARY KEY,
  email  VARCHAR(256) NOT NULL,
  login  VARCHAR(256) NOT NULL,
  displayName  VARCHAR(256) NOT NULL,
  avatar  VARCHAR(2048)
);
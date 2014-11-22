CREATE TABLE posts (
    id INT DEFAULT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    body  VARCHAR(1000000) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    user_id INT

);

CREATE TABLE comments (
    id INT DEFAULT NULL AUTO_INCREMENT PRIMARY KEY,
    body  VARCHAR(1000000) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    post_id INT,
    user_id INT
);

CREATE TABLE users (
  id INT DEFAULT NULL AUTO_INCREMENT PRIMARY KEY,
  email  VARCHAR(1000000) NOT NULL,
  login  VARCHAR(1000000) NOT NULL,
  displayName  VARCHAR(1000000) NOT NULL
);

INSERT  INTO  users (email, login, displayName) VALUES ('','','Guest')


SHOW DATABASES;
CREATE DATABASE eazybank DEFAULT CHARACTER SET = 'utf8mb4';
USE eazybank;
SHOW TABLES;



-- Abrir o Maven Dependencies > spring-security.core-6.3.1.jar > org.springframework.security.core.userdetails.jdbc > users.ddl
-- https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/jdbc.html

-- create table users(username varchar_ignorecase(50) not null primary key,password varchar_ignorecase(500) not null,enabled boolean not null);
CREATE TABLE users (
	id INT NOT NULL AUTO_INCREMENT,
	username VARCHAR(45) NOT NULL,
    password VARCHAR(45) NOT NULL,
    enabled BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);

-- create table authorities (username varchar_ignorecase(50) not null,authority varchar_ignorecase(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
CREATE TABLE authorities (
	id INT NOT NULL AUTO_INCREMENT,
	username VARCHAR(45) NOT NULL,
    authority VARCHAR(45) NOT NULL,
    PRIMARY KEY (id)
);

-- create unique index ix_auth_username on authorities (username,authority);
-- IGNORE

DESCRIBE users;
DESCRIBE authorities;

DROP TABLE users;
DROP TABLE authorities;

TRUNCATE TABLE users;
TRUNCATE TABLE authorities;

INSERT IGNORE INTO users VALUES (NULL, 'happy', '12345', '1');
INSERT IGNORE INTO authorities VALUES (NULL, 'happy', 'write');

SELECT * FROM users LIMIT 100;
SELECT * FROM authorities LIMIT 100;


CREATE TABLE customer (
	id INT NOT NULL AUTO_INCREMENT,
	email VARCHAR(45) NOT NULL,
    pwd VARCHAR(200) NOT NULL,
    role VARCHAR(45) NOT NULL,
    PRIMARY KEY (id)
);
DROP TABLE customer;
TRUNCATE TABLE customer;
DESCRIBE customer;
INSERT INTO customer (email, pwd, role) VALUES ('dzrrcreations@gmail.com', 'xpto123', 'admin');
SELECT * FROM customer LIMIT 100;

SHOW DATABASES;
USE eazybank;
SHOW TABLES;

-- JdbcUserDetailsManager.java
-- JdbcDaoImpl.java
-- users.ddl

-- create table users(username varchar_ignorecase(50) not null primary key,password varchar_ignorecase(500) not null,enabled boolean not null);
CREATE TABLE users (
	username VARCHAR(50) NOT NULL PRIMARY KEY,
    password VARCHAR(500) NOT NULL,
    enabled BOOLEAN NOT NULL
);

-- create table authorities (username varchar_ignorecase(50) not null,authority varchar_ignorecase(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
CREATE TABLE authorities (
	username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY(username) REFERENCES users(username)
);

-- create unique index ix_auth_username on authorities (username,authority);
CREATE UNIQUE INDEX ix_auth_username ON authorities (username,authority);

-- Describe tables
DESCRIBE users;
DESCRIBE authorities;

-- Create users with no encoder
INSERT INTO users VALUES ('user', '{noop}User@123454321_', '1');
INSERT INTO users VALUES ('admin', '{noop}Admin@543212345_', '1');

-- Create users with BCRYPT encoder
INSERT INTO users VALUES ('user', '{bcrypt}$2a$12$er7CFofbRLMiYlrk0WuRd.DT9dsiYp33DGY2PI/vNnIt7MXeEk9vi', '1');
INSERT INTO users VALUES ('admin', '{bcrypt}$2a$12$lnh7GFMhzZl5kQcG6YOEQ.nspcL5xGj0UjpFVBO/6u5m.ZsIqZdDG', '1');

-- Create users with MD5 encoder
INSERT INTO users VALUES ('user', '{MD5}b0ddc228087f2b807f086f61fccdda8f', '1');
INSERT INTO users VALUES ('admin', '{MD5}c0b04fac954af99f22897a7ec444cc2f', '1');

-- Create authorities
INSERT INTO authorities VALUES ('user', 'read');
INSERT INTO authorities VALUES ('admin', 'admin');

-- Select all values
SELECT * FROM users LIMIT 100;
SELECT * FROM authorities LIMIT 100;
SHOW DATABASES;
USE eazybank;
SHOW TABLES;

-- Customized table
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

-- Create a admin customer (plain text password: Admin@543212345_)
INSERT INTO customer (email, pwd, role) VALUES ('dzrrcreations@gmail.com', '{bcrypt}$2a$12$lnh7GFMhzZl5kQcG6YOEQ.nspcL5xGj0UjpFVBO/6u5m.ZsIqZdDG', 'admin');
-- Create a user customer (plain text password: User@123454321_)
INSERT INTO customer (email, pwd, role) VALUES ('user@gmail.com', '{bcrypt}$2a$12$er7CFofbRLMiYlrk0WuRd.DT9dsiYp33DGY2PI/vNnIt7MXeEk9vi', 'read');

SELECT * FROM customer LIMIT 100;
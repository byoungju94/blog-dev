# BLOG WEB APPLICATION

## GIT Repository URL
- https://github.com/byoungju94/blog-dev/tree/main/server

## URL
- http://blog.byoungju94.me

## Language
- JAVA, JDK 16

## Architecture
- Domain Driven Design
- Event Sourcing
- CQRS
- RESTFUL API

## Dependencies
- spring-boot-starter
- spring-boot-starter-web
- spring-boot-starter-hateoas
- spring-boot-starter-data-jdbc
- jdbc-mysql-connector

## Build And Execute This Application
### Need Two Database Connection
- using mysql command line.
```sql
-- using this database for production.
create database blog_dev;

-- using this database for unit test.
create database blog_dev_test;
```

- generate table into two database using below DDL (./src/main/resources/schema.sql)
```sql
-- todoList
--    1. some of column needs to be indexed.
--    2. change few colums data type into char instead of varchar.

CREATE TABLE tbl_post (
    seq INT NOT NULL AUTO_INCREMENT primary key,
    id VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    state CHAR(30) CHECK (state in ('OPENED', 'BLOCKED', 'HIDDEN', 'DELETED')),
    account_id VARCHAR(255) NOT NULL,
    category_id VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE tbl_content (
    seq INT NOT NULL AUTO_INCREMENT primary key,
    id VARCHAR(255) NOT NULL,
    file_path VARCHAR(255) NOT NULL,
    state CHAR(30) NOT NULL,
    order_num INT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    post_id VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE tbl_comment (
    seq INT NOT NULL AUTO_INCREMENT primary key,
    id VARCHAR(255) NOT NULL,
    content VARCHAR(255) NOT NULL,
    state CHAR(30) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    account_id VARCHAR(255) NOT NULL,
    post_id VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE tbl_account (
    seq INT NOT NULL AUTO_INCREMENT primary key,
    id VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    state VARCHAR(30) CHECK (state in ('ACTIVE', 'LOCKED', 'DELETED'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE tbl_category (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

```

```Bash
./mvn clean install
java -jar ./target/blog-0.0.1-SNAPSHOT.jar
```
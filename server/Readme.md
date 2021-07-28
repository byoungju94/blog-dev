# BLOG WEB APPLICATION

## URL
- github: https://github.com/byoungju94/blog-dev/tree/main/server
- web: http://blog.byoungju94.me
- frontend: https://github.com/byoungju94/blog-dev/tree/main/browser

## Language
- JAVA, JDK 16

## Architecture
- Domain Driven Design
- Event Sourcing
- CQRS
- RESTFUL API
- Test Driven Development

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

- generate table into two database using below DDL `./src/main/resources/schema.sql`
```sql
-- TodoList
--    1. some of column needs to be indexed.
--    2. change some column datatype into char instead of varchar.

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

- You can also change `DataSource` configuration.
- application.properties files are in 
- `./src/main/resources/application.properties`
- `./src/test/resources/application.properties`
```
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/blog_dev?allowPublicKeyRetrieval=true&useSSL=false&useUnicode=true&characterEncoding=utf8
spring.datasource.username=root
spring.datasource.password=root
```   

- Creating FatJar And Execute Spring Application
```Bash
./mvn clean install
java -jar ./target/blog-0.0.1-SNAPSHOT.jar

server port is 8587
```

## API
- Category
```bash
- retrieveAll
    curl http://localhost:8587/api/v1/category/all
        -H "Accept: application/json"

- retrieve
    curl http://localhost:8587/api/v1/category/`{id}`
        -H "Accept: application/json"

- create
    curl -X POST http://localhost:8587/api/v1/category 
         -H "Content-Type: application/json" 
         -H "Accept: application/json" 
         -d "{\"name\": \"Java\"}"
```   

- Account
```

```   

- Post
```bash
- retrieveAll
    curl http://localhost:8587/api/v1/post/all
        -H "Accept: application/json"

- retrieve
    curl http://localhost:8587/api/v1/post/`{id}`
        -H "Accept: application/json"
```

- Content
```bash

```   

- Comment
```bash
- retrieveAll
    curl http://localhost:8587/api/v1/category/all
        -H "Accept: application/json"

- retrieve
    curl http://localhost:8587/api/v1/category/`{id}`
        -H "Accept: application/json"

- update
    curl -X POST http://localhost:8587/api/v1/category 
         -H "Content-Type: application/json" 
         -H "Accept: application/json" 
         -d "{\"id\": \"6eb2377c-eda9-4ec8-a6d4-0dd2215f0c2a\",\"content\": \"Thank's for write this article.\",\"postId\": \"d821770b-0a32-4937-a92e-02079ba41688\",\"accountId\": \"dd0b2a28-b3fe-4063-b456-0ec874564162\"}"

- delete
    curl -X DELETE http://localhost:8587/api/v1/category 
         -H "Content-Type: application/json" 
         -H "Accept: application/json" 
         -d "{\"id\": \"6eb2377c-eda9-4ec8-a6d4-0dd2215f0c2a\",\"content\": \"Thank's for write this article.\",\"postId\": \"d821770b-0a32-4937-a92e-02079ba41688\",\"accountId\": \"dd0b2a28-b3fe-4063-b456-0ec874564162\"}"
```

- SwaggerUI
```
http://localhost:8587/swagger-ui.html
```

## Aggregate Map
https://file.byoungju94.me/aggregate_map.png
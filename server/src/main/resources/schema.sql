CREATE TABLE tbl_post (
    id VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    state VARCHAR(30) CHECK (state in ('OPENED', 'BLOCKED', 'HIDDEN', 'DELETED')),
    account_id VARCHAR(255) NOT NULL,
    category_id VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE tbl_content (
    id BIGINT NOT NULL AUTO_INCREMENT,
    uuid VARCHAR(255) NOT NULL,
    file_path VARCHAR(255) NOT NULL,
    order_num VARCHAR(255) NOT NULL,
    state VARCHAR(30) CHECK (state in ('OPENED', 'BLOCKED', 'HIDDEN')),
    post_id VARCHAR(255) NOT NULL
);

CREATE TABLE tbl_comment (
    id BIGINT NOT NULL AUTO_INCREMENT,
    uuid VARCHAR(255) NOT NULL,
    content VARCHAR(255) NOT NULL,
    state VARCHAR(255) CHECK (state int ('OPENED, BLOCKED, DELETED')),
    created_at TIMESTAMP NOT NULL,
    account_id VARCHAR(255) NOT NULL,
    post_id VARCHAR(255) NOT NULL
);

CREATE TABLE category (
    id VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE tbl_account (
    id BIGINT NOT NULL AUTO_INCREMENT,
    uuid VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(255),
    name VARCHAR(255) NOT NULL,
    is_insert BOOLEAN,
    created_at TIMESTAMP NOT NULL,
    state VARCHAR(30) CHECK (state in ('ACTIVE', 'LOCKED', 'DELETED'))
);

CREATE TABLE label (
    id VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    color VARCHAR(255) NOT NULL,
    post_id VARCHAR(255) NOT NULL
);

CREATE TABLE comment (
    id VARCHAR(255) NOT NULL,
    version INT NOT NULL,
    post_id VARCHAR(255) NOT NULL,
    writer_id VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE comment_content (
    comment_id VARCHAR(255) NOT NULL,
    body VARCHAR(255) NOT NULL,
    mime_type VARCHAR(255) NOT NULL
);

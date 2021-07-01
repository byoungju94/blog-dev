CREATE TABLE post (
    id VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    author_id VARCHAR(255) NOT NULL,
    is_insert BOOLEAN,
    content_file_path VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    state VARCHAR(30) CHECK (state in ('ACTIVE', 'LOCKED', 'DELETED'))
);

CREATE TABLE account (
    id VARCHAR(255) NOT NULL,
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
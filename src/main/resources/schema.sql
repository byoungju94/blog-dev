CREATE TABLE post (
    id VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    author_id BIGINT NOT NULL,
    is_insert BOOLEAN,
    content_file_path VARCHAR(255) NOT NULL,
    state VARCHAR(30) CHECK (state in ('ACTIVE', 'LOCKED', 'DELETED'))
);
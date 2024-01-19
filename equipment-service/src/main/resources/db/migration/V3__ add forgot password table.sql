CREATE TABLE forgot_password
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    uuid       VARCHAR(255)          NOT NULL,
    user_id    BIGINT                NULL,
    email      VARCHAR(255)          NOT NULL,
    created_at BIGINT                NOT NULL,
    CONSTRAINT pk_forgot_password PRIMARY KEY (id)
);

ALTER TABLE forgot_password
    ADD CONSTRAINT USER FOREIGN KEY (user_id) REFERENCES users (id);
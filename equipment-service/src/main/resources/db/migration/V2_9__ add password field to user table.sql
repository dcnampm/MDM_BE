ALTER TABLE users
    ADD password VARCHAR(255) NULL;

UPDATE users
SET password = ''
WHERE password IS NULL;
ALTER TABLE users
    MODIFY password VARCHAR(255) NOT NULL;
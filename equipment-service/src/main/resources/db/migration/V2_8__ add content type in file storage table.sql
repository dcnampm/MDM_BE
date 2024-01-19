ALTER TABLE file_storage
    ADD content_type VARCHAR(255) NULL;

ALTER TABLE file_storage
    MODIFY content_type VARCHAR(255) NOT NULL;
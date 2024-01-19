ALTER TABLE file_storage
    DROP COLUMN associated_entity_id;

ALTER TABLE file_storage
    ADD associated_entity_id BIGINT NOT NULL;
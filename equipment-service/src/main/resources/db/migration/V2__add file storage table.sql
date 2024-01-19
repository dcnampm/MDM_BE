CREATE TABLE file_storage
(
    id                     BIGINT AUTO_INCREMENT NOT NULL,
    name                   VARCHAR(255)          NOT NULL,
    extension              VARCHAR(255)          NOT NULL,
    associated_entity_type VARCHAR(255)          NOT NULL,
    associated_entity_id   VARCHAR(255)          NOT NULL,
    `description`          VARCHAR(255)          NOT NULL,
    data                   MEDIUMBLOB                  NOT NULL,
    CONSTRAINT pk_file_storage PRIMARY KEY (id)
);
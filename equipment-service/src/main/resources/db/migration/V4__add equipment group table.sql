CREATE TABLE equipment_groups
(
    id    BIGINT AUTO_INCREMENT NOT NULL,
    name  VARCHAR(255)          NULL,
    note  VARCHAR(255)          NULL,
    alias VARCHAR(255)          NULL,
    CONSTRAINT pk_equipment_groups PRIMARY KEY (id)
);

ALTER TABLE equipment_categories
    CHANGE super_category_id group_id BIGINT;

ALTER TABLE equipment_categories
    ADD CONSTRAINT alias UNIQUE (alias);
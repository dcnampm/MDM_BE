CREATE TABLE supply_groups
(
    id    BIGINT AUTO_INCREMENT NOT NULL,
    name  VARCHAR(255)          NOT NULL,
    alias VARCHAR(255)          NULL,
    note  TEXT                  NULL,
    CONSTRAINT pk_supply_groups PRIMARY KEY (id)
);

ALTER TABLE supply_categories
    CHANGE super_category_id group_id BIGINT;
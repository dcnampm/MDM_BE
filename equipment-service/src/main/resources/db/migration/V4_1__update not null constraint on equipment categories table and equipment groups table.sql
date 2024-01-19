ALTER TABLE equipment_categories
    ADD CONSTRAINT FK_EQUIPMENT_CATEGORIES_ON_GROUP FOREIGN KEY (group_id) REFERENCES equipment_groups (id);

ALTER TABLE equipment_categories
    DROP FOREIGN KEY SUPER_CATEGORY;

UPDATE equipment_categories
SET alias = ''
WHERE alias IS NULL;
ALTER TABLE equipment_categories
    MODIFY alias VARCHAR(255) NOT NULL;

UPDATE equipment_groups
SET alias = ''
WHERE alias IS NULL;
ALTER TABLE equipment_groups
    MODIFY alias VARCHAR(255) NOT NULL;

UPDATE equipment_categories
SET group_id = ''
WHERE group_id IS NULL;
ALTER TABLE equipment_categories
    MODIFY group_id BIGINT NOT NULL;

UPDATE equipment_groups
SET name = ''
WHERE name IS NULL;
ALTER TABLE equipment_groups
    MODIFY name VARCHAR(255) NOT NULL;
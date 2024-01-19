ALTER TABLE departments
    DROP COLUMN created_at;

ALTER TABLE departments
    DROP COLUMN updated_at;

ALTER TABLE equipment_categories
    DROP COLUMN created_at;

ALTER TABLE equipment_categories
    DROP COLUMN updated_at;

ALTER TABLE equipment_supply_usages
    DROP COLUMN created_at;

ALTER TABLE equipment_supply_usages
    DROP COLUMN updated_at;

ALTER TABLE equipments
    DROP COLUMN created_at;

ALTER TABLE equipments
    DROP COLUMN updated_at;

ALTER TABLE projects
    DROP COLUMN created_at;

ALTER TABLE projects
    DROP COLUMN updated_at;

ALTER TABLE supply_categories
    DROP COLUMN created_at;

ALTER TABLE supply_categories
    DROP COLUMN updated_at;

ALTER TABLE users
    DROP COLUMN created_at;

ALTER TABLE users
    DROP COLUMN updated_at;
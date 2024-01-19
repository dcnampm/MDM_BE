ALTER TABLE supply_categories
    DROP FOREIGN KEY FK_SUPPLY_CATEGORIES_ON_SUPER_CATEGORY;

DROP TABLE supply_groups;

ALTER TABLE supply_categories
    DROP COLUMN group_id;
ALTER TABLE inventories
    DROP COLUMN inventory_status;

UPDATE forgot_password
SET uuid = ''
WHERE uuid IS NULL;
ALTER TABLE forgot_password
    MODIFY uuid VARCHAR(255) NOT NULL;
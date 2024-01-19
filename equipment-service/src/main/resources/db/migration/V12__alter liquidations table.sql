ALTER TABLE liquidations
    ADD approval_date datetime NULL;

ALTER TABLE liquidations
    ADD approver_note TEXT NULL;

ALTER TABLE liquidations
    ADD code VARCHAR(255) NULL;

ALTER TABLE liquidations
    ADD created_date datetime NULL;

ALTER TABLE liquidations
    ADD creator_id BIGINT NULL;

ALTER TABLE liquidations
    ADD creator_note TEXT NULL;

ALTER TABLE liquidations
    CHANGE note liquidation_note TEXT;

ALTER TABLE liquidations
    ADD status VARCHAR(255) NULL;

UPDATE liquidations
SET code = ''
WHERE code IS NULL;
ALTER TABLE liquidations
    MODIFY code VARCHAR(255) NOT NULL;

UPDATE liquidations
SET created_date = ''
WHERE created_date IS NULL;
ALTER TABLE liquidations
    MODIFY created_date datetime NOT NULL;

ALTER TABLE liquidations
    ADD CONSTRAINT FK_LIQUIDATIONS_ON_CREATOR FOREIGN KEY (creator_id) REFERENCES users (id)  ON DELETE SET NULL ON UPDATE SET NULL;
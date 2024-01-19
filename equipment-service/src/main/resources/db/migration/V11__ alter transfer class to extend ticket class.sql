ALTER TABLE transfers
    ADD approval_date datetime NULL;

ALTER TABLE transfers
    ADD approver_note TEXT NULL;

ALTER TABLE transfers
    ADD code VARCHAR(255) NULL;

ALTER TABLE transfers
    ADD created_date datetime NULL;

ALTER TABLE transfers
    ADD creator_id BIGINT NULL;

ALTER TABLE transfers
    ADD creator_note TEXT NULL;

ALTER TABLE transfers
    ADD status VARCHAR(255) NULL;

ALTER TABLE transfers
    CHANGE note transfer_note TEXT;

UPDATE transfers
SET code = ''
WHERE code IS NULL;
ALTER TABLE transfers
    MODIFY code VARCHAR(255) NOT NULL;

UPDATE transfers
SET created_date = ''
WHERE created_date IS NULL;
ALTER TABLE transfers
    MODIFY created_date datetime NOT NULL;

ALTER TABLE transfers
    ADD CONSTRAINT FK_TRANSFERS_ON_CREATOR FOREIGN KEY (creator_id) REFERENCES users (id) ON DELETE SET NULL ON UPDATE SET NULL;
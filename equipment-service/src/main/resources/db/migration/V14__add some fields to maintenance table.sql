ALTER TABLE maintenance_tickets
    ADD approval_date datetime NULL;

ALTER TABLE maintenance_tickets
    ADD approver_id BIGINT NULL;

ALTER TABLE maintenance_tickets
    ADD approver_note TEXT NULL;

ALTER TABLE maintenance_tickets
    ADD code VARCHAR(255) NULL;

ALTER TABLE maintenance_tickets
    ADD created_date datetime NULL;

ALTER TABLE maintenance_tickets
    ADD creator_id BIGINT NULL;

ALTER TABLE maintenance_tickets
    ADD creator_note TEXT NULL;

ALTER TABLE maintenance_tickets
    CHANGE note maintenance_note TEXT;

ALTER TABLE maintenance_tickets
    CHANGE evaluation_status maintenance_status VARCHAR(255);

ALTER TABLE maintenance_tickets
    ADD status VARCHAR(255) NULL;

ALTER TABLE maintenance_tickets
    MODIFY code VARCHAR(255) NOT NULL;

ALTER TABLE maintenance_tickets
    MODIFY created_date datetime NOT NULL;

ALTER TABLE maintenance_tickets
    ADD CONSTRAINT FK_MAINTENANCE_TICKETS_ON_APPROVER FOREIGN KEY (approver_id) REFERENCES users (id)  ON DELETE SET NULL ON UPDATE SET NULL;

ALTER TABLE maintenance_tickets
    ADD CONSTRAINT FK_MAINTENANCE_TICKETS_ON_CREATOR FOREIGN KEY (creator_id) REFERENCES users (id)  ON DELETE SET NULL ON UPDATE SET NULL;
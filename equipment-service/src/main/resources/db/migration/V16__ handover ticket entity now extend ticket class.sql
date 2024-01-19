ALTER TABLE handover_tickets
    ADD code VARCHAR(255) NULL;

ALTER TABLE handover_tickets
    ADD created_date datetime NULL;

ALTER TABLE handover_tickets
    CHANGE created_by creator_id BIGINT;

ALTER TABLE handover_tickets
    ADD creator_note TEXT NULL;

ALTER TABLE handover_tickets
    CHANGE note handover_note TEXT;

UPDATE handover_tickets
SET code = ''
WHERE code IS NULL;
ALTER TABLE handover_tickets
    MODIFY code VARCHAR(255) NOT NULL;

UPDATE handover_tickets
SET created_date = ''
WHERE created_date IS NULL;
ALTER TABLE handover_tickets
    MODIFY created_date datetime NOT NULL;

ALTER TABLE handover_tickets
    MODIFY status VARCHAR(255) NULL;
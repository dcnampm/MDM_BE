ALTER TABLE handoverTickets
    ADD approver_note VARCHAR(255) NULL;

ALTER TABLE handoverTickets
    ADD status VARCHAR(255) NULL;

UPDATE handoverTickets
SET status = 'PENDING'
WHERE status IS NULL;
ALTER TABLE handoverTickets
    MODIFY status VARCHAR(255) NOT NULL;
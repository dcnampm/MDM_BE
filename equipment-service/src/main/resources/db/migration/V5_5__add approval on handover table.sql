ALTER TABLE handoverTickets
    ADD approval_date date NULL;

ALTER TABLE handoverTickets
    ADD approver_id BIGINT NULL;

ALTER TABLE handoverTickets
    ADD CONSTRAINT FK_HANDOVERS_ON_APPROVER FOREIGN KEY (approver_id) REFERENCES users (id) ON DELETE SET NULL ON UPDATE SET NULL;
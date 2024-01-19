ALTER TABLE handoverTickets
    ADD created_by BIGINT NULL;

UPDATE handoverTickets
SET created_by = ''
WHERE created_by IS NULL;
ALTER TABLE handoverTickets
    MODIFY created_by BIGINT NOT NULL;

ALTER TABLE handoverTickets
    ADD CONSTRAINT FK_HANDOVERS_ON_CREATED_BY FOREIGN KEY (created_by) REFERENCES users (id) ON UPDATE SET NULL ON DELETE SET NULL;
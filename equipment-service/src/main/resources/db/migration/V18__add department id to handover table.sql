ALTER TABLE handover_tickets
    ADD department_id BIGINT NULL;

UPDATE handover_tickets
SET department_id = '9'
WHERE department_id IS NULL;
ALTER TABLE handover_tickets
    MODIFY department_id BIGINT NOT NULL;

ALTER TABLE handover_tickets
    ADD CONSTRAINT FK_HANDOVER_TICKETS_ON_DEPARTMENT FOREIGN KEY (department_id) REFERENCES departments (id)  ON DELETE SET NULL ON UPDATE SET NULL;
ALTER TABLE handover_tickets
    DROP FOREIGN KEY FK_HANDOVERS_ON_DEPARTMENT;

ALTER TABLE handover_tickets
    DROP COLUMN department_id;
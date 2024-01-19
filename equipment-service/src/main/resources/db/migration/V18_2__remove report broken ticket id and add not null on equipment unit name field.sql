ALTER TABLE equipment_units
    ADD CONSTRAINT uc_equipment_units_name UNIQUE (name);

ALTER TABLE repair_tickets
    DROP FOREIGN KEY FK_REPAIR_TICKETS_ON_REPORT_BROKEN_TICKET;

ALTER TABLE repair_tickets
    DROP COLUMN report_broken_ticket_id;

ALTER TABLE equipment_units
    MODIFY name VARCHAR(255) NOT NULL;
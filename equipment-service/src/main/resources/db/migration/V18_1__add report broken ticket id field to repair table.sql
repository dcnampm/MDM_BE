ALTER TABLE repair_tickets
    ADD report_broken_ticket_id BIGINT NULL;

ALTER TABLE repair_tickets
    ADD CONSTRAINT FK_REPAIR_TICKETS_ON_REPORT_BROKEN_TICKET FOREIGN KEY (report_broken_ticket_id) REFERENCES report_broken_tickets (id);
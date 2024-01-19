ALTER TABLE broken_report_tickets
    ADD approval_date datetime NULL;

ALTER TABLE broken_report_tickets
    ADD approver_id BIGINT NULL;

ALTER TABLE broken_report_tickets
    ADD approver_note TEXT NULL;

ALTER TABLE broken_report_tickets
    CHANGE ticket_code code VARCHAR(255);

ALTER TABLE broken_report_tickets
    ADD created_date datetime NULL;

ALTER TABLE broken_report_tickets
    ADD creator_id BIGINT NULL;

ALTER TABLE broken_report_tickets
    ADD creator_note TEXT NULL;

ALTER TABLE repair_tickets
    ADD approval_date datetime NULL;

ALTER TABLE repair_tickets
    ADD approver_id BIGINT NULL;

ALTER TABLE repair_tickets
    ADD approver_note TEXT NULL;

ALTER TABLE repair_tickets
    CHANGE ticket_code code VARCHAR(255);

ALTER TABLE repair_tickets
    ADD created_date datetime NULL;

ALTER TABLE repair_tickets
    ADD creator_id BIGINT NULL;

ALTER TABLE repair_tickets
    ADD creator_note TEXT NULL;

ALTER TABLE broken_report_tickets
    MODIFY created_date datetime NOT NULL;

ALTER TABLE repair_tickets
    MODIFY created_date datetime NOT NULL;

ALTER TABLE broken_report_tickets
    ADD CONSTRAINT FK_BROKEN_REPORT_TICKETS_ON_APPROVER FOREIGN KEY (approver_id) REFERENCES users (id) ON DELETE SET NULL ON UPDATE SET NULL ;

ALTER TABLE broken_report_tickets
    ADD CONSTRAINT FK_BROKEN_REPORT_TICKETS_ON_CREATOR FOREIGN KEY (creator_id) REFERENCES users (id) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE repair_tickets
    ADD CONSTRAINT FK_REPAIR_TICKETS_ON_APPROVER FOREIGN KEY (approver_id) REFERENCES users (id)  ON DELETE SET NULL ON UPDATE SET NULL;

ALTER TABLE repair_tickets
    ADD CONSTRAINT FK_REPAIR_TICKETS_ON_CREATOR FOREIGN KEY (creator_id) REFERENCES users (id)  ON DELETE SET NULL ON UPDATE SET NULL;

ALTER TABLE broken_report_ticket_processes
    DROP FOREIGN KEY FK_BROKEN_REPORT_TICKET_PROCESSES_ON_APPROVER;

ALTER TABLE broken_report_ticket_processes
    DROP FOREIGN KEY FK_BROKEN_REPORT_TICKET_PROCESSES_ON_BROKEN_REPORT_TICKET;

ALTER TABLE broken_report_ticket_processes
    DROP FOREIGN KEY FK_BROKEN_REPORT_TICKET_PROCESSES_ON_CREATOR;

ALTER TABLE repair_ticket_processes
    DROP FOREIGN KEY FK_REPAIR_TICKET_PROCESSES_ON_APPROVER;

ALTER TABLE repair_ticket_processes
    DROP FOREIGN KEY FK_REPAIR_TICKET_PROCESSES_ON_CREATOR;

ALTER TABLE repair_ticket_processes
    DROP FOREIGN KEY FK_REPAIR_TICKET_PROCESSES_ON_REPAIR_TICKET;

DROP TABLE broken_report_ticket_processes;

DROP TABLE repair_ticket_processes;
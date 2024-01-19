CREATE TABLE broken_report_ticket_processes
(
    id                      BIGINT AUTO_INCREMENT NOT NULL,
    creator_id              BIGINT                NULL,
    created_date            datetime              NOT NULL,
    creator_note            TEXT                  NULL,
    approval_date           datetime              NULL,
    approver_note           TEXT                  NULL,
    approver_id             BIGINT                NULL,
    is_accepted             BIT(1)                NULL,
    broken_report_ticket_id BIGINT                NOT NULL,
    CONSTRAINT pk_broken_report_ticket_processes PRIMARY KEY (id)
);

CREATE TABLE broken_report_tickets
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    ticket_code  BIGINT                NOT NULL,
    status       VARCHAR(255)          NULL,
    equipment_id BIGINT                NOT NULL,
    reason       TEXT                  NULL,
    CONSTRAINT pk_broken_report_tickets PRIMARY KEY (id)
);

CREATE TABLE repair_ticket_processes
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    creator_id       BIGINT                NULL,
    created_date     datetime              NOT NULL,
    creator_note     TEXT                  NULL,
    approval_date    datetime              NULL,
    approver_note    TEXT                  NULL,
    approver_id      BIGINT                NULL,
    is_accepted      BIT(1)                NULL,
    repair_ticket_id BIGINT                NOT NULL,
    CONSTRAINT pk_repair_ticket_processes PRIMARY KEY (id)
);

CREATE TABLE repair_tickets
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    ticket_code       BIGINT                NOT NULL,
    status            VARCHAR(255)          NULL,
    equipment_id      BIGINT                NOT NULL,
    estimated_cost    BIGINT                NULL,
    repair_status     VARCHAR(255)          NULL,
    repair_start_date datetime              NULL,
    repair_end_date   datetime              NULL,
    actual_cost       BIGINT                NULL,
    repair_company_id BIGINT                NOT NULL,
    CONSTRAINT pk_repair_tickets PRIMARY KEY (id)
);

ALTER TABLE broken_report_tickets
    ADD CONSTRAINT ticket_code UNIQUE (ticket_code);

ALTER TABLE repair_tickets
    ADD CONSTRAINT ticket_code UNIQUE (ticket_code);

ALTER TABLE broken_report_tickets
    ADD CONSTRAINT FK_BROKEN_REPORT_TICKETS_ON_EQUIPMENT FOREIGN KEY (equipment_id) REFERENCES equipments (id);

ALTER TABLE broken_report_ticket_processes
    ADD CONSTRAINT FK_BROKEN_REPORT_TICKET_PROCESSES_ON_APPROVER FOREIGN KEY (approver_id) REFERENCES users (id);

ALTER TABLE broken_report_ticket_processes
    ADD CONSTRAINT FK_BROKEN_REPORT_TICKET_PROCESSES_ON_BROKEN_REPORT_TICKET FOREIGN KEY (broken_report_ticket_id) REFERENCES broken_report_tickets (id);

ALTER TABLE broken_report_ticket_processes
    ADD CONSTRAINT FK_BROKEN_REPORT_TICKET_PROCESSES_ON_CREATOR FOREIGN KEY (creator_id) REFERENCES users (id);

ALTER TABLE repair_tickets
    ADD CONSTRAINT FK_REPAIR_TICKETS_ON_EQUIPMENT FOREIGN KEY (equipment_id) REFERENCES equipments (id);

ALTER TABLE repair_tickets
    ADD CONSTRAINT FK_REPAIR_TICKETS_ON_REPAIR_COMPANY FOREIGN KEY (repair_company_id) REFERENCES suppliers (id);

ALTER TABLE repair_ticket_processes
    ADD CONSTRAINT FK_REPAIR_TICKET_PROCESSES_ON_APPROVER FOREIGN KEY (approver_id) REFERENCES users (id);

ALTER TABLE repair_ticket_processes
    ADD CONSTRAINT FK_REPAIR_TICKET_PROCESSES_ON_CREATOR FOREIGN KEY (creator_id) REFERENCES users (id);

ALTER TABLE repair_ticket_processes
    ADD CONSTRAINT FK_REPAIR_TICKET_PROCESSES_ON_REPAIR_TICKET FOREIGN KEY (repair_ticket_id) REFERENCES repair_tickets (id);

ALTER TABLE repairs
    DROP FOREIGN KEY FK_REPAIRS_ON_APPROVER;

ALTER TABLE repairs
    DROP FOREIGN KEY FK_REPAIRS_ON_EQUIPMENT;

ALTER TABLE repairs
    DROP FOREIGN KEY FK_REPAIRS_ON_REPAIR_COMPANY;

ALTER TABLE repairs
    DROP FOREIGN KEY FK_REPAIRS_ON_REPORTING_PERSON;

DROP TABLE repairs;

UPDATE users
SET birthday = ''
WHERE birthday IS NULL;
ALTER TABLE users
    MODIFY birthday datetime NOT NULL;

ALTER TABLE transfers
    MODIFY date_transfer datetime NOT NULL;

ALTER TABLE handoverTickets
    MODIFY handover_date datetime NOT NULL;

ALTER TABLE inventories
    MODIFY inventory_date datetime NOT NULL;

ALTER TABLE clinic_environment_inspections
    MODIFY last_time datetime NOT NULL;

ALTER TABLE cv_radiations
    MODIFY last_time datetime NOT NULL;

ALTER TABLE external_quality_assessments
    MODIFY last_time datetime NOT NULL;

ALTER TABLE inspections
    MODIFY last_time datetime NOT NULL;

ALTER TABLE maintenances
    MODIFY last_time datetime NOT NULL;

ALTER TABLE radiation_inspections
    MODIFY last_time datetime NOT NULL;

ALTER TABLE liquidations
    MODIFY liquidation_date datetime NOT NULL;
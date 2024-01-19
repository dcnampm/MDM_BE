ALTER TABLE broken_report_tickets
    ADD priority VARCHAR(255) NULL;

UPDATE broken_report_tickets
SET priority = ''
WHERE priority IS NULL;
ALTER TABLE broken_report_tickets
    MODIFY priority VARCHAR(255) NOT NULL;

UPDATE broken_report_tickets
SET code = ''
WHERE code IS NULL;
ALTER TABLE broken_report_tickets
    MODIFY code VARCHAR(255) NOT NULL;

UPDATE repair_tickets
SET code = ''
WHERE code IS NULL;
ALTER TABLE repair_tickets
    MODIFY code VARCHAR(255) NOT NULL;
ALTER TABLE handoverTickets
    DROP FOREIGN KEY FK_HANDOVERS_ON_APPROVER;

ALTER TABLE handoverTickets
    DROP COLUMN approval_date;

ALTER TABLE handoverTickets
    DROP COLUMN approver_id;
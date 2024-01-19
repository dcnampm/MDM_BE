ALTER TABLE handoverTickets
    ADD approval_date date NULL;

ALTER TABLE handoverTickets
    DROP COLUMN acceptance_date;
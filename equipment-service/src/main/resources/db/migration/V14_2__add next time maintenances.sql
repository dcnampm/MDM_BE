ALTER TABLE maintenance_tickets
    ADD next_time datetime NULL;

ALTER TABLE maintenance_tickets
    MODIFY last_time datetime NULL;
ALTER TABLE inspection_tickets
    CHANGE last_time inspection_date datetime;

ALTER TABLE maintenance_tickets
    CHANGE last_time maintenance_date datetime;
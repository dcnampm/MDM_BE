ALTER TABLE handoverTickets
    ADD note TEXT NULL;

ALTER TABLE notifications
    MODIFY content JSON;
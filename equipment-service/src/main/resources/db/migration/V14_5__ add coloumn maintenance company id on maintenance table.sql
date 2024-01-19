ALTER TABLE maintenance_tickets
    ADD maintenance_company_id BIGINT NULL;

ALTER TABLE maintenance_tickets
    ADD CONSTRAINT FK_MAINTENANCE_TICKETS_ON_MAINTENANCE_COMPANY FOREIGN KEY (maintenance_company_id) REFERENCES suppliers (id);
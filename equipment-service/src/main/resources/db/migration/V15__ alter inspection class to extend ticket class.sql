ALTER TABLE inspections
    ADD approval_date datetime NULL;

ALTER TABLE inspections
    ADD approver_id BIGINT NULL;

ALTER TABLE inspections
    ADD approver_note TEXT NULL;

ALTER TABLE inspections
    ADD code VARCHAR(255) NULL;

ALTER TABLE inspections
    ADD created_date datetime NULL;

ALTER TABLE inspections
    ADD creator_id BIGINT NULL;

ALTER TABLE inspections
    ADD creator_note TEXT NULL;

ALTER TABLE inspections
    CHANGE note inspection_note TEXT;

ALTER TABLE inspections
    ADD maintenance_company_id BIGINT NULL;

ALTER TABLE inspections
    CHANGE evaluation_status status VARCHAR(255);

ALTER TABLE inspections
    MODIFY code VARCHAR(255) NOT NULL;

ALTER TABLE inspections
    MODIFY created_date datetime NOT NULL;

ALTER TABLE inspections
    ADD CONSTRAINT FK_INSPECTIONS_ON_APPROVER FOREIGN KEY (approver_id) REFERENCES users (id) ON DELETE SET NULL ON UPDATE SET NULL;

ALTER TABLE inspections
    ADD CONSTRAINT FK_INSPECTIONS_ON_CREATOR FOREIGN KEY (creator_id) REFERENCES users (id) ON DELETE SET NULL ON UPDATE SET NULL ;

ALTER TABLE inspections
    ADD CONSTRAINT FK_INSPECTIONS_ON_MAINTENANCE_COMPANY FOREIGN KEY (maintenance_company_id) REFERENCES suppliers (id);
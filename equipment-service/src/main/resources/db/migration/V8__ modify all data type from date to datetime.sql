ALTER TABLE handoverTickets
    MODIFY approval_date datetime;

ALTER TABLE repairs
    MODIFY approval_date datetime;

ALTER TABLE users
    MODIFY birthday datetime;

ALTER TABLE repairs
    MODIFY broken_report_date datetime;

ALTER TABLE supplies
    MODIFY create_at datetime;

ALTER TABLE transfers
    MODIFY date_transfer datetime;

ALTER TABLE projects
    MODIFY end_date datetime;

ALTER TABLE handoverTickets
    MODIFY handover_date datetime;

ALTER TABLE inventories
    MODIFY inventory_date datetime;

ALTER TABLE equipments
    MODIFY joint_venture_contract_expiration_date datetime;

ALTER TABLE clinic_environment_inspections
    MODIFY last_time datetime;

ALTER TABLE cv_radiations
    MODIFY last_time datetime;

ALTER TABLE external_quality_assessments
    MODIFY last_time datetime;

ALTER TABLE inspections
    MODIFY last_time datetime;

ALTER TABLE maintenances
    MODIFY last_time datetime;

ALTER TABLE radiation_inspections
    MODIFY last_time datetime;

ALTER TABLE liquidations
    MODIFY liquidation_date datetime;

ALTER TABLE repairs
    MODIFY repair_completion_date datetime;

ALTER TABLE repairs
    MODIFY repair_start_date datetime;

ALTER TABLE projects
    MODIFY start_date datetime;

ALTER TABLE supplies
    MODIFY update_at datetime;

ALTER TABLE equipments
    MODIFY warehouse_import_date datetime;

ALTER TABLE equipments
    MODIFY warranty_expiration_date datetime;
CREATE TABLE clinic_environment_inspections
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    equipment_id      BIGINT                NOT NULL,
    last_time         date                  NOT NULL,
    note              TEXT                  NULL,
    price             DOUBLE                NULL,
    evaluation_status INT                   NULL,
    CONSTRAINT pk_clinic_environment_inspections PRIMARY KEY (id)
);

CREATE TABLE cv_radiations
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    equipment_id      BIGINT                NOT NULL,
    last_time         date                  NOT NULL,
    note              TEXT                  NULL,
    price             DOUBLE                NULL,
    evaluation_status INT                   NULL,
    CONSTRAINT pk_cv_radiations PRIMARY KEY (id)
);

CREATE TABLE departments
(
    id                    BIGINT AUTO_INCREMENT NOT NULL,
    name                  VARCHAR(255)          NOT NULL,
    alias                 VARCHAR(255)          NULL,
    phone                 VARCHAR(255)          NULL,
    email                 VARCHAR(255)          NULL,
    address               VARCHAR(255)          NULL,
    active_status         VARCHAR(255)          NULL,
    contact_person_id     BIGINT                NULL,
    head_of_department_id BIGINT                NULL,
    chief_nurse_id        BIGINT                NULL,
    manager_id            BIGINT                NULL,
    created_at            date                  NULL,
    updated_at            date                  NULL,
    CONSTRAINT pk_departments PRIMARY KEY (id)
);

CREATE TABLE equipment_categories
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    name              VARCHAR(255)          NOT NULL,
    note              TEXT                  NULL,
    alias             VARCHAR(255)          NULL,
    super_category_id BIGINT                NULL,
    created_at        date                  NULL,
    updated_at        date                  NULL,
    CONSTRAINT pk_equipment_categories PRIMARY KEY (id)
);

CREATE TABLE equipment_supply_usages
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    equipment_id BIGINT                NULL,
    supply_id    BIGINT                NULL,
    note         TEXT                  NULL,
    amount       DOUBLE                NOT NULL,
    amount_used  DOUBLE                NOT NULL,
    created_at   date                  NULL,
    updated_at   date                  NULL,
    CONSTRAINT pk_equipment_supply_usages PRIMARY KEY (id)
);

CREATE TABLE equipments
(
    id                                     BIGINT AUTO_INCREMENT NOT NULL,
    name                                   VARCHAR(255)          NOT NULL,
    model                                  VARCHAR(255)          NOT NULL,
    serial                                 VARCHAR(255)          NOT NULL,
    code                                   VARCHAR(255)          NULL,
    hash_code                              VARCHAR(255)          NULL,
    risk_level                             VARCHAR(255)          NULL,
    technical_parameter                    TEXT                  NULL,
    warehouse_import_date                  date                  NULL,
    year_of_manufacture                    SMALLINT              NULL,
    year_in_use                            SMALLINT              NULL,
    configuration                          TEXT                  NULL,
    import_price                           DOUBLE                NULL,
    initial_value                          DOUBLE                NULL,
    annual_depreciation                    DOUBLE                NULL,
    usage_procedure                        TEXT                  NULL,
    note                                   TEXT                  NULL,
    status                                 VARCHAR(255)          NOT NULL,
    manufacturer                           VARCHAR(255)          NULL,
    manufacturing_country                  VARCHAR(255)          NULL,
    category_id                            BIGINT                NULL,
    department_id                          BIGINT                NULL,
    regular_maintenance                    INT                   NULL,
    regular_inspection                     INT                   NULL,
    regular_radiation_inspection           INT                   NULL,
    regular_external_quality_assessment    INT                   NULL,
    regular_clinic_environment_inspection  INT                   NULL,
    regular_cv_radiation                   INT                   NULL,
    joint_venture_contract_expiration_date date                  NULL,
    warranty_expiration_date               date                  NULL,
    project_id                             BIGINT                NULL,
    supplier_id                            BIGINT                NULL,
    created_at                             date                  NULL,
    updated_at                             date                  NULL,
    deleted                                BIT(1)                NOT NULl DEFAULT 0,
    CONSTRAINT pk_equipments PRIMARY KEY (id)
);

CREATE TABLE external_quality_assessments
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    equipment_id      BIGINT                NOT NULL,
    last_time         date                  NOT NULL,
    note              TEXT                  NULL,
    price             DOUBLE                NULL,
    evaluation_status INT                   NULL,
    CONSTRAINT pk_external_quality_assessments PRIMARY KEY (id)
);

CREATE TABLE handoverTickets
(
    id                    BIGINT AUTO_INCREMENT NOT NULL,
    equipment_id          BIGINT                NOT NULL,
    equipment_status      INT                   NOT NULL,
    handover_date         date                  NOT NULL,
    document              TEXT                  NULL,
    responsible_person_id BIGINT                NOT NULL,
    department_id         BIGINT                NOT NULL,
    acceptance_date       date                  NULL,
    approver_id           BIGINT                NULL,
    handover_status       INT                   NOT NULL,
    CONSTRAINT pk_handovers PRIMARY KEY (id)
);

CREATE TABLE inspections
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    equipment_id      BIGINT                NOT NULL,
    last_time         date                  NOT NULL,
    note              TEXT                  NULL,
    price             DOUBLE                NULL,
    evaluation_status INT                   NULL,
    CONSTRAINT pk_inspections PRIMARY KEY (id)
);

CREATE TABLE inventories
(
    id                  BIGINT AUTO_INCREMENT NOT NULL,
    equipment_id        BIGINT                NOT NULL,
    inventory_date      date                  NOT NULL,
    note                TEXT                  NULL,
    inventory_person_id BIGINT                NOT NULL,
    time                INT                   NOT NULL,
    inventory_status    INT                   NOT NULL,
    CONSTRAINT pk_inventories PRIMARY KEY (id)
);

CREATE TABLE liquidations
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    liquidation_date date                  NOT NULL,
    equipment_id     BIGINT                NOT NULL,
    approver_id      BIGINT                NULL,
    note             TEXT                  NULL,
    price            DOUBLE                NULL,
    CONSTRAINT pk_liquidations PRIMARY KEY (id)
);

CREATE TABLE maintenances
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    equipment_id      BIGINT                NOT NULL,
    last_time         date                  NOT NULL,
    note              TEXT                  NULL,
    price             DOUBLE                NULL,
    evaluation_status INT                   NULL,
    CONSTRAINT pk_maintenances PRIMARY KEY (id)
);

CREATE TABLE projects
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    name           VARCHAR(255)          NOT NULL,
    funding_source VARCHAR(255)          NULL,
    start_date     date                  NULL,
    end_date       date                  NULL,
    created_at     date                  NULL,
    updated_at     date                  NULL,
    CONSTRAINT pk_projects PRIMARY KEY (id)
);

CREATE TABLE provider_provides_services
(
    provider_id BIGINT NOT NULL,
    service_id  BIGINT NOT NULL
);

CREATE TABLE radiation_inspections
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    equipment_id      BIGINT                NOT NULL,
    last_time         date                  NOT NULL,
    note              TEXT                  NULL,
    price             DOUBLE                NULL,
    evaluation_status INT                   NULL,
    CONSTRAINT pk_radiation_inspections PRIMARY KEY (id)
);

CREATE TABLE repairs
(
    id                     BIGINT AUTO_INCREMENT NOT NULL,
    code                   VARCHAR(255)          NULL,
    equipment_id           BIGINT                NOT NULL,
    reason                 TEXT                  NULL,
    reporting_person_id    BIGINT                NOT NULL,
    broken_report_date     date                  NOT NULL,
    repair_priority        INT                   NOT NULL,
    approval_date          date                  NULL,
    approver_id            BIGINT                NULL,
    repair_status          INT                   NOT NULL,
    repair_start_date      date                  NULL,
    repair_completion_date date                  NULL,
    repair_document        TEXT                  NULL,
    repair_cost            DOUBLE                NULL,
    repair_company_id      BIGINT                NULL,
    image                  TEXT                  NULL,
    CONSTRAINT pk_repairs PRIMARY KEY (id)
);

CREATE TABLE services
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)          NOT NULL,
    note TEXT                  NOT NULL,
    CONSTRAINT pk_services PRIMARY KEY (id)
);

CREATE TABLE suppliers
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    name              VARCHAR(255)          NOT NULL,
    address           VARCHAR(255)          NULL,
    hotline           VARCHAR(255)          NOT NULL,
    email             VARCHAR(255)          NOT NULL,
    fax               VARCHAR(255)          NULL,
    website           VARCHAR(255)          NULL,
    tax_code          VARCHAR(255)          NULL,
    contact_person_id BIGINT                NULL,
    note              VARCHAR(255)          NULL,
    CONSTRAINT pk_suppliers PRIMARY KEY (id)
);

CREATE TABLE supplies
(
    id                    BIGINT AUTO_INCREMENT NOT NULL,
    name                  VARCHAR(255)          NOT NULL,
    model                 VARCHAR(255)          NOT NULL,
    serial                VARCHAR(255)          NULL,
    code                  VARCHAR(255)          NULL,
    hash_code             VARCHAR(255)          NULL,
    amount_used           DOUBLE                NOT NULL,
    amount                DOUBLE                NOT NULL,
    warehouse_import_date datetime              NULL,
    year_of_manufacture   INT                   NOT NULL,
    year_in_use           INT                   NOT NULL,
    import_price          DOUBLE                NULL,
    project_id            BIGINT                NULL,
    unit_id               BIGINT                NULL,
    supply_category_id    BIGINT                NULL,
    risk_level            VARCHAR(255)          NULL,
    status                VARCHAR(255)          NOT NULL,
    manufacturer          VARCHAR(255)          NULL,
    manufacturing_country VARCHAR(255)          NULL,
    supplier_id           BIGINT                NULL,
    expiry_date           datetime              NULL,
    technical_parameter   TEXT                  NULL,
    configuration         TEXT                  NULL,
    usage_procedure       TEXT                  NULL,
    note                  TEXT                  NULL,
    create_at             date                  NULL,
    update_at             date                  NULL,
    CONSTRAINT pk_supplies PRIMARY KEY (id)
);

CREATE TABLE supply_categories
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    name              VARCHAR(255)          NOT NULL,
    super_category_id BIGINT                NULL,
    alias             VARCHAR(255)          NULL,
    note              TEXT                  NULL,
    created_at        date                  NULL,
    updated_at        date                  NULL,
    CONSTRAINT pk_supply_categories PRIMARY KEY (id)
);

CREATE TABLE supply_units
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)          NULL,
    CONSTRAINT pk_supply_units PRIMARY KEY (id)
);

CREATE TABLE transfers
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    equipment_id       BIGINT                NOT NULL,
    from_department_id BIGINT                NOT NULL,
    to_department_id   BIGINT                NOT NULL,
    date_transfer      date                  NOT NULL,
    document           TEXT                  NULL,
    note               TEXT                  NULL,
    approver_id        BIGINT                NULL,
    CONSTRAINT pk_transfers PRIMARY KEY (id)
);

CREATE TABLE users
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    name           VARCHAR(255)          NOT NULL,
    username       VARCHAR(255)          NOT NULL,
    email          VARCHAR(255)          NOT NULL,
    phone          VARCHAR(255)          NOT NULL,
    gender         BIT(1)                NOT NULL,
    address        VARCHAR(255)          NULL,
    birthday       date                  NOT NULL,
    enabled        BIT(1)                NOT NULL,
    working_status VARCHAR(255)          NULL,
    role_name      VARCHAR(255)          NULL,
    department_id  BIGINT                NULL,
    created_at     date                  NULL,
    updated_at     date                  NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE equipments
    ADD CONSTRAINT code UNIQUE (code);

ALTER TABLE repairs
    ADD CONSTRAINT code UNIQUE (code);

ALTER TABLE supplies
    ADD CONSTRAINT code UNIQUE (code);

ALTER TABLE departments
    ADD CONSTRAINT email UNIQUE (email);

ALTER TABLE suppliers
    ADD CONSTRAINT email UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT email UNIQUE (email);

ALTER TABLE supplies
    ADD CONSTRAINT hashCode UNIQUE (hash_code);

ALTER TABLE equipments
    ADD CONSTRAINT hash_code UNIQUE (hash_code);

ALTER TABLE suppliers
    ADD CONSTRAINT hotline UNIQUE (hotline);

ALTER TABLE departments
    ADD CONSTRAINT phone UNIQUE (phone);

ALTER TABLE users
    ADD CONSTRAINT phone UNIQUE (phone);

ALTER TABLE equipments
    ADD CONSTRAINT serial UNIQUE (serial);

ALTER TABLE supplies
    ADD CONSTRAINT serial UNIQUE (serial);

ALTER TABLE suppliers
    ADD CONSTRAINT taxCode UNIQUE (tax_code);

ALTER TABLE users
    ADD CONSTRAINT username UNIQUE (username);

ALTER TABLE equipments
    ADD CONSTRAINT CATEGORY FOREIGN KEY (category_id) REFERENCES equipment_categories (id)  ON DELETE SET NULL ON UPDATE SET NULL;

ALTER TABLE departments
    ADD CONSTRAINT CHIEF_NURSE FOREIGN KEY (chief_nurse_id) REFERENCES users (id)  ON DELETE SET NULL ON UPDATE SET NULL;

ALTER TABLE departments
    ADD CONSTRAINT CONTACT_PERSON_ID FOREIGN KEY (contact_person_id) REFERENCES users (id)  ON DELETE SET NULL ON UPDATE SET NULL;

ALTER TABLE users
    ADD CONSTRAINT DEPARTMENT FOREIGN KEY (department_id) REFERENCES departments (id) ON DELETE NO ACTION ON UPDATE SET NULL;

ALTER TABLE clinic_environment_inspections
    ADD CONSTRAINT FK_CLINIC_ENVIRONMENT_INSPECTIONS_ON_EQUIPMENT FOREIGN KEY (equipment_id) REFERENCES equipments (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE cv_radiations
    ADD CONSTRAINT FK_CV_RADIATIONS_ON_EQUIPMENT FOREIGN KEY (equipment_id) REFERENCES equipments (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE equipments
    ADD CONSTRAINT FK_EQUIPMENTS_ON_DEPARTMENT FOREIGN KEY (department_id) REFERENCES departments (id) ON UPDATE CASCADE ON DELETE SET NULL;

ALTER TABLE equipment_supply_usages
    ADD CONSTRAINT FK_EQUIPMENT_SUPPLY_USAGES_ON_EQUIPMENT FOREIGN KEY (equipment_id) REFERENCES equipments (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE equipment_supply_usages
    ADD CONSTRAINT FK_EQUIPMENT_SUPPLY_USAGES_ON_SUPPLY FOREIGN KEY (supply_id) REFERENCES supplies (id) ON UPDATE CASCADE ON DELETE SET NULL;

ALTER TABLE external_quality_assessments
    ADD CONSTRAINT FK_EXTERNAL_QUALITY_ASSESSMENTS_ON_EQUIPMENT FOREIGN KEY (equipment_id) REFERENCES equipments (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE handoverTickets
    ADD CONSTRAINT FK_HANDOVERS_ON_APPROVER FOREIGN KEY (approver_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE SET NULL;

ALTER TABLE handoverTickets
    ADD CONSTRAINT FK_HANDOVERS_ON_DEPARTMENT FOREIGN KEY (department_id) REFERENCES departments (id) ON UPDATE CASCADE ON DELETE NO ACTION;

ALTER TABLE handoverTickets
    ADD CONSTRAINT FK_HANDOVERS_ON_EQUIPMENT FOREIGN KEY (equipment_id) REFERENCES equipments (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE handoverTickets
    ADD CONSTRAINT FK_HANDOVERS_ON_RESPONSIBLE_PERSON FOREIGN KEY (responsible_person_id) REFERENCES users (id) ON DELETE SET NULL ON UPDATE SET NULL;

ALTER TABLE inspections
    ADD CONSTRAINT FK_INSPECTIONS_ON_EQUIPMENT FOREIGN KEY (equipment_id) REFERENCES equipments (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE inventories
    ADD CONSTRAINT FK_INVENTORIES_ON_EQUIPMENT FOREIGN KEY (equipment_id) REFERENCES equipments (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE inventories
    ADD CONSTRAINT FK_INVENTORIES_ON_INVENTORY_PERSON FOREIGN KEY (inventory_person_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE NO ACTION;

ALTER TABLE liquidations
    ADD CONSTRAINT FK_LIQUIDATIONS_ON_APPROVER FOREIGN KEY (approver_id) REFERENCES users (id)  ON DELETE SET NULL ON UPDATE SET NULL;

ALTER TABLE liquidations
    ADD CONSTRAINT FK_LIQUIDATIONS_ON_EQUIPMENT FOREIGN KEY (equipment_id) REFERENCES equipments (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE maintenances
    ADD CONSTRAINT FK_MAINTENANCES_ON_EQUIPMENT FOREIGN KEY (equipment_id) REFERENCES equipments (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE radiation_inspections
    ADD CONSTRAINT FK_RADIATION_INSPECTIONS_ON_EQUIPMENT FOREIGN KEY (equipment_id) REFERENCES equipments (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE repairs
    ADD CONSTRAINT FK_REPAIRS_ON_APPROVER FOREIGN KEY (approver_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE SET NULL;

ALTER TABLE repairs
    ADD CONSTRAINT FK_REPAIRS_ON_EQUIPMENT FOREIGN KEY (equipment_id) REFERENCES equipments (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE repairs
    ADD CONSTRAINT FK_REPAIRS_ON_REPAIR_COMPANY FOREIGN KEY (repair_company_id) REFERENCES suppliers (id) ON UPDATE CASCADE ON DELETE SET NULL;

ALTER TABLE repairs
    ADD CONSTRAINT FK_REPAIRS_ON_REPORTING_PERSON FOREIGN KEY (reporting_person_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE NO ACTION;

ALTER TABLE suppliers
    ADD CONSTRAINT FK_SUPPLIERS_ON_CONTACT_PERSON FOREIGN KEY (contact_person_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE NO ACTION;

ALTER TABLE supplies
    ADD CONSTRAINT FK_SUPPLIES_ON_PROJECT FOREIGN KEY (project_id) REFERENCES projects (id) ON UPDATE CASCADE ON DELETE SET NULL;

ALTER TABLE supplies
    ADD CONSTRAINT FK_SUPPLIES_ON_SUPPLIER FOREIGN KEY (supplier_id) REFERENCES suppliers (id) ON UPDATE CASCADE ON DELETE SET NULL;

ALTER TABLE supplies
    ADD CONSTRAINT FK_SUPPLIES_ON_SUPPLY_CATEGORY FOREIGN KEY (supply_category_id) REFERENCES supply_categories (id) ON UPDATE CASCADE ON DELETE SET NULL;

ALTER TABLE supplies
    ADD CONSTRAINT FK_SUPPLIES_ON_UNIT FOREIGN KEY (unit_id) REFERENCES supply_units (id) ON UPDATE CASCADE ON DELETE SET NULL;

ALTER TABLE supply_categories
    ADD CONSTRAINT FK_SUPPLY_CATEGORIES_ON_SUPER_CATEGORY FOREIGN KEY (super_category_id) REFERENCES supply_categories (id) ON UPDATE CASCADE ON DELETE SET NULL;

ALTER TABLE transfers
    ADD CONSTRAINT FK_TRANSFERS_ON_APPROVER FOREIGN KEY (approver_id) REFERENCES users (id)  ON DELETE SET NULL ON UPDATE SET NULL;

ALTER TABLE transfers
    ADD CONSTRAINT FK_TRANSFERS_ON_EQUIPMENT FOREIGN KEY (equipment_id) REFERENCES equipments (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE transfers
    ADD CONSTRAINT FK_TRANSFERS_ON_FROM_DEPARTMENT FOREIGN KEY (from_department_id) REFERENCES departments (id) ON UPDATE CASCADE ON DELETE NO ACTION;

ALTER TABLE transfers
    ADD CONSTRAINT FK_TRANSFERS_ON_TO_DEPARTMENT FOREIGN KEY (to_department_id) REFERENCES departments (id) ON UPDATE CASCADE ON DELETE NO ACTION;

ALTER TABLE departments
    ADD CONSTRAINT HEAD_OF_DEPARTMENT_ID FOREIGN KEY (head_of_department_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE SET NULL;

ALTER TABLE departments
    ADD CONSTRAINT MANAGER FOREIGN KEY (manager_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE SET NULL;

ALTER TABLE equipments
    ADD CONSTRAINT PROJECT FOREIGN KEY (project_id) REFERENCES projects (id) ON UPDATE CASCADE ON DELETE SET NULL;

ALTER TABLE equipment_categories
    ADD CONSTRAINT SUPER_CATEGORY FOREIGN KEY (super_category_id) REFERENCES equipment_categories (id) ON UPDATE CASCADE ON DELETE SET NULL;

ALTER TABLE equipments
    ADD CONSTRAINT SUPPLIER FOREIGN KEY (supplier_id) REFERENCES suppliers (id) ON UPDATE CASCADE ON DELETE SET NULL;

ALTER TABLE provider_provides_services
    ADD CONSTRAINT fk_proproser_on_service FOREIGN KEY (service_id) REFERENCES services (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE provider_provides_services
    ADD CONSTRAINT fk_proproser_on_supplier FOREIGN KEY (provider_id) REFERENCES suppliers (id) ON UPDATE CASCADE ON DELETE CASCADE;
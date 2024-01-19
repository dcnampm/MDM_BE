CREATE TABLE equipment_units
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)          NULL,
    note TEXT                  NULL,
    CONSTRAINT pk_equipment_units PRIMARY KEY (id)
);

ALTER TABLE equipments
    ADD unit_id BIGINT NULL;

ALTER TABLE equipments
    ADD CONSTRAINT FK_EQUIPMENTS_ON_UNIT FOREIGN KEY (unit_id) REFERENCES equipment_units (id);
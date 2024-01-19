CREATE TABLE equipment_user
(
    equipment_id BIGINT NOT NULL,
    user_id      BIGINT NOT NULL
);

ALTER TABLE equipment_user
    ADD CONSTRAINT fk_equuse_on_equipment FOREIGN KEY (equipment_id) REFERENCES equipments (id) ON DELETE CASCADE;

ALTER TABLE equipment_user
    ADD CONSTRAINT fk_equuse_on_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE;
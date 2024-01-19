ALTER TABLE equipment_user
    DROP FOREIGN KEY fk_equuse_on_equipment;

ALTER TABLE equipment_user
    DROP FOREIGN KEY fk_equuse_on_user;

DROP TABLE equipment_user;
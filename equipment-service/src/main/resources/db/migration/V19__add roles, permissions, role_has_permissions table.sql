CREATE TABLE permissions
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255)          NOT NULL,
    `description` VARCHAR(255)          NULL,
    CONSTRAINT pk_permissions PRIMARY KEY (id)
);

CREATE TABLE role_has_permissions
(
    permission_id BIGINT NOT NULL,
    role_id       BIGINT NOT NULL,
    CONSTRAINT pk_role_has_permissions PRIMARY KEY (permission_id, role_id)
);

CREATE TABLE roles
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255)          NOT NULL,
    `description` VARCHAR(255)          NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

ALTER TABLE users
    ADD role_id BIGINT NULL;

ALTER TABLE permissions
    ADD CONSTRAINT name UNIQUE (name);

ALTER TABLE roles
    ADD CONSTRAINT name UNIQUE (name);

ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_ROLE FOREIGN KEY (role_id) REFERENCES roles (id) ON DELEte set null on update set null ;

ALTER TABLE role_has_permissions
    ADD CONSTRAINT fk_rolhasper_on_permission FOREIGN KEY (permission_id) REFERENCES permissions (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE role_has_permissions
    ADD CONSTRAINT fk_rolhasper_on_role FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE user_read_notifications
    DROP FOREIGN KEY FK_USER_READ_NOTIFICATIONS_ON_NOTIFICATION;

ALTER TABLE user_read_notifications
    DROP FOREIGN KEY FK_USER_READ_NOTIFICATIONS_ON_USER;

DROP TABLE notifications;

DROP TABLE user_read_notifications;
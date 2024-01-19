CREATE TABLE notification_configs
(
    id                            BIGINT AUTO_INCREMENT NOT NULL,
    role_can_receive_notification VARCHAR(255)          NULL,
    event_type                    VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_notification_configs PRIMARY KEY (id)
);

CREATE TABLE notifications
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    event_type VARCHAR(255)          NULL,
    content    JSON                  NULL,
    created_at BIGINT                NULL,
    CONSTRAINT pk_notifications PRIMARY KEY (id)
);

CREATE TABLE user_read_notifications
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    user_id         BIGINT                NULL,
    notification_id BIGINT                NULL,
    is_read         BIT(1)                NOT NULL,
    CONSTRAINT pk_user_read_notifications PRIMARY KEY (id)
);

ALTER TABLE user_read_notifications
    ADD CONSTRAINT FK_USER_READ_NOTIFICATIONS_ON_NOTIFICATION FOREIGN KEY (notification_id) REFERENCES notifications (id) ON DELETE CASCADE;

ALTER TABLE user_read_notifications
    ADD CONSTRAINT FK_USER_READ_NOTIFICATIONS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE;
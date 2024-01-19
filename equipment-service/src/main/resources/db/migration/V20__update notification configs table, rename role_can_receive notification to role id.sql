ALTER TABLE notification_configs
    CHANGE role_can_receive_notification role_id BIGINT;

ALTER TABLE users
    DROP COLUMN role_name;
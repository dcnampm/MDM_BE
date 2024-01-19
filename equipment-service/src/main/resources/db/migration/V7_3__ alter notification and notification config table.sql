ALTER TABLE notification_configs
    CHANGE event_type notification_type VARCHAR(255);

ALTER TABLE notifications
    CHANGE content notification_type VARCHAR(255);

ALTER TABLE notifications
    CHANGE event_type content JSON;

ALTER TABLE notification_configs
    MODIFY role_can_receive_notification VARCHAR(255) NOT NULL;
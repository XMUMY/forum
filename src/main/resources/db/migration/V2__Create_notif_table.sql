CREATE TABLE IF NOT EXISTS forum.notif
(
    id          SERIAL,
    create_time TIMESTAMP NOT NULL,
    uid         TEXT      NOT NULL,
    type        INT       NOT NULL,
    ref_id      INT       NOT NULL,
    obj_id      INT       NOT NULL,
    value       INT       NULL,
    has_read    BOOLEAN   NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON COLUMN forum.notif.type IS 'The type values refer to notif.proto';
COMMENT ON COLUMN forum.notif.ref_id IS 'The referring object of the notification';
COMMENT ON COLUMN forum.notif.ref_id IS 'The object that triggered the notification';
COMMENT ON COLUMN forum.notif.value IS 'The meaning of value depends on notif type';

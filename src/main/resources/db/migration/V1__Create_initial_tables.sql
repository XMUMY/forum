CREATE SCHEMA IF NOT EXISTS forum;

CREATE TABLE IF NOT EXISTS forum.group
(
    id          SERIAL,
    title       TEXT      NULL,
    description TEXT      NULL,
    create_time TIMESTAMP NULL,
    delete_time TIMESTAMP NULL,
    creator_uid TEXT      NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS forum.post
(
    id          SERIAL,
    uid         TEXT      NULL,
    create_time TIMESTAMP NULL,
    update_time TIMESTAMP NULL,
    delete_time TIMESTAMP NULL,
    title       TEXT      NULL,
    body        TEXT      NULL,
    vote        INT       NULL DEFAULT 0,
    group_id    INT       NOT NULL,
    topped      BOOLEAN   NULL DEFAULT FALSE,
    best        BOOLEAN   NULL DEFAULT FALSE,
    PRIMARY KEY (id),
    CONSTRAINT fk_post_group1
        FOREIGN KEY (group_id)
            REFERENCES forum.group (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS forum.reply
(
    id           SERIAL,
    content      TEXT      NULL,
    create_time  TIMESTAMP NULL,
    uid          TEXT      NULL,
    vote         INT       NULL,
    ref_post_id  INT       NOT NULL DEFAULT -1,
    ref_reply_id INT       NOT NULL DEFAULT -1,
    ref_uid      TEXT      NULL,
    topped       BOOLEAN   NULL     DEFAULT FALSE,
    delete_time  TIMESTAMP NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS forum.member
(
    id          SERIAL,
    uid         TEXT      NOT NULL,
    group_id    INT       NOT NULL,
    create_time TIMESTAMP NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_member_group1
        FOREIGN KEY (group_id)
            REFERENCES forum.group (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS forum.moderate
(
    id          SERIAL,
    uid         TEXT      NOT NULL,
    group_id    INT       NOT NULL,
    create_time TIMESTAMP NULL,
    delete_time TIMESTAMP NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_moderate_group1
        FOREIGN KEY (group_id)
            REFERENCES forum.group (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS forum.saved_post
(
    id          SERIAL,
    uid         TEXT      NOT NULL,
    post_id     INT       NOT NULL,
    create_time TIMESTAMP NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_saved_post1
        FOREIGN KEY (post_id)
            REFERENCES forum.post (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS forum.saved_reply
(
    id          SERIAL,
    uid         TEXT      NOT NULL,
    reply_id    INT       NOT NULL,
    create_time TIMESTAMP NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_saved_reply1
        FOREIGN KEY (reply_id)
            REFERENCES forum.reply (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);
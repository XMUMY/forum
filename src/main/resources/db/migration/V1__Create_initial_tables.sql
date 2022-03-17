CREATE SCHEMA IF NOT EXISTS forum;

CREATE TABLE forum."forum" (
                         "id" SERIAL PRIMARY KEY,
                         "title" TEXT,
                         "description" TEXT,
                         "create_at" timestamp,
                         "update_at" timestamp,
                         "creator_uid" TEXT NOT NULL
);

CREATE TABLE forum."member" (
                          "id" SERIAL PRIMARY KEY,
                          "uid" TEXT NOT NULL,
                          "forum_id" INT NOT NULL,
                          "create_at" timestamp
);

CREATE TABLE forum."thread" (
                          "id" SERIAL PRIMARY KEY,
                          "uid" TEXT,
                          "forum_id" INT NOT NULL,
                          "title" TEXT,
                          "body" TEXT,
                          "create_at" timestamp,
                          "update_at" timestamp,
                          "last_update" timestamp,
                          "likes" INT DEFAULT 0,
                          "posts" INT DEFAULT 0,
                          "pinned" BOOLEAN DEFAULT FALSE,
                          "digest" BOOLEAN DEFAULT FALSE
);

CREATE TABLE forum."post" (
                        "id" SERIAL PRIMARY KEY,
                        "uid" TEXT,
                        "forum_id" INT NOT NULL,
                        "thread_id" INT NOT NULL,
                        "parent_id" INT NOT NULL DEFAULT (-1),
                        "ref_post_id" INT NOT NULL DEFAULT (-1),
                        "ref_post_uid" TEXT,
                        "content" TEXT,
                        "create_at" timestamp,
                        "update_at" timestamp,
                        "likes" INT DEFAULT 0,
                        "pinned" BOOLEAN DEFAULT FALSE
);

CREATE TABLE forum."saved_thread" (
                                "id" SERIAL PRIMARY KEY,
                                "uid" TEXT NOT NULL,
                                "thread_id" INT NOT NULL,
                                "create_at" timestamp
);

CREATE TABLE forum."saved_post" (
                              "id" SERIAL PRIMARY KEY,
                              "uid" TEXT NOT NULL,
                              "post_id" INT NOT NULL,
                              "create_at" timestamp
);

CREATE TABLE forum."liked_thread" (
                                "id" SERIAL PRIMARY KEY,
                                "uid" TEXT NOT NULL,
                                "thread_id" INT NOT NULL,
                                "liked" BOOLEAN DEFAULT TRUE,
                                "create_at" timestamp
);

CREATE TABLE forum."liked_post" (
                              "id" SERIAL PRIMARY KEY,
                              "uid" TEXT NOT NULL,
                              "post_id" INT NOT NULL,
                              "liked" BOOLEAN DEFAULT TRUE,
                              "create_at" timestamp
);

CREATE TABLE forum."notif" (
                         "id" SERIAL PRIMARY KEY,
                         "create_at" timestamp NOT NULL,
                         "uid" TEXT NOT NULL,
                         "sender_uid" TEXT NOT NULL,
                         "has_read" BOOLEAN NOT NULL,
                         "type" INT NOT NULL,
                         "ref_id" INT NOT NULL,
                         "obj_id" INT NOT NULL,
                         "value" INT
);

ALTER TABLE forum."thread" ADD FOREIGN KEY ("forum_id") REFERENCES forum."forum" ("id");

ALTER TABLE forum."post" ADD FOREIGN KEY ("thread_id") REFERENCES forum."thread" ("id")
    ON DELETE CASCADE;

ALTER TABLE forum."member" ADD FOREIGN KEY ("forum_id") REFERENCES forum."forum" ("id");

ALTER TABLE forum."saved_thread" ADD FOREIGN KEY ("thread_id") REFERENCES forum."thread" ("id")
    ON DELETE CASCADE;

ALTER TABLE forum."saved_post" ADD FOREIGN KEY ("post_id") REFERENCES forum."post" ("id")
    ON DELETE CASCADE;

ALTER TABLE forum."liked_thread" ADD FOREIGN KEY ("thread_id") REFERENCES forum."thread" ("id")
    ON DELETE CASCADE;

ALTER TABLE forum."liked_post" ADD FOREIGN KEY ("post_id") REFERENCES forum."post" ("id")
    ON DELETE CASCADE;

COMMENT ON COLUMN forum."notif".sender_uid IS 'The uid of notification sender';
COMMENT ON COLUMN forum."notif".type IS 'The type values refer to notif.proto';
COMMENT ON COLUMN forum."notif".ref_id IS 'The referring object of the notification';
COMMENT ON COLUMN forum."notif".obj_id IS 'The object that triggered the notification';
COMMENT ON COLUMN forum."notif".value IS 'The meaning of value depends on notif type';
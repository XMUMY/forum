CREATE SCHEMA IF NOT EXISTS forum;
CREATE TABLE forum."forum" (
    "id" SERIAL PRIMARY KEY,
    "title" TEXT NOT NULL,
    "description" TEXT,
    "create_at" timestamp NOT NULL,
    "update_at" timestamp,
    "creator_uid" TEXT NOT NULL
);
CREATE TABLE forum."member" (
    "id" SERIAL PRIMARY KEY,
    "uid" TEXT NOT NULL,
    "forum_id" INT NOT NULL,
    "create_at" timestamp NOT NULL
);
CREATE TABLE forum."thread" (
    "id" SERIAL PRIMARY KEY,
    "uid" TEXT NOT NULL,
    "forum_id" INT NOT NULL,
    "title" TEXT NOT NULL,
    "body" TEXT,
    "create_at" timestamp NOT NULL,
    "update_at" timestamp,
    "last_update" timestamp,
    "likes" INT NOT NULL DEFAULT 0,
    "posts" INT NOT NULL DEFAULT 0,
    "pinned" BOOLEAN NOT NULL DEFAULT FALSE,
    "digest" BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE TABLE forum."post" (
    "id" SERIAL PRIMARY KEY,
    "uid" TEXT NOT NULL,
    "forum_id" INT NOT NULL,
    "thread_id" INT NOT NULL,
    "parent_id" INT NOT NULL DEFAULT 0,
    "ref_post_id" INT NOT NULL DEFAULT 0,
    "ref_post_uid" TEXT,
    "content" TEXT,
    "create_at" timestamp NOT NULL,
    "update_at" timestamp,
    "likes" INT NOT NULL DEFAULT 0,
    "pinned" BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE TABLE forum."saved_thread" (
    "id" SERIAL PRIMARY KEY,
    "uid" TEXT NOT NULL,
    "thread_id" INT NOT NULL,
    "create_at" timestamp NOT NULL
);
CREATE TABLE forum."saved_post" (
    "id" SERIAL PRIMARY KEY,
    "uid" TEXT NOT NULL,
    "post_id" INT NOT NULL,
    "create_at" timestamp NOT NULL
);
CREATE TABLE forum."liked_thread" (
    "id" SERIAL PRIMARY KEY,
    "uid" TEXT NOT NULL,
    "thread_id" INT NOT NULL,
    "liked" BOOLEAN NOT NULL DEFAULT TRUE,
    "create_at" timestamp NOT NULL
);
CREATE TABLE forum."liked_post" (
    "id" SERIAL PRIMARY KEY,
    "uid" TEXT NOT NULL,
    "post_id" INT NOT NULL,
    "liked" BOOLEAN NOT NULL DEFAULT TRUE,
    "create_at" timestamp NOT NULL
);
CREATE TABLE forum."notif" (
    "id" SERIAL PRIMARY KEY,
    "uid" TEXT NOT NULL,
    "sender_uid" TEXT NOT NULL,
    "create_at" timestamp NOT NULL,
    "has_read" BOOLEAN NOT NULL,
    "type" INT NOT NULL
);
ALTER TABLE forum."thread"
ADD FOREIGN KEY ("forum_id") REFERENCES forum."forum" ("id");
ALTER TABLE forum."post"
ADD FOREIGN KEY ("thread_id") REFERENCES forum."thread" ("id") ON DELETE CASCADE;
ALTER TABLE forum."member"
ADD FOREIGN KEY ("forum_id") REFERENCES forum."forum" ("id");
ALTER TABLE forum."saved_thread"
ADD FOREIGN KEY ("thread_id") REFERENCES forum."thread" ("id") ON DELETE CASCADE;
ALTER TABLE forum."saved_post"
ADD FOREIGN KEY ("post_id") REFERENCES forum."post" ("id") ON DELETE CASCADE;
ALTER TABLE forum."liked_thread"
ADD FOREIGN KEY ("thread_id") REFERENCES forum."thread" ("id") ON DELETE CASCADE;
ALTER TABLE forum."liked_post"
ADD FOREIGN KEY ("post_id") REFERENCES forum."post" ("id") ON DELETE CASCADE;
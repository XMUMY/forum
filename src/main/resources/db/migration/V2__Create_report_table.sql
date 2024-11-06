CREATE TABLE IF NOT EXISTS forum."report" (
    "id" SERIAL PRIMARY KEY,
    "uid" TEXT NOT NULL,
    "type_id" INT NOT NULL,
    "target_id" INT NOT NULL,
    "target_type" INT NOT NULL,
    "reason" TEXT NOT NULL,
    "created_at" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN forum."report"."target_type" IS '0: thread, 1: post';

CREATE INDEX idx_report_user_target ON forum."report" ("uid", "target_id", "target_type");

CREATE TABLE IF NOT EXISTS forum."report_type" (
    "id" INT PRIMARY KEY,
    "name" TEXT NOT NULL
);

INSERT INTO forum."report_type" ("id", "name") VALUES (0, 'spam');
INSERT INTO forum."report_type" ("id", "name") VALUES (1, 'violence');
INSERT INTO forum."report_type" ("id", "name") VALUES (2, 'sexual');
INSERT INTO forum."report_type" ("id", "name") VALUES (3, 'false_information');
INSERT INTO forum."report_type" ("id", "name") VALUES (4, 'politics');
INSERT INTO forum."report_type" ("id", "name") VALUES (5, 'cyber_bullying');
INSERT INTO forum."report_type" ("id", "name") VALUES (6, 'other');

CREATE TABLE IF NOT EXISTS forum."censored_content" (
    "id" SERIAL PRIMARY KEY,
    "uid" TEXT NOT NULL,
    "type_id" INT NOT NULL,
    "content_type" INT NOT NULL,
    "content" TEXT NOT NULL,
    "reason" TEXT NOT NULL,
    "created_at" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN forum."censored_content"."type_id" IS '0: thread, 1: post';
COMMENT ON COLUMN forum."censored_content"."content_type" IS '0: text, 1: image';

ALTER TABLE forum."notif" ALTER COLUMN "create_at" SET DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE forum."notif" ADD COLUMN "read_at" timestamp;
ALTER TABLE forum."notif" ALTER COLUMN "has_read" SET DEFAULT FALSE;

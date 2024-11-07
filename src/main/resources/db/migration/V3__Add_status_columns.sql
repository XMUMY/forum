ALTER TABLE forum."thread"
ADD COLUMN "status" INT NOT NULL DEFAULT 0;

COMMENT ON COLUMN forum."thread"."status" IS '0: normal, 1: deleted';

ALTER TABLE forum."post"
ADD COLUMN "status" INT NOT NULL DEFAULT 0;

COMMENT ON COLUMN forum."post"."status" IS '0: normal, 1: deleted';

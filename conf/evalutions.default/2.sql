# --- !Ups
CREATE TABLE "Language"(
    "id" SERIAL NOT NULL PRIMARY KEY,
    "name" VARCHAR NOT NULL,
    "photo" VARCHAR NULL
);
ALTER TABLE "Language" RENAME "photo" TO "logo";

# --- !Downs
DROP TABLE "Language";
ALTER TABLE "Language" RENAME "logo" TO "photo";

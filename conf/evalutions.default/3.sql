# --- !Ups
CREATE TABLE "Direction"(
    "id" SERIAL NOT NULL PRIMARY KEY,
    "name" VARCHAR NOT NULL
);
# --- !Downs
DROP TABLE "Direction";
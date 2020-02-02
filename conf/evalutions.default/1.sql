# --- !Ups
CREATE TABLE "User"(
    "id" SERIAL NOT NULL PRIMARY KEY,
    "first_name" VARCHAR NOT NULL,
    "last_name" VARCHAR NOT NULL,
    "login" VARCHAR NOT NULL,
    "password" VARCHAR NOT NULL,
    "created_date" DATE NOT NULL,
    "photo" VARCHAR NULL,
    "permission_id" INT CONSTRAINT  "UserFkPermissionsId" REFERENCES  "Permissions" ("id") ON UPDATE CASCADE ON DELETE CASCADE
);

# --- !Downs
DROP TABLE "User";
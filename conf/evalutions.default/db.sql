# --- !Ups
CREATE TABLE "Language"
(
    "id"   SERIAL  NOT NULL PRIMARY KEY,
    "name" VARCHAR NOT NULL,
    "logo" VARCHAR NULL
);
CREATE TABLE "Direction"
(
    "id"   SERIAL  NOT NULL PRIMARY KEY,
    "name" VARCHAR NOT NULL
);

CREATE TABLE "Role"
(
    "id"   SERIAL  NOT NULL PRIMARY KEY,
    "name" VARCHAR NOT NULL
);
CREATE TABLE "Permissions"
(
    "id"      SERIAL NOT NULL PRIMARY KEY,
    "role_id" INT
        CONSTRAINT PermissionsFkRoleId REFERENCES "Role" ("id") ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE "User"
(
    "id"            SERIAL  NOT NULL PRIMARY KEY,
    "first_name"    VARCHAR NOT NULL,
    "last_name"     VARCHAR NOT NULL,
    "login"         VARCHAR NOT NULL,
    "password"      VARCHAR NOT NULL,
    "created_date"  DATE    NOT NULL,
    "photo"         VARCHAR NULL,
    "permission_id" INT
        CONSTRAINT "UserFkPermissionsId" REFERENCES "Permissions" ("id") ON UPDATE CASCADE ON DELETE CASCADE
);
# --- !Downs
DROP TABLE "User";
DROP TABLE "Permissions";
DROP TABLE "Role";
DROP TABLE "Language";
DROP TABLE "Direction";


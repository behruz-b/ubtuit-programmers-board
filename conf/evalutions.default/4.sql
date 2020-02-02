# --- !Ups
CREATE TABLE "Permissions"(
    "id" SERIAL NOT NULL PRIMARY KEY,
    "role_id" INT CONSTRAINT  PermissionsFkRoleId REFERENCES  "Role" ("id") ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE "Role"(
    "id" SERIAL NOT NULL PRIMARY KEY,
    "name" VARCHAR NOT NULL
);

# --- !Downs
DROP TABLE "Permissions";
DROP TABLE "Role";
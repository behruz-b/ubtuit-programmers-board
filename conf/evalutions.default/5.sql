# --- !Ups

INSERT INTO "Role" ("name")
       VALUES ('User');
INSERT INTO "Role" ("name")
       VALUES ('Create Leader');
INSERT INTO "Role" ("name")
       VALUES ('Update Leader');
INSERT INTO "Role" ("name")
       VALUES ('Delete Leader');
INSERT INTO "Role" ("name")
       VALUES ('Show Leader');
INSERT INTO "Role" ("name")
       VALUES ('Create Language');
INSERT INTO "Role" ("name")
       VALUES ('Update Language');
INSERT INTO "Role" ("name")
       VALUES ('Delete Language');
INSERT INTO "Role" ("name")
       VALUES ('Create Direction');
INSERT INTO "Role" ("name")
       VALUES ('Update Direction');
INSERT INTO "Role" ("name")
       VALUES ('Delete Direction');
INSERT INTO "Role" ("name")
       VALUES ('Message');
INSERT INTO "Role" ("name")
       VALUES ('Create User');
INSERT INTO "Role" ("name")
       VALUES ('Delete User');
INSERT INTO "Role" ("name")
       VALUES ('Update User');

# --- !Downs

DELETE FROM "Role" WHERE name = 'User';
DELETE FROM "Role" WHERE name = 'Create Leader';
DELETE FROM "Role" WHERE name = 'Update Leader';
DELETE FROM "Role" WHERE name = 'Delete Leader';
DELETE FROM "Role" WHERE name = 'Show Leader';
DELETE FROM "Role" WHERE name = 'Create Language';
DELETE FROM "Role" WHERE name = 'Update Language';
DELETE FROM "Role" WHERE name = 'Delete Language';
DELETE FROM "Role" WHERE name = 'Create Direction';
DELETE FROM "Role" WHERE name = 'Update Direction';
DELETE FROM "Role" WHERE name = 'Delete Direction';
DELETE FROM "Role" WHERE name = 'Message';
DELETE FROM "Role" WHERE name = 'Create User';
DELETE FROM "Role" WHERE name = 'Delete User';
DELETE FROM "Role" WHERE name = 'Update User';
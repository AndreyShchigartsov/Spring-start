--liquibase formatted sql

--changeset andrey:1
ALTER TABLE users
ADD COLUMN image VARCHAR(64)

--changeset andrey:2
ALTER TABLE users_aud
ADD COLUMN image VARCHAR(64)
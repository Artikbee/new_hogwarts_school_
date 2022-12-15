-- liquibase formatted sql

-- changeset usov:1
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT count(*) FROM information_schema.tables WHERE table_schema = 'public' AND table_name = 'faculty'
CREATE TABLE faculty
(
    id    SERIAL PRIMARY KEY,
    name  varchar(255),
    color varchar(255)
);

-- changeset usov:2
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT count(*) FROM information_schema.tables WHERE table_schema = 'public' AND table_name = 'avatar'
CREATE TABLE avatar
(
    id         SERIAL PRIMARY KEY,
    file_path  VARCHAR(255),
    file_size  BIGINT NOT NULL,
    media_type VARCHAR(255),
    data       BYTEA,
    student_id BIGINT
);

-- changeset usov:3
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 SELECT count(*) FROM information_schema.tables WHERE table_schema = 'public' AND table_name = 'student'
CREATE TABLE student
(
    id         SERIAL PRIMARY KEY,
    name       varchar(255)       NOT NULL,
    age        INTEGER DEFAULT 20 NOT NULL,
    faculty_id BIGINT REFERENCES faculty (id),
    avatar_id  BIGINT REFERENCES avatar (id)
);

-- changeset usov:4
CREATE INDEX student_name_find ON student (name);

-- changeset usov:5
CREATE INDEX faculty_name_or_color_find ON faculty (name, color);
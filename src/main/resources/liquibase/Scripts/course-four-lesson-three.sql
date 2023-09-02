-- liquibase formatted sql

-- changeset esheffer:1
CREATE INDEX student_index ON student (name);
CREATE INDEX name_or_color_index ON faculty (name, color);

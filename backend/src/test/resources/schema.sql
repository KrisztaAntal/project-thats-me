CREATE TYPE Role AS ENUM('ROLE_USER', 'ROLE_ADMIN');

DROP TABLE IF EXISTS monogram CASCADE;
CREATE TABLE monogram
(
    monogram_id        bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    characters         varchar(255),
    color_code         varchar(255),
    monogram_public_id uuid
);

DROP TABLE IF EXISTS member_role CASCADE;
CREATE TABLE member_role
(
    role_id bigint,
    role    Role
);


DROP TABLE IF EXISTS members_roles CASCADE;
CREATE TABLE members_roles
(
    member_id bigint,
    role_id   bigint
);

DROP TABLE IF EXISTS member CASCADE;
CREATE TABLE member
(
    member_id        bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    avatar           varchar(255),
    banner_image     varchar(255),
    biography        varchar(255),
    birth_date       date,
    date_of_registry date,
    email            varchar(255),
    first_name       varchar(255),
    last_name        varchar(255),
    member_public_id uuid,
    password         varchar(255),
    username         varchar(255),
    monogram_id      bigint UNIQUE,
    CONSTRAINT fk_monogram FOREIGN KEY (monogram_id) REFERENCES monogram (monogram_id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS expertise CASCADE;
CREATE TABLE expertise
(
    expertise_id        bigint,
    expertise_public_id uuid,
    name                varchar(255)
);

DROP TABLE IF EXISTS member_expertises CASCADE;
CREATE TABLE member_expertises
(
    member_member_id        bigint,
    expertises_expertise_id bigint
);

DROP TABLE IF EXISTS past_job CASCADE;
CREATE TABLE past_job
(
    job_id        bigint,
    job_public_id uuid,
    company_name  varchar(255),
    description   varchar(255),
    job_title     varchar(255),
    end_date      date,
    start_date    date
);

DROP TABLE IF EXISTS member_past_jobs CASCADE;
CREATE TABLE member_past_jobs
(
    member_member_id bigint,
    past_jobs_job_id bigint
);

DROP TABLE IF EXISTS project_of_member CASCADE;
CREATE TABLE project_of_member
(
    project_id          bigint,
    project_public_id   uuid,
    project_title       varchar(255),
    project_description varchar(255)
);

DROP TABLE IF EXISTS member_projects_of_member CASCADE;
CREATE TABLE member_projects_of_member
(
    member_member_id              bigint,
    projects_of_member_project_id bigint
);


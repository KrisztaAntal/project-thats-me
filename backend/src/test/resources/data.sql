INSERT INTO member_role (role_id, role)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN');

INSERT INTO members_roles(member_id, role_id)
VALUES (1, 1);

INSERT INTO monogram (characters, color_code, monogram_public_id)
VALUES ('KA', '#000000', 'cbde557a-8129-463d-8e29-81f126b0cebd');

INSERT INTO member (avatar, banner_image, biography, birth_date, date_of_registry, email, first_name,
                    last_name, member_public_id, password, username, monogram_id)
VALUES (null, null, null, '2004-12-10', '2025-01-23', 'Kriszta@gmail.com', 'Kriszta', 'Antal',
        'c5e8f43f-5dd4-4b96-a522-56e757693867', '$2a$10$zCRjgNcjcKkUcrajpTcyw.GnN81Ma/cZDMBB997POK6BnrYmSLqfS',
        'ankriszt', (SELECT MAX(monogram_id) FROM monogram));
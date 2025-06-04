INSERT INTO categories(id, category_name, sub_category_name)
VALUES ('CASE00001', 'Hardware', 'Muis'),
       ('CASE00002', 'Software', 'Licentie'),
       ('CASE00003', 'Netwerk', 'Internet');

INSERT INTO details(id, description, title, type)
VALUES ('CASE00001', 'Mijn muis kan ik niet verbinden met de laptop (Dell)', 'Geen verbinding', 'MALFUNCTION'),
       ('CASE00002', 'De licenties van ons Office pakket is bijna verlopen', 'Licentie verlopen', 'QUESTION'),
       ('CASE00003', 'Het hele bedrijf heeft soms last van trage internet', 'Internet traag', 'COMPLAINT');

INSERT INTO users(username, password, role, email)
VALUES ('Test_Manager', '$2a$10$oh2jnssxP9pT/TcHPfhXb.au5/Fn1.7/AO.A1dRKA1jhunmuybd0.', 'MANAGER','test_manager@novi.nl'),
       ('Test_Agent', '$2a$10$.k.Ug5Pf7CGRf/QIw5zuy.BYCH17d5R.IlxHxS1r5SeZXgD6ptKRW', 'AGENT', 'test_agent@novi.nl'),
       ('Test_Client', '$2a$10$wMMChXMeYRqPSwSP/4Nns.CrFArfWhaBfswig.ljtEjbSvnd45gn6', 'CLIENT', 'test_client@novi.nl');

INSERT INTO fixes(id, solution, feedback, status)
VALUES ('CASE00001', 'Maak opnieuw verbinding via bluetooth.', 'Uitstekende oplossing!', 'IN_PROGRESS'),
       ('CASE00002', 'De afdeling inkoop is bezig met een verlenging', 'Wij kijken er naar uit!', 'IN_PROGRESS'),
       ('CASE00003', 'Er loopt een case bij onze ISP (KPN)', 'Trage afhandeling', 'REJECTED');

INSERT INTO tickets(ID, CATEGORY_ID, CLOSED_AT, CREATED_AT, DETAIL_ID, FIX_ID, PRIORITY, USER_ID)
VALUES ('CASE00001', 'CASE00001', null, current_timestamp, 'CASE00001', 'CASE00001', 'P4_INDIVIDUAL', 'Test_Manager'),
       ('CASE00002','CASE00002', null, current_timestamp, 'CASE00002', 'CASE00002', 'P2_DEPARTEMENT', 'Test_Agent'),
       ('CASE00003','CASE00003', null, current_timestamp, 'CASE00003', 'CASE00003', 'P1_ORGANIZATION', 'Test_Client');



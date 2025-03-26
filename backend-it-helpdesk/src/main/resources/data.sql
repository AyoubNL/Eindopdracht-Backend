INSERT INTO users (username, password, role, email)
VALUES ('Test01', '$2a$10$oh2jnssxP9pT/TcHPfhXb.au5/Fn1.7/AO.A1dRKA1jhunmuybd0.', 'MANAGER', 'test01@novi.nl'),
       ('Test02', '$2a$10$.k.Ug5Pf7CGRf/QIw5zuy.BYCH17d5R.IlxHxS1r5SeZXgD6ptKRW', 'AGENT', 'test02@novi.nl'),
       ('Test03', '$2a$10$wMMChXMeYRqPSwSP/4Nns.CrFArfWhaBfswig.ljtEjbSvnd45gn6', 'CLIENT', 'test03@novi.nl');

INSERT INTO tickets(CATEGORY_ID, CLOSED_AT, CREATED_AT, DETAIL_ID, FIX_ID, PRIORITY, USER_ID)
VALUES (1,'',localtime, 1,1);

INSERT INTO categories(id, category_name, sub_category_name)
VALUES (1, 'Hardware', 'Mouse');

INSERT INTO details(id, description, title, type)
VALUES (1, 'Mijn muis kan ik niet verbinden met de laptop (Dell)', 'Geen verbinding', 'MALFUNCTION')
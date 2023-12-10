INSERT INTO zanr (naziv) VALUES ('komedija');
INSERT INTO zanr (naziv) VALUES ('drama');

INSERT INTO igralec (ime, priimek) VALUES ('Eddie', 'Murphy');
INSERT INTO igralec (ime, priimek) VALUES ('Cameron', 'Diaz');
INSERT INTO igralec (ime, priimek) VALUES ('Chris', 'Miller');

INSERT INTO film_igralec (film_id, igralec_id) VALUES (1, 1);
INSERT INTO film_igralec (film_id, igralec_id) VALUES (1, 2);
INSERT INTO film_igralec (film_id, igralec_id) VALUES (1, 3);
INSERT INTO film_igralec (film_id, igralec_id) VALUES (2, 1);
INSERT INTO film_igralec (film_id, igralec_id) VALUES (2, 2);

INSERT INTO film (naslov, opis, leto_izzida, zanr_id, ocena) VALUES ('Shrek', 'Opis filma Shrek.', 2001, 1, 8);
INSERT INTO film (naslov, opis, leto_izzida, zanr_id, ocena) VALUES ('Shrek 2', 'Opis filma Shrek 2.', 2004, 1, 7);

INSERT INTO uporabnik (uporabnisko_ime, geslo, email, spol, starost) VALUES ('petrakos', 'geslo123', 'petra.kos@hotmail.com', 'Z', 45);
INSERT INTO uporabnik (uporabnisko_ime, geslo, email, spol, starost) VALUES ('mihakovac', 'geslo456', 'miha.kovac@gmail.com', 'M', 23);

INSERT INTO ocena (uporabnik_id, film_id, ocena, komentar, cas_objave) VALUES (1, 1, 10, 'Najboljsi film, kar sem ga kdajkoli gledal', '2023-10-10 19:08:57');
INSERT INTO ocena (uporabnik_id, film_id, ocena, komentar, cas_objave) VALUES (1, 2, 8, 'Film je bil slabši od prvega, vendar še vseeno zelo smešen.', '2023-10-18 11:10:57');

INSERT INTO uporabnik_zanr (uporabnik_id, zanr_id) VALUES (1, 1);

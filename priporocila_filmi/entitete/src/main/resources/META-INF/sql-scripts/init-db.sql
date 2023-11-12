INSERT INTO uporabnik (uporabnisko_ime, email, spol, starost) VALUES ('petrakos', 'petra.kos@hotmail.com', Z, 45);
INSERT INTO uporabnik (uporabnisko_ime, email, spol, starost) VALUES ('mihakovac', 'miha.kovac@gmail.com', M, 23);

INSERT INTO zanr (naziv) VALUES ('komedija');
INSERT INTO zanr (naziv) VALUES ('drama');

INSERT INTO film (naslov, opis, ocena, zanr_id, zasedba, ocene) VALUES ('Shrek', 'Opis filma Shrek.', 8, 1, [1, 2, 3], [1]);
INSERT INTO film (naslov, opis, ocena, zanr_id, zasedba, ocene) VALUES ('Shrek 2', 'Opis filma Shrek 2.', 7, 1, [1, 2], [2]);

INSERT INTO igralec (ime, priimek) VALUES ('Eddie', 'Murphy');
INSERT INTO igralec (ime, priimek) VALUES ('Cameron', 'Diaz');
INSERT INTO igralec (ime, priimek) VALUES ('Chris', 'Miller');

INSERT INTO ocena (uporabnik_id, film_id, ocena, komentar, cas_objave) VALUES (2, 1, 10, 'Najboljsi film, kar sem ga kdajkoli gledal', 2023-10-10-19-08-57-11);
INSERT INTO ocena (uporabnik_id, film_id, ocena, komentar, cas_objave) VALUES (2, 2, 8, 'Film je bil slabši od prvega, vendar še vseeno zelo smešen.', 2023-10-18-11-10-57-41);

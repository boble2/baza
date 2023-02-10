DROP TABLE IF EXISTS Reprezentacja CASCADE;


CREATE TABLE Reprezentacja (
	id_rep SERIAL PRIMARY KEY,
	nazwa VARCHAR(40) UNIQUE NOT NULL
);


DROP TABLE IF EXISTS Konkurs CASCADE;

CREATE TABLE Konkurs (
	id_kon SERIAL PRIMARY KEY,
	data DATE NOT NULL,
	termin_zgloszen DATE NOT NULL,
	id_org INTEGER NOT NULL REFERENCES Reprezentacja,
	miejsce VARCHAR(60) NOT NULL,
	status INTEGER NOT NULL DEFAULT 1 
	/*1 - nowy, 2 - w trakcie, 3 - zakonczony */
);


DROP TABLE IF EXISTS REPREZENTACJA_KONKURS CASCADE;

CREATE TABLE REPREZENTACJA_KONKURS (
	id_rep INTEGER REFERENCES Reprezentacja,
	id_kon INTEGER REFERENCES Konkurs,
	kwota_bazowa INTEGER NOT NULL,
	PRIMARY KEY(id_rep, id_kon)
);


DROP TABLE IF EXISTS Seria CASCADE;

CREATE TABLE Seria (
	id_serii SERIAL PRIMARY KEY,
	id_kon INTEGER NOT NULL REFERENCES Konkurs,
	numer INTEGER NOT NULL
);


DROP TABLE IF EXISTS Zawodnik CASCADE;

CREATE TABLE Zawodnik (
	id_zaw SERIAL PRIMARY KEY,
	id_rep INTEGER NOT NULL REFERENCES Reprezentacja,
	imie VARCHAR(20) NOT NULL,
	nazwisko VARCHAR(40) NOT NULL
);


DROP TABLE IF EXISTS Zgloszenie CASCADE;

CREATE TABLE Zgloszenie (
	id_zgl SERIAL PRIMARY KEY,
	id_zaw INTEGER NOT NULL REFERENCES Zawodnik,
	id_kon INTEGER NOT NULL REFERENCES Konkurs,
	wynik_zaw DECIMAL,
	miejsce_zaw INTEGER,
	UNIQUE(id_zaw, id_kon)
);

DROP TABLE IF EXISTS Wynik CASCADE;

CREATE TABLE Wynik (
	id_wyn SERIAL PRIMARY KEY,
	id_serii INTEGER REFERENCES Seria,
	id_zgl INTEGER REFERENCES Zgloszenie,
	ocena_zb DECIMAL NOT NULL,
	dlugosc DECIMAL NOT NULL,
	czy_dsq BOOLEAN NOT NULL,
	punkty DECIMAL NOT NULL
);

CREATE OR REPLACE FUNCTION f() RETURNS TRIGGER AS $$
DECLARE
	curr RECORD;
BEGIN
	FOR curr IN (SELECT COUNT(*) AS cnt, q.nazwa, q.kwota_bazowa FROM
        (((Zgloszenie k NATURAL JOIN Zawodnik l) m
        NATURAL JOIN Reprezentacja n) o NATURAL JOIN REPREZENTACJA_KONKURS p) q
        GROUP BY q.id_kon, q.nazwa, q.kwota_bazowa)
	LOOP
		IF curr.cnt > curr.kwota_bazowa THEN
			RAISE EXCEPTION
			'Liczba zgłoszeń zawodników danej reprezentacji przekracza jej kwotę bazową!';
		END IF;
	END LOOP;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER t AFTER INSERT OR UPDATE OR DELETE ON Zgloszenie
EXECUTE PROCEDURE f();

CREATE OR REPLACE FUNCTION rozgrywana_jest_kwalifikacyjna(_id_kon INTEGER) RETURNS BOOLEAN AS $$
DECLARE
	sum INTEGER;
BEGIN
	SELECT n.cnt FROM (SELECT COUNT(*) AS cnt, id_kon FROM Zgloszenie
	GROUP BY id_kon) n WHERE n.id_kon = _id_kon
	INTO sum;
	IF sum IS NULL THEN RETURN FALSE;
	END IF;
	RETURN sum > 50;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION czy_jest_kwalifikacyjna(_id_kon INTEGER) RETURNS BOOLEAN AS $$
BEGIN
	RETURN (SELECT COUNT(*) FROM seria WHERE id_kon = _id_kon AND numer = 0) > 0;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION czy_pierwsza_seria(_id_kon INTEGER, _numer INTEGER) RETURNS BOOLEAN AS $$
BEGIN
	RETURN ((_numer = 0 AND czy_jest_kwalifikacyjna(_id_kon)) OR ((NOT czy_jest_kwalifikacyjna(_id_kon)) AND _numer = 1));
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION prog_do_serii(_id_kon INTEGER, _numer INTEGER) RETURNS DECIMAL AS $$
BEGIN
	IF _numer = 1 THEN
		RETURN wynik_ntego(_id_kon, _numer-1, 50);
	ELSE
		RETURN wynik_ntego(_id_kon, _numer-1, 30);
	END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION ile_uczestnikow_serii(_id_kon INTEGER, _numer INTEGER) RETURNS INTEGER AS $$
DECLARE 
	_id_serii INTEGER;
	wyn INTEGER;
BEGIN
	SELECT id_serii FROM seria WHERE _id_kon = id_kon AND _numer = numer INTO _id_serii;
	IF czy_pierwsza_seria(_id_kon, _numer) THEN
		SELECT COUNT(*) FROM zgloszenie WHERE id_kon = _id_kon INTO wyn;
	ELSE 
		SELECT COUNT(*) FROM wynik NATURAL JOIN seria WHERE 
		punkty >= prog_do_serii(_id_kon, _numer) AND
		id_kon = _id_kon AND numer + 1 = _numer INTO wyn;
	END IF;
	RETURN wyn;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION ile_wynikow_serii(_id_kon INTEGER, n INTEGER) RETURNS INTEGER AS $$
BEGIN
	RETURN (SELECT COUNT(*) FROM wynik NATURAL JOIN seria where id_kon = _id_kon AND numer = n);
END;
$$ LANGUAGE plpgsql;
		
		
CREATE OR REPLACE FUNCTION aktualna_seria(_id_kon INTEGER) RETURNS INTEGER AS $$
DECLARE
	wyn INTEGER;
BEGIN
	CASE WHEN ile_wynikow_serii(_id_kon, 0) < ile_uczestnikow_serii(_id_kon, 0) THEN
		RETURN 0;
	WHEN ile_wynikow_serii(_id_kon, 1) < ile_uczestnikow_serii(_id_kon, 1) THEN
		RETURN 1;
	WHEN ile_wynikow_serii(_id_kon, 2) < ile_uczestnikow_serii(_id_kon, 2) THEN
		RETURN 2;
	ELSE
		RETURN null;
	END CASE;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION aktualna_seria_id(_id_kon INTEGER) RETURNS INTEGER AS $$
DECLARE
	wyn INTEGER;
BEGIN
	SELECT id_serii FROM seria WHERE id_kon = _id_kon AND numer = aktualna_seria(_id_kon) INTO wyn;
	RETURN wyn;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION wynik_ntego(_id_kon INTEGER, _numer INTEGER , n INTEGER) RETURNS DECIMAL AS $$
DECLARE
	wyn DECIMAL;
BEGIN
	SELECT MIN(punkty) FROM 
	(SELECT punkty  FROM wynik NATURAL JOIN seria 
	WHERE _id_kon = id_kon AND _numer = numer 
	ORDER BY punkty desc LIMIT n) as foo
	INTO wyn;
	RETURN wyn;
END;
$$ LANGUAGE plpgsql;
	





CREATE OR REPLACE FUNCTION nastepny(_id_kon INTEGER, _numer INTEGER) RETURNS INTEGER AS $$
DECLARE 
	wyn INTEGER;
BEGIN
	IF czy_pierwsza_seria(_id_kon, _numer) THEN
		SELECT id_zgl FROM zgloszenie WHERE 
		_id_kon = id_kon AND
		id_zgl NOT IN 
		(SELECT id_zgl FROM wynik NATURAL JOIN seria WHERE _id_kon = id_kon AND _numer = numer)
		LIMIT 1
		INTO wyn;
		
	ELSE
		SELECT id_zgl FROM wynik NATURAL JOIN seria WHERE 
		(punkty >= prog_do_serii(_id_kon, _numer) AND
		_id_kon = id_kon AND _numer - 1 = numer AND
		id_zgl NOT IN 
			(SELECT id_zgl FROM wynik NATURAL JOIN seria WHERE _id_kon = id_kon AND _numer = numer))
		ORDER BY punkty LIMIT 1
		INTO wyn;
	END IF;
	RETURN wyn;
END;
$$ LANGUAGE plpgsql;

DROP FUNCTION nastepny_zawodnik(integer);

CREATE OR REPLACE FUNCTION nastepny_zawodnik(_id_kon INTEGER) RETURNS INTEGER AS $$
DECLARE
	_numer INTEGER;
	wyn INTEGER;
BEGIN
	SELECT aktualna_seria(_id_kon) INTO _numer;
	IF _numer = null THEN
		RETURN null;
	END IF;
	SELECT nastepny(_id_kon, _numer) INTO wyn;
	RETURN wyn;
END;
$$ LANGUAGE plpgsql;

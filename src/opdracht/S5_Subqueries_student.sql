-- ------------------------------------------------------------------------
-- Data & Persistency
-- Opdracht S5: Subqueries
--
-- (c) 2020 Hogeschool Utrecht
-- Tijmen Muller (tijmen.muller@hu.nl)
-- André Donk (andre.donk@hu.nl)
--
--
-- Opdracht: schrijf SQL-queries om onderstaande resultaten op te vragen,
-- aan te maken, verwijderen of aan te passen in de database van de
-- bedrijfscasus.
--
-- NB: Gebruik in elke vraag van deze opdracht een subquery.
--
-- Codeer je uitwerking onder de regel 'DROP VIEW ...' (bij een SELECT)
-- of boven de regel 'ON CONFLICT DO NOTHING;' (bij een INSERT)
-- Je kunt deze eigen query selecteren en los uitvoeren, en wijzigen tot
-- je tevreden bent.

-- Vervolgens kun je je uitwerkingen testen door de testregels
-- (met [TEST] erachter) te activeren (haal hiervoor de commentaartekens
-- weg) en vervolgens het hele bestand uit te voeren. Hiervoor moet je de
-- testsuite in de database hebben geladen (bedrijf_postgresql_test.sql).
-- NB: niet alle opdrachten hebben testregels.
--
-- Lever je werk pas in op Canvas als alle tests slagen.
-- ------------------------------------------------------------------------


-- S5.1.
-- Welke medewerkers hebben zowel de Java als de XML cursus
-- gevolgd? Geef hun personeelsnummers.
DROP VIEW IF EXISTS s5_1; CREATE OR REPLACE VIEW s5_1 AS                                                     -- [TEST]
select il.cursist
from inschrijvingen il
         inner join inschrijvingen ir
                    on il.cursist=ir.cursist
where il.cursus = 'JAV' AND ir.cursus = 'XML';


-- S5.2.
-- Geef de nummers van alle medewerkers die niet aan de afdeling 'OPLEIDINGEN'
-- zijn verbonden.
DROP VIEW IF EXISTS s5_2; CREATE OR REPLACE VIEW s5_2 AS                                                     -- [TEST]
select mnr from medewerkers where afd != 20;


-- S5.3.
-- Geef de nummers van alle medewerkers die de Java-cursus niet hebben
-- gevolgd.
-- DROP VIEW IF EXISTS s5_3; CREATE OR REPLACE VIEW s5_3 AS                                                     -- [TEST]
select mnr
from medewerkers m
         inner join inschrijvingen i
                    on m.mnr=i.cursist
where i.cursus !='JAV'
group by mnr


-- S5.4.
-- a. Welke medewerkers hebben ondergeschikten? Geef hun naam.
-- DROP VIEW IF EXISTS s5_4a; CREATE OR REPLACE VIEW s5_4a AS                                                   -- [TEST]

-- b. En welke medewerkers hebben geen ondergeschikten? Geef wederom de naam.
-- DROP VIEW IF EXISTS s5_4b; CREATE OR REPLACE VIEW s5_4b AS                                                   -- [TEST]


-- S5.5.
-- Geef cursuscode en begindatum van alle uitvoeringen van programmeercursussen
-- ('BLD') in 2020.
DROP VIEW IF EXISTS s5_5; CREATE OR REPLACE VIEW s5_5 AS                                                     -- [TEST]
select cursus, begindatum from uitvoeringen u, cursussen c where u.cursus = c.code and EXTRACT(YEAR FROM begindatum)= '2020' and  c.type = 'BLD';


-- S5.6.
-- Geef van alle cursusuitvoeringen: de cursuscode, de begindatum en het
-- aantal inschrijvingen (`aantal_inschrijvingen`). Sorteer op begindatum.
DROP VIEW IF EXISTS s5_6; CREATE OR REPLACE VIEW s5_6 AS                                                     -- [TEST]
select u.cursus, u.begindatum, count(*) filter (where cursist is not null)
from inschrijvingen i right join uitvoeringen u on (i.cursus = u.cursus and i.begindatum = u.begindatum)
group by u.cursus, u.begindatum
order by u.begindatum;


-- S5.7.
-- Geef voorletter(s) en achternaam van alle trainers die ooit tijdens een
-- algemene ('ALG') cursus hun eigen chef als cursist hebben gehad.
DROP VIEW IF EXISTS s5_7; CREATE OR REPLACE VIEW s5_7 AS                                                     -- [TEST]
select DISTINCT voorl, naam from uitvoeringen u
                                     inner join inschrijvingen i on u.cursus = i.cursus and u.begindatum = i.begindatum
                                     inner join medewerkers m on u.docent = m.mnr
where u.cursus in (select code from cursussen where type = 'ALG')
  and cursist = chef;


-- S5.8.
-- Geef de naam van de medewerkers die nog nooit een cursus hebben gegeven.
-- DROP VIEW IF EXISTS s5_8; CREATE OR REPLACE VIEW s5_8 AS                                                     -- [TEST]



-- -------------------------[ HU TESTRAAMWERK ]--------------------------------
-- Met onderstaande query kun je je code testen. Zie bovenaan dit bestand
-- voor uitleg.

SELECT * FROM test_select('S5.1') AS resultaat
UNION
SELECT * FROM test_select('S5.2') AS resultaat
UNION
SELECT * FROM test_select('S5.3') AS resultaat
UNION
SELECT * FROM test_select('S5.4a') AS resultaat
UNION
SELECT * FROM test_select('S5.4b') AS resultaat
UNION
SELECT * FROM test_select('S5.5') AS resultaat
UNION
SELECT * FROM test_select('S5.6') AS resultaat
UNION
SELECT * FROM test_select('S5.7') AS resultaat
UNION
SELECT * FROM test_select('S5.8') AS resultaat
ORDER BY resultaat;

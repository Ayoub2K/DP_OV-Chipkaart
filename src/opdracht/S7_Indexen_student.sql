-- ------------------------------------------------------------------------
-- Data & Persistency
-- Opdracht S7: Indexen
--
-- (c) 2020 Hogeschool Utrecht
-- Tijmen Muller (tijmen.muller@hu.nl)
-- André Donk (andre.donk@hu.nl)
-- ------------------------------------------------------------------------
-- LET OP, zoals in de opdracht op Canvas ook gezegd kun je informatie over
-- het query plan vinden op: https://www.postgresql.org/docs/current/using-explain.html


-- S7.1.
--
-- Je maakt alle opdrachten in de 'sales' database die je hebt aangemaakt en gevuld met
-- de aangeleverde data (zie de opdracht op Canvas).
--
-- Voer het voorbeeld uit wat in de les behandeld is:
-- 1. Voer het volgende EXPLAIN statement uit:
--    EXPLAIN SELECT * FROM order_lines WHERE stock_item_id = 9;
--    Bekijk of je het resultaat begrijpt. Kopieer het explain plan onderaan de opdracht
-- 2. Voeg een index op stock_item_id toe:
--    CREATE INDEX ord_lines_si_id_idx ON order_lines (stock_item_id);
-- 3. Analyseer opnieuw met EXPLAIN hoe de query nu uitgevoerd wordt
--    Kopieer het explain plan onderaan de opdracht
-- 4. Verklaar de verschillen. Schrijf deze hieronder op.

-- "Gather  (cost=1000.00..6152.07 rows=1008 width=96)"
-- "  Workers Planned: 2"
-- "  ->  Parallel Seq Scan on order_lines  (cost=0.00..5051.27 rows=420 width=96)"
-- "        Filter: (stock_item_id = 9)"
--
-- "Bitmap Heap Scan on order_lines  (cost=12.11..2303.97 rows=1008 width=96)"
-- "  Recheck Cond: (stock_item_id = 9)"
-- "  ->  Bitmap Index Scan on ord_lines_si_id_idx  (cost=0.00..11.85 rows=1008 width=0)"
-- "        Index Cond: (stock_item_id = 9)"
--
-- het resultaat krijg je nu door te zoeken naar de index waardoor de snelheid verbeterd wordt




-- S7.2.
--
-- 1. Maak de volgende twee query’s:
-- 	  A. Toon uit de order tabel de order met order_id = 73590
select * from orders where order_id = 73590;

-- 	  B. Toon uit de order tabel de order met customer_id = 1028
select * from orders where customer_id = 1028;


-- 2. Analyseer met EXPLAIN hoe de query’s uitgevoerd worden en kopieer het explain plan onderaan de opdracht

-- "Index Scan using pk_sales_orders on orders  (cost=0.29..8.31 rows=1 width=155)"
-- "  Index Cond: (order_id = 73590)"

-- "Seq Scan on orders  (cost=0.00..1819.94 rows=107 width=155)"
-- "  Filter: (customer_id = 1028)"

-- 3. Verklaar de verschillen en schrijf deze op

-- de tweede query doet er een stuk korter over omdat hij langs minder rows hoeft te gaan

-- 4. Voeg een index toe, waarmee query B versneld kan worden
CREATE INDEX orders_customer_id_index ON orders (customer_id);
-- 5. Analyseer met EXPLAIN en kopieer het explain plan onder de opdracht
-- "Bitmap Heap Scan on orders  (cost=5.12..308.96 rows=107 width=155)"
-- "  Recheck Cond: (customer_id = 1028)"
-- "  ->  Bitmap Index Scan on orders_customer_id_index  (cost=0.00..5.10 rows=107 width=0)"
-- "        Index Cond: (customer_id = 1028)"
-- 6. Verklaar de verschillen en schrijf hieronder op
-- de cost is veel lager onmdat de database meteen naar de juiste index gaat en vanaf daar  dus zoekt
-- alleen omdat er voor elke id een apparte index gemaakt wordt is het nogsteeds niet super efficient

-- S7.3.A
--
-- Het blijkt dat customers regelmatig klagen over trage bezorging van hun bestelling.
-- Het idee is dat verkopers misschien te lang wachten met het invoeren van de bestelling in het systeem.
-- Daar willen we meer inzicht in krijgen.
-- We willen alle orders (order_id, order_date, salesperson_person_id (als verkoper),
--    het verschil tussen expected_delivery_date en order_date (als levertijd),  
--    en de bestelde hoeveelheid van een product zien (quantity uit order_lines).
-- Dit willen we alleen zien voor een bestelde hoeveelheid van een product > 250
--   (we zijn nl. als eerste geïnteresseerd in grote aantallen want daar lijkt het vaker mis te gaan)
-- En verder willen we ons focussen op verkopers wiens bestellingen er gemiddeld langer over doen.
-- De meeste bestellingen kunnen binnen een dag bezorgd worden, sommige binnen 2-3 dagen.
-- Het hele bestelproces is er op gericht dat de gemiddelde bestelling binnen 1.45 dagen kan worden bezorgd.
-- We willen in onze query dan ook alleen de verkopers zien wiens gemiddelde levertijd 
--  (expected_delivery_date - order_date) over al zijn/haar bestellingen groter is dan 1.45 dagen.
-- Maak om dit te bereiken een subquery in je WHERE clause.
-- Sorteer het resultaat van de hele geheel op levertijd (desc) en verkoper.
-- 1. Maak hieronder deze query (als je het goed doet zouden er 377 rijen uit moeten komen, en het kan best even duren...)

select o.order_id, o.order_date, o.salesperson_person_id as verkoper, ol.quantity, (o.expected_delivery_date - o.order_date) as levertijd
    from orders o, order_lines ol, customers c WHERE  o.order_id = ol.order_id AND c.customer_id = o.salesperson_person_id
        and ol.quantity > 250 and (SELECT avg(expected_delivery_date - order_date)
            FROM orders o, customers cu WHERE o.salesperson_person_id = c.customer_id AND c.customer_id = cu.customer_id
                GROUP BY c.customer_id) > 1.45
                    order by levertijd DESC, verkoper;

-- S7.3.B
--
-- 1. Vraag het EXPLAIN plan op van je query (kopieer hier, onder de opdracht)
-- 2. Kijk of je met 1 of meer indexen de query zou kunnen versnellen
-- 3. Maak de index(en) aan en run nogmaals het EXPLAIN plan (kopieer weer onder de opdracht) 
-- 4. Wat voor verschillen zie je? Verklaar hieronder.

-- Sort  (cost=48777.44..48778.20 rows=306 width=20)
-- "  Sort Key: ((o.expected_delivery_date - o.order_date)) DESC, o.salesperson_person_id"
--   ->  Merge Join  (cost=7656.84..48764.80 rows=306 width=20)
--         Merge Cond: (c.customer_id = o.salesperson_person_id)
--         ->  Index Only Scan using pk_sales_customers on customers c  (cost=0.28..1297571.05 rows=221 width=4)
--               Filter: ((SubPlan 1) > 1.45)
--               SubPlan 1
--                 ->  GroupAggregate  (cost=0.28..1957.04 rows=1 width=36)
--                       Group Key: c.customer_id
--                       ->  Nested Loop  (cost=0.28..1901.83 rows=7360 width=12)
--                             ->  Index Only Scan using pk_sales_customers on customers cu  (cost=0.28..8.29 rows=1 width=0)
--                                   Index Cond: (customer_id = c.customer_id)
--                             ->  Seq Scan on orders o_1  (cost=0.00..1819.94 rows=7360 width=8)
--                                   Filter: (salesperson_person_id = c.customer_id)
--         ->  Sort  (cost=7656.57..7658.86 rows=918 width=20)
--               Sort Key: o.salesperson_person_id
--               ->  Gather  (cost=1000.29..7611.39 rows=918 width=20)
--                     Workers Planned: 2
--                     ->  Nested Loop  (cost=0.29..6519.59 rows=382 width=20)
--                           ->  Parallel Seq Scan on order_lines ol  (cost=0.00..5051.27 rows=382 width=8)
--                                 Filter: (quantity > 250)
--                           ->  Index Scan using pk_sales_orders on orders o  (cost=0.29..3.84 rows=1 width=16)
--                                 Index Cond: (order_id = ol.order_id)

CREATE INDEX index_orders ON orders (order_id ) ;

-- Na index
-- "Sort  (cost=48777.44..48778.20 rows=306 width=20)"
-- "  Sort Key: ((o.expected_delivery_date - o.order_date)) DESC, o.salesperson_person_id"
-- "  ->  Merge Join  (cost=7656.84..48764.80 rows=306 width=20)"
-- "        Merge Cond: (c.customer_id = o.salesperson_person_id)"
-- "        ->  Index Only Scan using pk_sales_customers on customers c  (cost=0.28..1297571.05 rows=221 width=4)"
-- "              Filter: ((SubPlan 1) > 1.45)"
-- "              SubPlan 1"
-- "                ->  GroupAggregate  (cost=0.28..1957.04 rows=1 width=36)"
-- "                      Group Key: c.customer_id"
-- "                      ->  Nested Loop  (cost=0.28..1901.83 rows=7360 width=12)"
-- "                            ->  Index Only Scan using pk_sales_customers on customers cu  (cost=0.28..8.29 rows=1 width=0)"
-- "                                  Index Cond: (customer_id = c.customer_id)"
-- "                            ->  Seq Scan on orders o_1  (cost=0.00..1819.94 rows=7360 width=8)"
-- "                                  Filter: (salesperson_person_id = c.customer_id)"
-- "        ->  Sort  (cost=7656.57..7658.86 rows=918 width=20)"
-- "              Sort Key: o.salesperson_person_id"
-- "              ->  Gather  (cost=1000.29..7611.39 rows=918 width=20)"
-- "                    Workers Planned: 2"
-- "                    ->  Nested Loop  (cost=0.29..6519.59 rows=382 width=20)"
-- "                          ->  Parallel Seq Scan on order_lines ol  (cost=0.00..5051.27 rows=382 width=8)"
-- "                                Filter: (quantity > 250)"
-- "                          ->  Index Scan using pk_sales_orders on orders o  (cost=0.29..3.84 rows=1 width=16)"
-- "                                Index Cond: (order_id = ol.order_id)"

-- ik zie weinig vershil in de sort





-- S7.3.C
--
-- Zou je de query ook heel anders kunnen schrijven om hem te versnellen?
-- met minder sub query's zou het sneller zijn
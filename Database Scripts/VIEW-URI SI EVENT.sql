-- ----------------------------------------------------
-- ADAUGAREA VIEW-URILOR SI A EVENT-ULUI
-- ----------------------------------------------------

-- VIEW PENTRU ACTIVITATILE VIITOARE DIN CALENDAR
drop view if exists vw_calendar_viitor;
delimiter //
create view vw_calendar_viitor as
	select cal.*, a.interval_zile, cnd.id_predare, cnd.id_activitate, p.id_profesor, p.id_materie, m.nume, m.descriere, m.an, a.denumire_activitate_didactica, a.denumire_evaluare, ifnull(count(ia.id_student), 0) as nr_studenti 
    from calendar cal
	inner join conditie cnd on cnd.id_conditie = cal.id_conditie
	inner join predare p on p.id_predare = cnd.id_predare
	inner join materie m on m.id_materie = p.id_materie
	inner join activitate a on a.id_activitate = cnd.id_activitate
    left join inscriere_activitate ia on ia.id_programare = cal.id_programare
	where now() <= cal.data_terminare
    group by cal.id_programare;
//

-- VIEW PENTRU CATALOG
drop view if exists vw_catalog;
delimiter //
create view vw_catalog as
	select p.id_profesor, concat(prof.nume, ' ', prof.prenume) as profesor, im.id_student, concat(u.nume, ' ', u.prenume) as student, m.id_materie, m.nume as materie, im.id_predare, cnd.id_activitate, a.denumire_activitate_didactica as activitate, c.nota
	from inscriere_materie im
	inner join utilizator u on u.id_utilizator = im.id_student
	inner join predare p on p.id_predare = im.id_predare
	inner join utilizator prof on prof.id_utilizator = p.id_profesor
	inner join materie m on m.id_materie = p.id_materie
	inner join (
		select id_predare, count(id_activitate) as nr_note_necesar
		from conditie
		group by id_predare
	) cnr on cnr.id_predare = p.id_predare
	inner join conditie cnd on cnd.id_predare = im.id_predare
	inner join activitate a on a.id_activitate = cnd.id_activitate
	inner join catalog c on c.id_conditie = cnd.id_conditie;
//

-- EVENT CARE UPDATEAZA TIMPUL DE VALABILITATE RAMAS AL UNUI EVENIMENT DINTR-UN GRUP DE STUDIU
drop event if exists verificare_evenimente;
delimiter //
CREATE EVENT verificare_evenimente
ON SCHEDULE EVERY 1 MINUTE
STARTS CURRENT_TIMESTAMP
ENDS CURRENT_TIMESTAMP + INTERVAL 1 year
DO
begin
	update eveniment
    set valabilitate = valabilitate - interval 1 minute
    where valabilitate > time("00:00:00");
    
    delete from participanti_event
    where id_event in (
		select tbl.id_event from (
			select e.* from 
			eveniment e
			left join participanti_event pe on pe.id_event = e.id_event
			where e.valabilitate <= time("00:00:00")
			group by e.id_event
			having count(pe.id_student) < e.nr_min_participanti
		) tbl
    );
    
    delete from eveniment 
    where id_event in (
		select tbl.id_event from (
			select e.* from 
			eveniment e
			left join participanti_event pe on pe.id_event = e.id_event
			where e.valabilitate <= time("00:00:00")
			group by e.id_event
			having count(pe.id_student) < e.nr_min_participanti
		) tbl
    );
end//
delimiter ;
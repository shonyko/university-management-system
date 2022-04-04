-- ----------------------------------------------------------
-- ADAUGAREA PROCEDURILOR PENTRU TIPUL DE UTILIZATOR PROFESOR
-- ----------------------------------------------------------

-- PROCEDURA PRIN CARE PROFESORUL POATE VEDEA MATERIILE LA CARE A FOST ASIGNAT
drop procedure if exists profesor_assigned_courses;
delimiter //
create procedure profesor_assigned_courses(profesor_id varchar(255))
begin
	set @p_id = nullif(profesor_id, '');
    
	select m.* from predare p
	inner join materie m on m.id_materie = p.id_materie
	where p.id_profesor = @p_id;
end; //

-- PROCEDURA PRIN CARE PROFESORUL ISI POATE VEDEA ACTIVITATILE DIN CADRUL MATERIEI
drop procedure if exists profesor_activities;
delimiter //
create procedure profesor_activities(profesor_id varchar(255), course_id varchar(255))
begin
	set @p_id = nullif(profesor_id, '');
    set @c_id = nullif(course_id, '');
    
	select a.*, c.procentaj, c.id_conditie, m.nume from conditie c
	inner join predare p on p.id_predare = c.id_predare
    inner join activitate a on a.id_activitate = c.id_activitate
    inner join materie m on m.id_materie = p.id_materie
	where p.id_profesor = @p_id and (p.id_materie = @c_id or @c_id is null);
end; //

-- PROCEDURA PRIN CARE PROFESORUL POATE VEDEA STUDENTII IN FUNCTIE DE MATERIE
drop procedure if exists profesor_students_by_course;
delimiter //
create procedure profesor_students_by_course(profesor_id varchar(255), course_id varchar(255))
begin
	set @p_id = nullif(profesor_id, '');
    set @c_id = nullif(course_id, '');
    
	select u.*, s.* from predare p 
	inner join inscriere_materie im on im.id_predare = p.id_predare
	inner join student s on s.id_student = im.id_student
	inner join utilizator u on u.id_utilizator = s.id_student
	where p.id_profesor = @p_id and p.id_materie = @c_id;
end; //

-- PROCEDURA PRIN CARE PROFESORUL ISI PROGRAMEAZA ACTIVITATILE IN CADRUL MATERIEI PE CARE O PREDA
drop procedure if exists profesor_programare_activitati;
delimiter //
create procedure profesor_programare_activitati (id_conditie int, data_incepere datetime, data_terminare datetime, evaluare bool, 
interval_zile int, nr_studenti int)
begin 
    set @id_conditie = id_conditie;
    set @data_incepere = data_incepere;
    set @data_terminare = data_terminare;
    set @evaluare = evaluare;
    set @nr_studenti = nr_studenti;
    
    set @id_conditie_valid = if(exists (select id_conditie from conditie where conditie.id_conditie = @id_conditie), 1, 0);
    if @id_conditie_valid = 1 then
		insert into calendar 
        values(0, @id_conditie, @data_incepere, @data_terminare, @evaluare, @nr_studenti);
	else
		set @mesaj = "Eroare la programare activitate";
		signal sqlstate '45000' set message_text = @mesaj;
	end if;
end //

-- PROCEDURA PRIN CARE PROFESORUL ISI VEDE CALENDARUL
drop procedure if exists profesor_calendar;
delimiter //
create procedure profesor_calendar(profesor_id varchar(255))
begin
	set @p_id = nullif(profesor_id, '');
    
	select vw.*
	from vw_calendar_viitor vw
    where vw.id_profesor = @p_id;
end; //

-- PROCEDURA PRIN CARE PROFESORUL ISI VEDE DOAR CALENDARUL CURENT
drop procedure if exists profesor_calendar_curent;
delimiter //
create procedure profesor_calendar_curent(profesor_id varchar(255))
begin
	set @p_id = nullif(profesor_id, '');
    
	select vw.*
	from vw_calendar_viitor vw
    where vw.id_profesor = @p_id
    and (datediff(now(), tbl.data_incepre) >= 0 and datediff(now(), vw.data_incepre) % vw.interval_zile = 0);
end; //
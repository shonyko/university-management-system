-- ---------------------------------------------------------
-- ADAUGAREA PROCEDURILOR PENTRU TIPUL DE UTILIZATOR STUDENT
-- ---------------------------------------------------------

-- PROCEDURA PRIN CARE STUDENTUL VEDE MATERIILE LA CARE SE POATE INSCRIE
drop procedure if exists student_joinable_courses;
delimiter //
create procedure student_joinable_courses(student_id varchar(255))
begin
	set @s_id = nullif(student_id, '');
    
    set @an = null;
    select s.an into @an from student s where s.id_student = @s_id;
    
	select m.* from materie m
	where m.id_materie not in(
		select m.id_materie from inscriere_materie im
		inner join predare p on p.id_predare = im.id_predare
		inner join materie m on m.id_materie = p.id_materie
		where id_student = @s_id
	)
    and m.an <= @an;
end; //

-- PROCEDURA PRIN CARE STUDENTUL VEDE MATERIILE SELECTATE
drop procedure if exists student_selected_courses;
delimiter //
create procedure student_selected_courses(student_id varchar(255))
begin
	set @s_id = nullif(student_id, '');
    
	select m.* from inscriere_materie im
	inner join predare p on p.id_predare = im.id_predare
	inner join materie m on m.id_materie = p.id_materie
	where id_student = @s_id;
end; //

-- PROCEDURA PRIN CARE STUDENTUL SE INSCRIE LA O MATERIE
drop procedure if exists student_join_cours;
delimiter //
create procedure student_join_cours(student_id varchar(255), curs_id varchar(255))
begin
	set @s_id = nullif(student_id, '');
    set @c_id = nullif(curs_id, '');
    
    set @p_id = null;
    select t.id_predare into @p_id from (
		select p.id_predare, ifnull(count(im.id_student), 0) as cnt 
		from predare p
		left join inscriere_materie im on im.id_predare = p.id_predare
		where p.id_materie = @c_id
		group by p.id_predare
		order by cnt
		limit 1
    ) t;
    
    insert into inscriere_materie values (0, @s_id, @p_id);
end; //

-- PROCEDURA PRIN CARE STUDENTUL RENUNTA LA O MATERIE
drop procedure if exists student_drop_cours;
delimiter //
create procedure student_drop_cours(student_id varchar(255), curs_id varchar(255))
begin
	set @s_id = nullif(student_id, '');
    set @c_id = nullif(curs_id, '');
    
    set @im_id = null;
    select t.id_inscriere_m into @im_id from (
		select im.id_inscriere_m
		from predare p 
		inner join inscriere_materie im on im.id_predare = p.id_predare
		where p.id_materie = @c_id
		and im.id_student = @s_id
    ) t;
    
    delete from inscriere_materie where id_inscriere_m = @im_id;
end; //

-- PROCEDURA PRIN CARE STUDENTUL ISI POATE VEDEA NOTELE
drop procedure if exists student_get_note;
delimiter //
create procedure student_get_note(student_id varchar(255))
begin
	set @s_id = nullif(student_id, '');
    
	select m.nume, case when cnr.nr_note_necesar = tbl2.nr_note_actual then tbl2.nota else null end as nota
	from inscriere_materie im
	inner join predare p on p.id_predare = im.id_predare
	inner join materie m on m.id_materie = p.id_materie
	inner join (
		select id_predare, count(id_activitate) as nr_note_necesar
		from conditie
		group by id_predare
	) cnr on cnr.id_predare = p.id_predare
	left join (
		select tbl.id_predare, sum(tbl.punctaj) as nota, count(tbl.punctaj) as nr_note_actual from (
			-- modificat aici
			-- select c.id_student, c.nota, cnd.procentaj, cnd.id_predare, p.id_materie, c.nota * cnd.procentaj / 100 as punctaj 
-- 			from catalog c 
-- 			inner join conditie cnd on cnd.id_conditie = c.id_conditie
-- 			inner join predare p on p.id_predare = cnd.id_predare
-- 			where c.id_student = @s_id 


			-- select c.id_student, cnd.id_predare, cnd.procentaj, avg(c.nota) as media, avg(c.nota) * cnd.procentaj / 100 as punctaj 
-- 			from catalog c
-- 			inner join conditie cnd on cnd.id_conditie = c.id_conditie
-- 			where c.id_student = @s_id
-- 			group by c.id_student, cnd.id_predare

			select c.id_student, c.nota, cnd.procentaj, cnd.id_predare, avg(c.nota) as media, avg(c.nota) * cnd.procentaj / 100 as punctaj 
			from catalog c
			inner join conditie cnd on cnd.id_conditie = c.id_conditie
			where c.id_student = @s_id
			group by c.id_student, cnd.id_predare, cnd.id_activitate
		) tbl
		group by tbl.id_predare
	) tbl2 on tbl2.id_predare = p.id_predare 
	where im.id_student = @s_id;
end; //

-- PROCEDURA PRIN CARE STUDENTUL ISI POATE VEDEA CALENDARUL
drop procedure if exists student_calendar;
delimiter //
create procedure student_calendar(student_id varchar(255))
begin
	set @s_id = nullif(student_id, '');
    
    select tbl.*, case when ia.id_student is not null then 'Inscris' else 'Neinscris' end as inscris 
	from inscriere_materie im 
	inner join (
		select vw.*
        from vw_calendar_viitor vw
	) tbl on tbl.id_predare = im.id_predare
	left join inscriere_activitate ia on ia.id_student = im.id_student and ia.id_programare = tbl.id_programare
	where im.id_student = @s_id;
end; //

-- PROCEDURA PRIN CARE STUDENTUL ISI POATE VEDEA DOAR CALENDARUL CURENT
drop procedure if exists student_calendar_curent;
delimiter //
create procedure student_calendar_curent(student_id varchar(255))
begin
	set @s_id = nullif(student_id, '');
    
    select tbl.*, case when ia.id_student is not null then 'Inscris' else 'Neinscris' end as inscris 
	from inscriere_materie im 
	inner join (
		select vw.*
        from vw_calendar_viitor vw
	) tbl on tbl.id_predare = im.id_predare
	left join inscriere_activitate ia on ia.id_student = im.id_student and ia.id_programare = tbl.id_programare
	where im.id_student = @s_id
    and (datediff(now(), tbl.data_incepre) >= 0 and datediff(now(), tbl.data_incepre) % tbl.interval_zile = 0);	
end; //

-- PROCEDURA PRIN CARE SE VERIFICA SUPRAPUNEREA ORELOR DIN CALENDARUL STUDENTULUI
drop procedure if exists student_calendar_check_collision;
delimiter //
create procedure student_calendar_check_collision(student_id varchar(255), programare_id varchar(255))
begin
	set @s_id = nullif(student_id, '');
    set @p_id = nullif(programare_id, '');
    
    set @d_inceput = null;
	select data_incepre into @d_inceput from calendar
	where id_programare = @p_id;

	select tbl.*, case when ia.id_student is not null then 'Inscris' else 'Neinscris' end as inscris 
	from inscriere_materie im 
	inner join (
		select vw.*
        from vw_calendar_viitor vw
	) tbl on tbl.id_predare = im.id_predare
	left join inscriere_activitate ia on ia.id_student = im.id_student and ia.id_programare = tbl.id_programare
	where im.id_student = @s_id
	and ia.id_student is not null
	and DAYOFWEEK(@d_inceput) = dayofweek(tbl.data_incepre)
	and time(@d_inceput) between time(tbl.data_incepre) and time(tbl.data_terminare);
end; //

-- PROCEDURA PRIN CARE STUDENTUL SE INSCRIE LA O ACTIVITATE
drop procedure if exists student_enroll_activity;
delimiter //
create procedure student_enroll_activity(student_id varchar(255), programare_id varchar(255))
begin
	set @s_id = nullif(student_id, '');
    set @p_id = nullif(programare_id, '');
    
    insert into inscriere_activitate values (0, @p_id, @s_id);
end; //

-- PROCEDURA PRIN CARE STUDENTUL VEDE GRUPURILE SALE DE STUDIU
drop procedure if exists student_my_study_groups;
delimiter //
create procedure student_my_study_groups(student_id varchar(255))
begin
	set @s_id = nullif(student_id, '');
    
    select gs.*, m.nume as nume_curs 
	from inscriere_materie im
	inner join predare p on p.id_predare = im.id_predare
    inner join materie m on m.id_materie = p.id_materie
	inner join grupuri_de_studiu gs on gs.id_materie = p.id_materie
	where im.id_student = @s_id
    and gs.id_grup_studiu in (
		select mg.id_grup_studiu
        from membrii_grup mg
        where mg.id_student = @s_id
    );
end; //

-- PROCEDURA PRIN CARE STUDENTUL VEDE GRUPURILE DE STUDIU DE CARE NU APARTINE
drop procedure if exists student_not_my_study_groups;
delimiter //
create procedure student_not_my_study_groups(student_id varchar(255))
begin
	set @s_id = nullif(student_id, '');
    
    select gs.*, m.nume as nume_curs 
	from inscriere_materie im
	inner join predare p on p.id_predare = im.id_predare
    inner join materie m on m.id_materie = p.id_materie
	inner join grupuri_de_studiu gs on gs.id_materie = p.id_materie
	where im.id_student = @s_id
    and gs.id_grup_studiu not in (
		select mg.id_grup_studiu
        from membrii_grup mg
        where mg.id_student = @s_id
    );
end; //

-- PROCEDURA PRIN CARE STUDENTUL POATE VEDEA MEMBRII DINTR-UN GRUP DE STUDIU
drop procedure if exists student_group_members;
delimiter //
create procedure student_group_members(student_id varchar(255), group_id varchar(255))
begin
	set @s_id = nullif(student_id, '');
    set @g_id = nullif(group_id, '');
    
    set @id = null;
    select mg.id_grup_mem into @id
    from membrii_grup mg
    where mg.id_student = @s_id
    and mg.id_grup_studiu = @g_id;
    
    select u.*, s.*
    from membrii_grup mg
    inner join utilizator u on u.id_utilizator = mg.id_student
    inner join student s on s.id_student = u.id_utilizator
    where @id is not null
    and mg.id_grup_studiu = @g_id;
end; //

-- PROCEDURA PRIN CARE VEDEM SUGESTII DE PARTICIPANTI PENTRU UN GRUP DE STUDIU
drop procedure if exists student_group_suggestions;
delimiter //
create procedure student_group_suggestions(student_id varchar(255), group_id varchar(255))
begin
	set @s_id = nullif(student_id, '');
    set @g_id = nullif(group_id, '');
    
    set @id = null;
    select mg.id_grup_mem into @id
    from membrii_grup mg
    where mg.id_student = @s_id
    and mg.id_grup_studiu = @g_id;
    
     select u.*, s.*
    from utilizator u
    inner join student s on s.id_student = u.id_utilizator
    inner join inscriere_materie im on im.id_student = s.id_student
    inner join predare p on im.id_predare = p.id_predare
    inner join grupuri_de_studiu gs on gs.id_materie = p.id_materie
    where @id is not null
    and gs.id_grup_studiu = @g_id
    and u.id_utilizator <> @s_id
    and not exists(select 1 from membrii_grup mg where mg.id_grup_studiu = @g_id and mg.id_student = u.id_utilizator)
    and p.id_predare in (
		select p.id_predare
        from predare p
        inner join inscriere_materie im on im.id_predare = p.id_predare
        where im.id_student = @s_id
    );
    
end; //

-- PROCEDURA PRIN CARE STUDENTUL VEDE MESAJELE DIN CADRUL UNUI GRUP DE STUDIU DE CARE APARTINE
drop procedure if exists student_group_messages;
delimiter //
create procedure student_group_messages(student_id varchar(255), group_id varchar(255))
begin
	set @s_id = nullif(student_id, '');
    set @g_id = nullif(group_id, '');
    
    set @id = null;
    select mg.id_grup_mem into @id
    from membrii_grup mg
    where mg.id_student = @s_id
    and mg.id_grup_studiu = @g_id;
    
    select concat(u.nume, ' ', u.prenume) as sender, m.mesaj
    from membrii_grup mg
    inner join utilizator u on u.id_utilizator = mg.id_student
    inner join mesaje m on m.id_student = u.id_utilizator and m.id_grup_studiu = mg.id_grup_studiu
    where @id is not null
    and mg.id_grup_studiu = @g_id
    order by m.id_mesaje desc;
end; //

-- PROCEDURA PRIN CARE STUDENTUL POATE TRIMITE UN NOU MESAJ PE UN GRUP DE STUDIU
drop procedure if exists student_group_add_msg;
delimiter //
create procedure student_group_add_msg(student_id varchar(255), group_id varchar(255), msg varchar(2056))
begin
	set @s_id = nullif(student_id, '');
    set @g_id = nullif(group_id, '');
    set @msg = msg;
    
    set @id = null;
    select mg.id_grup_mem into @id
    from membrii_grup mg
    where mg.id_student = @s_id
    and mg.id_grup_studiu = @g_id;
    
    if @id is not null then
		insert into mesaje values (0, msg, @g_id, @s_id);
    end if;
end; //

-- PROCEDURA PRIN CARE STUDENTUL ISI POATE VEDEA INBOX-UL
drop procedure if exists student_inbox;
delimiter //
create procedure student_inbox(student_id varchar(255))
begin
	set @s_id = nullif(student_id, '');
    
    select * 
    from inbox i
    where i.id_student = @s_id
    order by i.id_mesaje desc;
end; //

-- PROCEDURA PRIN CARE STUDENTUL SE ALATURA UNUI GRUP DE STUDIU
drop procedure if exists student_join_group;
delimiter //
create procedure student_join_group(student_id varchar(255), group_id varchar(255))
begin
	set @s_id = nullif(student_id, '');
    set @g_id = nullif(group_id, '');
    
    set @id = null;
    select gs.id_grup_studiu into @id 
    from grupuri_de_studiu gs
    inner join predare p on p.id_materie = gs.id_materie
    inner join inscriere_materie im on im.id_predare = p.id_predare
    where gs.id_grup_studiu = @g_id
    and im.id_student = @s_id;
    
    if @id is not null then
		insert into membrii_grup values(0, @g_id, @s_id);
	end if;
end; //

-- PROCEDURA PRIN CARE STUDENTUL PARASESTE UN GRUP DE STUDIU
drop procedure if exists student_exit_group;
delimiter //
create procedure student_exit_group(student_id varchar(255), group_id varchar(255))
begin
	set @s_id = nullif(student_id, '');
    set @g_id = nullif(group_id, '');
    
    delete 
    from membrii_grup 
    where id_grup_studiu = @g_id
    and id_student = @s_id;
end; //

-- PROCEDURA PRIN CARE STUDENTUL CREEAZA UN NOU GRUP DE STUDIU
drop procedure if exists student_create_group;
delimiter //
create procedure student_create_group(student_id varchar(255), materie_id varchar(255), nume varchar(255))
begin
	set @s_id = nullif(student_id, '');
    set @m_id = nullif(materie_id, '');
    set @nume = nume;
    
    if exists (select 1 from student s inner join inscriere_materie im on im.id_student = s.id_student inner join predare p on p.id_predare = im.id_predare where s.id_student = @s_id and p.id_materie = @m_id) then
		insert into grupuri_de_studiu values (0, @m_id, @nume);
        select last_insert_id() into @id;
        insert into membrii_grup values(0, @id, @s_id);
	else 
		signal sqlstate '45000' set message_text = "Nu esti inscris la aceasta materie!";
    end if;
end; //

-- PROCEDURA PRIN CARE STUDENTUL STERGE UN GRUP DE STUDIU
drop procedure if exists student_delete_group;
delimiter //
create procedure student_delete_group(student_id varchar(255), group_id varchar(255))
begin
	set @s_id = nullif(student_id, '');
    set @g_id = nullif(group_id, '');
    
    if exists (select 1 from membrii_grup where id_grup_studiu = @g_id and id_student = @s_id) then
		delete from grupuri_de_studiu
        where id_grup_studiu = @g_id;
	else 
		signal sqlstate '45000' set message_text = "Nu apartii acestui grup!";
    end if;
end; //

-- PROCEDURA PRIN CARE STUDENTUL POATE VEDEA EVENIMENTELE DIN CADRUL UNUI GRUP DE STUDIU
drop procedure if exists student_get_group_events;
delimiter //
create procedure student_get_group_events(student_id varchar(255), group_id varchar(255))
begin
	set @s_id = nullif(student_id, '');
    set @g_id = nullif(group_id, '');
    
    if exists (select 1 from membrii_grup where id_grup_studiu = @g_id and id_student = @s_id) then
		select e.*, u.id_utilizator, t.nume as tip, t.grad as grad, u.id_tip_utilizator, u.cnp, u.nume, u.prenume, u.adresa, u.telefon, u.email, u.iban, u.nr_contract, if(exists(select 1 from participanti_event pe where pe.id_event = e.id_event and pe.id_student = @s_id ), 1, 0) as participa
        from eveniment e
        left join utilizator u on u.id_utilizator = e.id_profesor
        left join tip_utilizator t on t.id_tip_utilizator = u.id_tip_utilizator
        where e.id_grup_studiu = @g_id
        and now() <= e.data_event;
	else 
		signal sqlstate '45000' set message_text = "Nu apartii acestui grup!";
    end if;
end; //

-- PROCEDURA PRIN CARE STUDENTUL VEDE PROFESORII DIN GRUPUL DE STUDIU
drop procedure if exists student_get_group_professors;
delimiter //
create procedure student_get_group_professors(student_id varchar(255), group_id varchar(255))
begin
	set @s_id = nullif(student_id, '');
    set @g_id = nullif(group_id, '');
    
    if exists (select 1 from membrii_grup where id_grup_studiu = @g_id and id_student = @s_id) then
		select  u.*, tu.nume as tip, tu.grad as grad from
		grupuri_de_studiu gs
		inner join predare p on p.id_materie = gs.id_materie
		inner join utilizator u on u.id_utilizator = p.id_profesor
		inner join tip_utilizator tu on tu.id_tip_utilizator = u.id_tip_utilizator
		where gs.id_grup_studiu = @g_id;
	else 
		signal sqlstate '45000' set message_text = "Nu apartii acestui grup!";
    end if;
end; //

-- PROCEDURA PRIN CARE STUDENTUL POATE CREA UN NOU EVENIMENT IN CADRUL UNUI GRUP DE STUDIU
drop procedure if exists student_create_event;
delimiter //
create procedure student_create_event(student_id varchar(255), group_id varchar(255), titlu varchar(255), descriere varchar(255), id_profesor int, data_event varchar(255), durata varchar(255), nr_min_participanti varchar(255), valabilitate varchar(255))
begin
	set @s_id = nullif(student_id, '');
    set @g_id = nullif(group_id, '');
    set @titlu = nullif(titlu, '');
    set @descriere = nullif(descriere, '');
	
    if id_profesor = -1 then
		set @id_profesor = null;
	else
		set @id_profesor = id_profesor;
	end if;
    
    set @data_event = nullif(data_event, '');
    set @durata = nullif(durata, '');
    set @nr_min_participanti = nullif(nr_min_participanti, '');
    set @valabilitate = nullif(valabilitate, '');
    
    if exists (select 1 from membrii_grup where id_grup_studiu = @g_id and id_student = @s_id) then
		insert into eveniment values (0, @g_id, @titlu, @descriere, @id_profesor, @data_event, @durata, @nr_min_participanti, @valabilitate);
        
        set @id_nou = null;
        SELECT LAST_INSERT_ID() into @id_nou;
        
        insert into participanti_event values (0, @id_nou,  @s_id);
        
	else 
		signal sqlstate '45000' set message_text = "Nu apartii acestui grup!";
    end if;
end; //

-- PROCEDURA PRIN CARE STUDENTUL SE POATE INSCRIE LA UN EVENIMENT DIN GRUPUL DE STUDIU
drop procedure if exists student_join_event;
delimiter //
create procedure student_join_event(student_id varchar(255), group_id varchar(255), event_id varchar(255))
begin
	set @s_id = nullif(student_id, '');
    set @g_id = nullif(group_id, '');
	set @e_id = nullif(event_id, '');
    
    if exists (select 1 from membrii_grup where id_grup_studiu = @g_id and id_student = @s_id) then
        insert into participanti_event values (0, @e_id, @s_id);
	else 
		signal sqlstate '45000' set message_text = "Nu apartii acestui grup!";
    end if;
end; //

-- PROCEDURA PRIN CARE STUDENTUL POATE VEDEA ACTIVITATILE CARE TIN DE O ANUMITA MATERIE
drop procedure if exists getActivities;
delimiter //
create procedure getActivities(idStudent varchar(20), idMaterie varchar(20), tip_activitate varchar(100))
begin
	set @studentId=idStudent;
    set @subjectId = idMaterie;
    set @activityType = tip_activitate;
    
    select tbl.*
	from inscriere_materie im 
	inner join (
		select vw.*
        from vw_calendar_viitor vw
	) tbl on tbl.id_predare = im.id_predare
	left join inscriere_activitate ia on ia.id_student = im.id_student and ia.id_programare = tbl.id_programare
	where im.id_student = @studentId
    and tbl.id_materie = @subjectId
    and tbl.evaluare = 0
    and tbl.denumire_activitate_didactica like concat("%", @activityType,"%");
    
end;// 

-- PROCEDURA PRIN CARE STUDENTUL POATE VEDEA ACTIVITATILE LA CARE ESTE INSCRIS
drop procedure if exists getStudentActivities;
delimiter //
create procedure getStudentActivities(idStudent varchar(20))
begin
	set @studentId = idStudent;
    
    select tbl.*
	from inscriere_materie im 
	inner join (
		select vw.*
        from vw_calendar_viitor vw
	) tbl on tbl.id_predare = im.id_predare
	inner join inscriere_activitate ia on ia.id_student = im.id_student and ia.id_programare = tbl.id_programare
	where im.id_student = @studentId
    and tbl.evaluare = 0;
   
end; //

-- PROCEDURA PRIN CARE STUDENTUL POATE VEDEA ACTIVITATILE DIN CADRUL GRUPULUI DE STUDIU
drop procedure if exists getStudentGroupActivities;
delimiter //
create procedure getStudentGroupActivities(studentId varchar(20))
begin

    set @idStudent = nullif(studentId, '');

    select e.*,  u.id_utilizator, t.nume as tip, t.grad as grad, u.id_tip_utilizator, u.cnp, u.nume, u.prenume, u.adresa, u.telefon, u.email, u.iban, u.nr_contract,false as participa from membrii_grup mg, eveniment e
    left join utilizator u on u.id_utilizator = e.id_profesor
    left join tip_utilizator t on t.id_tip_utilizator = u.id_tip_utilizator
    where mg.id_student = @idStudent and e.id_grup_studiu = mg.id_grup_studiu
    and @idStudent not in (select pe.id_student from participanti_event pe where pe.id_event=e.id_event);
end; //
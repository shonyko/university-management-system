-- ---------------------------------------------------------------
-- ADAUGAREA PROCEDURILOR PENTRU TIPUL DE UTILIZATOR ADMINISTRATOR
-- ---------------------------------------------------------------

-- PROCEDURA FOLOSITA LA LOGAREA UTILIZATORULUI
drop procedure if exists login;
delimiter //
create procedure login (username varchar(255), pass varchar(255))
begin
	declare exit handler for sqlexception
    begin
		rollback;
    end;

	start transaction;
    
    select * from date_logare where nume_utilizator = username and pass_hash = pass;
end//
delimiter ;

-- PROCEDURA PRIN CARE ADMINISTRATORUL POATE CAUTA UN UTILIZATOR
drop procedure if exists admin_insert_user;
delimiter //
create procedure admin_insert_user(id1 varchar(20), tip varchar(20), cnp varchar(13), nume varchar(50), prenume varchar(100), adresa varchar(200), telefon varchar(10), email varchar(50), iban varchar(24), nr_contract varchar(100))
begin
	select tip_utilizator.grad into @grad1 from tip_utilizator inner join utilizator on utilizator.id_tip_utilizator = tip_utilizator.id_tip_utilizator and utilizator.id_utilizator = id1;
    select tip_utilizator.grad into @grad2 from tip_utilizator where tip_utilizator.id_tip_utilizator = tip;
	if(@grad1 < @grad2) then
		call insert_user(tip, cnp, nume, prenume, adresa, telefon, email, iban, nr_contract);
    end if;
end; //

-- PROCEDURA PRIN CARE ADMINISTRATORUL POATE CAUTA CURSURILE
drop procedure if exists admin_search_courses;
delimiter //
create procedure admin_search_courses(course_name varchar(100))
begin
	set @cname = nullif(course_name, '');
	
    select * from materie where nume = @cname or @cname is null;
end//

-- PROCEDURA PRIN CARE ADMINISTRATORUL POATE VEDEA PROFESORII IN FUNCTIE DE CURSUL PE CARE IL PREDAU
drop procedure if exists admin_professors_by_course;
delimiter //
create procedure admin_professors_by_course(course_id varchar(100))
begin
	set @c_id = nullif(course_id, '');
    
	select u.*, p.* from profesori p
	inner join utilizator u on u.id_utilizator = p.id_profesor
	inner join predare pre on pre.id_profesor = p.id_profesor
	inner join materie m on m.id_materie = pre.id_materie
	where m.id_materie = @c_id;
end; //

-- PROCEDURA PRIN CARE ADMINISTRATORUL POATE VEDEA PROFESORII IN FUNCTIE DE CURSURILE PE CARE NU LE PREDAU
drop procedure if exists admin_professors_by_not_course;
delimiter //
create procedure admin_professors_by_not_course(course_id varchar(100))
begin
	set @c_id = nullif(course_id, '');
    
	select u.*, p.* from profesori p
	inner join utilizator u on u.id_utilizator = p.id_profesor
	where @c_id not in (
		select pre.id_materie from predare pre
		where pre.id_profesor = p.id_profesor
	);
end; //

-- PROCEDURA PRIN CARE ADMINISTRATORUL POATE VEDEA STUDENTII IN FUNCTIE DE CURSUL LA CARE S-AU INSCRIS
drop procedure if exists admin_students_by_course;
delimiter //
create procedure admin_students_by_course(course_id varchar(100))
begin
	set @c_id = nullif(course_id, '');
    
	select utilizator.*, student.* from utilizator 
	inner join student on utilizator.id_utilizator = student.id_student
	inner join inscriere_materie on student.id_student = inscriere_materie.id_student
	inner join predare on predare.id_predare = inscriere_materie.id_predare and predare.id_materie = @c_id;
end; //

-- ----------------------------------------------------
-- ADAUGAREA CHEILOR STRAINE
-- ----------------------------------------------------
use owo;

-- PROCEDURA PENTRU ADAUGAREA CHEILOR STRAINE
delimiter //
create procedure add_fk(fromTbl varchar(255), fromCol varchar(255), toTbl varchar(255), toCol varchar(255))
begin
	set @q = concat("alter table ", fromTbl, " add constraint `fk-", fromTbl, "-", toTbl, "-", toCol, "` foreign key (", fromCol,") references ", toTbl, "(", toCol, ") on delete cascade");
    prepare stmt from @q;
    execute stmt;
    deallocate prepare stmt;
end//

call add_fk("date_logare", "id_utilizator", "utilizator", "id_utilizator");

call add_fk("utilizator", "id_tip_utilizator", "tip_utilizator", "id_tip_utilizator");

call add_fk("profesori", "id_profesor", "utilizator", "id_utilizator");

call add_fk("conditie", "id_predare", "predare", "id_predare");
call add_fk("conditie", "id_activitate", "activitate", "id_activitate");

call add_fk("calendar", "id_conditie", "conditie", "id_conditie");

call add_fk("predare", "id_profesor", "profesori", "id_profesor");
call add_fk("predare", "id_materie", "materie", "id_materie");

call add_fk("catalog", "id_student", "student", "id_student");
call add_fk("catalog", "id_conditie", "conditie", "id_conditie");

call add_fk("inscriere_materie", "id_predare", "predare", "id_predare");
call add_fk("inscriere_materie", "id_student", "student", "id_student");

call add_fk("student", "id_student", "utilizator", "id_utilizator");

call add_fk("inscriere_activitate", "id_programare", "calendar", "id_programare");
call add_fk("inscriere_activitate", "id_student", "student", "id_student");

call add_fk("inbox", "id_student", "student", "id_student");

call add_fk("grupuri_de_studiu", "id_materie", "materie", "id_materie");

call add_fk("membrii_grup", "id_grup_studiu", "grupuri_de_studiu", "id_grup_studiu");
call add_fk("membrii_grup", "id_student", "student", "id_student");

call add_fk("eveniment", "id_grup_studiu", "grupuri_de_studiu", "id_grup_studiu");
call add_fk("eveniment", "id_profesor", "profesori", "id_profesor");

call add_fk("participanti_event", "id_event", "eveniment", "id_event");
call add_fk("participanti_event", "id_student", "student", "id_student");

call add_fk("mesaje", "id_grup_studiu", "grupuri_de_studiu", "id_grup_studiu");
call add_fk("mesaje", "id_student", "student", "id_student");
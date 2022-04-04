-- ----------------------------------------------------
-- ADAUGAREA TRIGGERELOR
-- ----------------------------------------------------

-- TRIGGER PENTRU GENERAREA NUMARULUI DE CONTRACT
drop trigger if exists nr_contract_lene;
delimiter //
create trigger nr_contract_lene 
before insert on utilizator
for each row
begin
    select max(nr_contract) into @v from utilizator;
    
    if(@v is null) then 
		set @v = 0;
    end if;
    
    set new.nr_contract = @v + 1;
end//
delimiter ;

-- TRIGGER PENTRU ANULAREA UNUI EVENIMENT
drop trigger if exists anulare_eveniment;
delimiter //
create trigger anulare_eveniment 
before delete on participanti_event
for each row
begin
	set @titlu = "filler";
    set @nume = "filler";
    select e.titlu, gs.nume into @titlu, @nume
    from eveniment e
    inner join grupuri_de_studiu gs on gs.id_grup_studiu = e.id_grup_studiu
	where e.id_event = old.id_event;
    
    insert into inbox values(0, old.id_student, "Eveniment anulat", concat("Evenimentul '", @titlu, "' din grupul '", @nume, "' a fost anulat!"));
end//
delimiter ;

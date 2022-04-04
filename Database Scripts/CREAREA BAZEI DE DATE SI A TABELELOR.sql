-- ----------------------------------------------------
-- CREAREA BAZEI DE DATE
-- ----------------------------------------------------

drop database if exists owo;
create database if not exists owo;
use owo;

-- ----------------------------------------------------
-- CREAREA TABELELOR
-- ----------------------------------------------------

-- TABEL PENTRU DATELE DE LOGARE
DROP TABLE IF EXISTS date_logare;
create table if not exists date_logare 
(
	id_utilizator int primary key,
    nume_utilizator varchar(100) not null unique,
    pass_hash varchar(100) not null
);

-- TABEL PENTRU UTILIZATOR
DROP TABLE IF EXISTS utilizator;
create table if not exists utilizator
(
	id_utilizator int primary key auto_increment,
    id_tip_utilizator int not null,
	cnp varchar(13) not null unique,
    nume varchar(50) not null,
    prenume varchar(100) not null,
    adresa varchar(200) not null,
    telefon varchar(10) not null,
    email varchar(50) not null,
    iban varchar(24) not null,
    nr_contract int not null unique
);

-- TABEL PENTRU TIPURILE DE UTILIZATOR
DROP TABLE IF EXISTS tip_utilizator;
create table if not exists tip_utilizator
(
	id_tip_utilizator int primary key auto_increment,
    nume varchar(20) not null,
    grad int not null
);

-- TABEL PENTRU TIPUL DE UTILIZATOR PROFESOR
DROP TABLE IF EXISTS profesori;
create table if not exists profesori 
(
	id_profesor int primary key,
    minim_ore int not null,
    maxim_ore int not null,
    departament varchar(50) not null
);

-- TABEL PENTRU RETINEREA DATELOR DESPRE CONDITIILE IN CARE UN PROFESOR ALEGE SA ISI DESFASOARE MATERIE
-- ALEGAND ACTIVITATILE SI PROCENTAJELE ACESTORA
DROP TABLE IF EXISTS conditie;
create table if not exists conditie
(
	id_conditie int primary key auto_increment,
    id_predare int not null,
    id_activitate int not null,
    procentaj int not null
);
alter table conditie 
add constraint combinatie_unica_conditie 
unique index(id_predare, id_activitate);

-- TABEL PENTRU CALENDAR
DROP TABLE IF EXISTS calendar;
create table if not exists calendar 
(
	id_programare int primary key auto_increment,
    id_conditie int not null,
    data_incepre datetime not null,
    data_terminare datetime not null,
    evaluare bool not null,
    nr_max_studenti int not null
);

-- TABEL IN CARE RETINEM PROFESORII SI CE MATERII PREDAU ACESTIA
DROP TABLE IF EXISTS  predare;
create table if not exists predare 
(
	id_predare int primary key auto_increment,
    id_profesor int not null,
    id_materie int not null
);
alter table predare 
add constraint combinatie_unica_predare 
unique index(id_profesor, id_materie);

-- TABEL PENTRU MATERII
DROP TABLE IF EXISTS materie;
create table if not exists materie 
(
	id_materie int primary key auto_increment,
    nume varchar(100) not null,
    descriere varchar(100),
    an int not null
);

-- TABEL PENTRU ACTIVITATILE DIN CADRUL UNEI MATERII
DROP TABLE IF EXISTS activitate;
create table if not exists activitate 
(
	id_activitate int primary key auto_increment,
    denumire_activitate_didactica varchar(50) not null,
    denumire_evaluare varchar(50),
    interval_zile int not null
);

-- TABEL PENTRU CATALOG
DROP TABLE IF EXISTS catalog;
create table if not exists catalog
(
	id_catalog int primary key auto_increment,
    id_student int not null,
    id_conditie int not null,
    nota float not null
);
alter table catalog 
add constraint combinatie_unica_catalog 
unique index(id_catalog, id_conditie);

-- TABEL PENTRU INSCRIEREA STUDENTULUI LA O ANUMITA MATERIE PREDATA DE UN ANUMIT PROFESOR
DROP TABLE IF EXISTS inscriere_materie;
create table if not exists inscriere_materie
(
	id_inscriere_m int primary key auto_increment,
    id_student int not null,
    id_predare int not null
);
alter table inscriere_materie 
add constraint combinatie_unica_inscriere_m 
unique index(id_student, id_predare);

-- TABEL PENTRU INSCRIEREA STUDENTULUI LA O ANUMITA ACTIVITATE
DROP TABLE IF EXISTS inscriere_activitate;
create table if not exists inscriere_activitate
(
	id_inscriere_a int primary key auto_increment,
    id_programare int not null,
    id_student int not null
);
alter table inscriere_activitate 
add constraint combinatie_unica_inscriere_a 
unique index(id_student, id_programare);

-- TABEL PENTRU MESAJELE DIN CADRUL UNUI GRUP DE STUDIU
DROP TABLE IF EXISTS mesaje;
create table if not exists mesaje 
(
	id_mesaje int primary key auto_increment,
	mesaj varchar(500) not null,
    id_grup_studiu int not null,
    id_student int not null
);

-- TABEL PENTRU TIPUL DE UTILIZATOR STUDENT
DROP TABLE IF EXISTS student;
create table if not exists student
(
	id_student int primary key,
    an int not null,
    nr_ore int not null
);

-- TABEL PENTRU GRUPURILE DE STUDIU
DROP TABLE IF EXISTS grupuri_de_studiu;
create table if not exists grupuri_de_studiu 
(
	id_grup_studiu int primary key auto_increment,
	id_materie int not null,
    nume varchar(100) not null
);

-- TABEL PENTRU EVENIMENTELE DIN CADRUL GRUPURILOR DE STUDIU
DROP TABLE IF EXISTS eveniment;
create table if not exists eveniment
(
	id_event int primary key auto_increment,
    id_grup_studiu int not null,
    titlu varchar(100) not null,
    descriere varchar(100),
    id_profesor int,
    data_event datetime not null,
    durata time not null,
    nr_min_participanti int not null,
    valabilitate time not null
);

-- TABEL PENTRU PARTICIPANTII LA EVENIMENTELE DIN CADRUL GRUPURILOR DE STUDIU
DROP TABLE IF EXISTS participanti_event;
create table if not exists participanti_event 
(
	id_event_mem int primary key auto_increment,
    id_event int not null,
    id_student int not null
);
alter table participanti_event 
add constraint combinatie_unica_participanti_event 
unique index(id_student, id_event);

-- TABEL PENTRU INBOX-UL STUDENTULUI, UNDE POATE VEDEA MESAJELE
DROP TABLE IF EXISTS inbox;
create table if not exists inbox 
(
	id_mesaje int primary key auto_increment,
    id_student int not null,
    titlu varchar(100) not null,
    mesaj varchar(1000) not null
);

-- TABEL PENTRU MEMBRII UNUI GRUP DE STUDIU
DROP TABLE IF EXISTS membrii_grup;
create table if not exists membrii_grup 
(
	id_grup_mem int primary key auto_increment,
    id_grup_studiu int not null,
    id_student int not null
);
alter table membrii_grup 
add constraint combinatie_unica_membrii_grup 
unique index(id_student, id_grup_studiu);
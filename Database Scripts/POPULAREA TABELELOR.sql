-- ----------------------------------------------------
-- POPULAREA TABELELOR
-- ----------------------------------------------------

-- POPULAM TABELUL CU TIPURILE DE UTILIZATOR
insert into tip_utilizator (nume, grad) values
("Super Administrator", 1),
("Administrator", 2),
("Profesor", 3),
("Student", 4);

-- POPULAM TABELUL CU UTILIZATORI
insert into utilizator values 
(0, 1, "2870316176634", "Andrei", "Ionescu", "BD. TRAIAN nr. 22 ap. 24, MARAMUREŞ, 430262", "0785557547", "walex_tu@maliberty.com", "RO76PORL4321834627533961", 1),
(0, 2, "2430303100067", "Andreea", "Metescu", "Strada Dudului 15, Campineanca", "0735557966", "2fuad102040f@bmpk.org", "RO08PORL4543588316116762", 2),
(0, 2, "1890208050053", "Mihai", "Vasile", "STR. PAUL CHINEZU nr. 10, MUREŞ, 540326", "0705559532", "kanaaslngn15o@cetssc.org", "RO92RZBR7263314974655334", 3),
(0, 3, "1461216130011", "Gigel", "Florian", "STR. PRUTULUI nr. 32A, Buzau", "0705558074", "byasminrob@ncpine.com", "RO86PORL2267356132497221", 4),
(0, 3, "1311214410034", "Frone", "Ion", "SAT PĂUŞEŞTI-OTĂSĂU, COM. PĂUŞEŞTI", "0745557073", "gfredbss5@jembott.com", "RO68RZBR7757152429364654", 1),
(0, 3, "2921201260044", "Katarina ", "Partridge", "STR. GHICA ION bl. 5 et parter ap. 1, DÂMBOVIŢA, 130121", "0711555580", "when@air.stream", "RO58PORL5977685253821669", 1),
(0, 3, "6000627460031", "Sahib", "Chase", "Bulevardul Iuliu Maniu 69, Bucuresti 6", "0705555292", "9zied.nouajaaw@cetssc.org", "RO30PORL8377888529462391", 1),
(0, 3, "2690928030054", "Ianis ", "Leech", "Strada Siriului 28-32, Bucuresti", "0705556942", "tali.afrekano3@paratudo.store", "RO59PORL8728846652346687", 1),
(0, 4, "2591120280096", "Guto", "Parry", "STR. 9 MAI nr. 42 bl. B1, BACĂU, 600022", "0705557988", "mces@royfield.com", "RO23PORL6183895213689539", 1),
(0, 4, "2260130240081", "Katy", "Parry", "Strada Stefan Augustin Doinas 32, Arad", "0705557953", "prozisk@18dealnshop.com", "RO75PORL1635116739626617", 1),
(0, 4, "2530819330099", "Fiza", "Dodd", "CAL. LUI TRAIAN nr. 331, VÂLCEA", "0741346407", "okaser_10113@triumphlotto.com", "RO03PORL1387369581661779", 1),
(0, 4, "2580930240017", "Robson", "Pruitt", "STR. ŞELARILOR nr. 2, SIBIU", "0705555039", "ahdkherm@206270.com", "RO63PORL5985863535417379", 1),
(0, 4, "2660429070071", "Romana", "Justice", "Strada Lamaitei 31/5, Tg. Mures 540660", "0711555971", "nantonioferreira@darwinav.com", "RO48RZBR1259698714545315", 1);

-- POPULAM TABELUL CU MATERII
insert into materie values 
(0, "PC", "Programarea calculatoarelor", 1),
(0, "SDA", "Structuri de date si algoritmi", 1),
(0, "ET", "Electrotehnica", 1),
(0, "Fizica", "Fizica", 1),
(0,"MSI", "Matematici speciale in inginerie", 2),
(0, "POO", "Programare orientata pe obiecte", 2);

-- POPULAM TABELUL CU PROFESORI
insert into profesori values
(4, 24, 72,"Electrotehnica"),
(5, 48, 96, "Matematica"),
(6, 24, 96, "Fizica si chimie"),
(7, 48, 72, "Calculatoare"),
(8, 48, 72, "Calculatoare");

-- POPULAM TABELUL CU STUDENTI
insert into student values 
(9, 1, 48),
(10, 1, 48),
(11, 2, 36),
(12, 2, 36),
(13, 2, 48);

-- ASIGNAREA PROFESORILOR LA CURSURI
call owo.admin_assign_prof_course('4', '3');
call owo.admin_assign_prof_course('5', '5');
call owo.admin_assign_prof_course('6', '4');
call owo.admin_assign_prof_course('7', '1');
call owo.admin_assign_prof_course('8', '1');
call owo.admin_assign_prof_course('8', '2');

-- POPULAM TABELUL CU ACTIVITATI
insert into activitate values
(0, "Curs", "Examen", 7),
(0, "Seminar", "Test seminar", 7),
(0, "Laborator", "Colocviu", 7);

-- POPULAM TABELUL CU  CONDITIILE IN CARE PROFESORII ISI DESFASOARA MATERIILE
insert into conditie values
(0, 1, 1, 50),
(0, 1, 2, 20),
(0, 1, 3, 30),
(0, 2, 1, 50),
(0, 2, 2, 0),
(0, 2, 3, 50),
(0, 3, 1, 70),
(0, 3, 2, 0),
(0, 3, 3, 30),
(0, 4, 1, 60),
(0, 4, 2, 10),
(0, 4, 3, 30),
(0, 5, 1, 60),
(0, 5, 2, 10),
(0, 5, 3, 30),
(0, 6, 1, 60),
(0, 6, 2, 0),
(0, 6, 3, 40);

-- POPULAM TABELUL DE INSCRIERE A STUDENTULUI LA O MATERIE
insert into inscriere_materie values
(0, 9, 1),
(0, 9, 2),
(0, 10, 1),
(0, 10, 2),
(0, 10, 3),
(0, 10, 4),
(0, 11, 5),
(0, 12, 6),
(0, 13, 5),
(0, 13, 6);

-- POPULAM CATALOGUL
insert into catalog values 
(0, 9, 1, 7),
(0, 9, 2, 8),
(0, 9, 3, 9),
(0, 13, 13, 10),
(0, 13, 14, 9),
(0, 13, 15, 8),
(0, 13, 16, 7),
(0, 13, 17, 6),
(0, 13, 18, 5);

-- POPULAM CALENDARUL
insert into calendar values
(0, 1, '2021-08-01 10:00:00', '2022-01-20 12:00:00', 0, 120),
(0, 2, '2021-08-02 12:00:00', '2022-01-22 14:00:00', 0, 40),
(0, 2, '2021-08-02 14:00:00', '2022-01-23 16:00:00', 0, 40),
(0, 2, '2021-08-02 16:00:00', '2022-01-24 18:00:00', 0, 40),
(0, 2, '2021-08-03 18:00:00', '2022-01-22 20:00:00', 0, 20),
(0, 3, '2021-08-03 12:00:00', '2022-01-22 14:00:00', 0, 20),
(0, 3, '2021-08-03 08:00:00', '2022-01-22 10:00:00', 0, 20),
(0, 3, '2021-08-03 10:00:00', '2022-01-22 12:00:00', 0, 20),
(0, 3, '2021-08-03 20:00:00', '2022-01-22 22:00:00', 0, 20),
(0, 3, '2021-08-03 14:00:00', '2022-01-22 16:00:00', 0, 20),
(0, 3, '2021-08-03 16:00:00', '2022-01-22 18:00:00', 0, 20),
(0, 4, '2021-09-05 08:00:00', '2022-03-25 10:00:00', 0, 140),
(0, 5, '2021-09-05 10:00:00', '2022-03-25 12:00:00', 0, 60),
(0, 6, '2021-09-08 12:00:00', '2022-03-08 14:00:00', 0, 16),
(0, 7, '2021-08-03 12:00:00', '2022-01-22 14:00:00', 0, 150),
(0, 8, '2021-09-05 16:00:00', '2022-03-25 18:00:00', 0, 50),
(0, 9, '2021-09-30 18:00:00', '2022-03-30 20:00:00', 0, 20),
(0, 1, '2022-01-27 20:00:00', '2022-01-27 22:00:00', 1, 120),
(0, 2, '2022-01-29 08:00:00', '2022-01-29 10:00:00', 1, 120),
(0, 3, '2021-01-29 10:00:00', '2022-01-29 12:00:00', 1, 120);

insert into calendar values
(0, 13, '2021-01-03 10:00:00', '2021-01-03 11:00:00', 1, 7, 120);

-- POPULAM TABELUL DE INSCRIERE A STUDENTULUI LA O ACTIVITATE
insert into inscriere_activitate values
(0, 1, 10),
(0, 2, 10),
(0, 3, 10),
(0, 15, 10),
(0, 16, 10),
(0, 17, 10),
(0, 21, 9),
(0, 7, 9);

-- POPULAM TABELUL CU GRUPURILE DE STUDIU
insert into grupuri_de_studiu values
(0, 1, "Programarea Calculatoarelor - Seria 1"),
(0, 1, "Programarea Calculatoarelor - Seria 2"),
(0, 1, "Programarea Calculatoarelor - Recontractari"),
(0, 2, "SDA - Forum intrebari"),
(0, 2, "SDA - Discutii examen"),
(0, 2, "SDA - Seria 1 si 2"),
(0, 3, "ET - Seria 1 si 2"),
(0, 3, "ET - Laboratoare"),
(0, 4, "Fizica - Seria 1 si 2"),
(0, 5, "MSI - Seminarii"),
(0, 5, "MSI - Seria 1"),
(0, 6, "POO - Seria 1 si 2"),
(0, 6, "POO - Discutii examen"),
(0, 6, "POO - In spate la grajdar");

-- POPULAM TABELUL CU MEMBRII UNUI GRUP DE STUDIU
insert into membrii_grup values
(0, 1, 9),
(0, 4, 9),
(0, 5, 9),
(0, 8, 9),
(0, 9, 9),
(0, 2, 10),
(0, 3, 10),
(0, 4, 10),
(0, 10, 11),
(0, 12, 11),
(0, 14, 11),
(0, 11, 12),
(0, 12, 12),
(0, 14, 12),
(0, 11, 13),
(0, 12, 13),
(0, 14, 13);

-- POPULAM TABELUL CU MESAJE
insert into mesaje values 
(0, "Hello World!", 1, 9),
(0, "Strive for greatness.", 4, 9),
(0, "May your choices reflect your hopes, not your fears.", 5, 9),
(0, "It hurt because it mattered.", 9, 9),
(0, "What we think, we become.", 2, 10),
(0, "No pressure, no diamonds.", 14, 11),
(0, "Damn right homie.", 14, 12),
(0, "I like turtles", 14, 13),
(0, "Boldness be my friend.", 12, 12),
(0, "Be so good they can’t ignore you.", 11, 13),
(0, "Admine da-mi grad.", 11, 12);

-- POPULAM TABELUL IN CARE RETINEM PROFESORII SI MATERIILE PE CARE LE PREDAU
insert into predare values
(0, 7, 6);

-- POPULAM TABELUL CU EVENIMENTE DIN CADRUL UNUI GRUP DE STUDIU
insert into eveniment values
(0, 1, "Rezolvari probleme curs", "Vom rezolva problemele propuse la curs",7,'2021-03-17 14:00:00', "04:00:00",  1, "48:00:00"),
(0, 2, "Rezolvari probleme curs", "Vom rezolva problemele propuse la curs", null ,'2021-03-17 14:00:00', "04:00:00", 2, "48:00:00"),
(0, 4, "Raspundem la intrebari", "Vom raspunde la cele mai puse intrebari de pe forum",7,'2021-04-20 15:00:00', "06:00:00",  2, "12:00:00"),
(0, 9, "Consultatii examen", "Doamna profesoara va raspunde intrebarilor ce privesc partea de materie studiata", 6 ,'2021-02-12 10:00:00', "03:00:00",  2, "12:00:00"),
(0, 14, "Trade de iteme", "Ne strangem in spatem la grajdar si facem
 trade", null, '2021-02-12 10:00:00', "12:00:00", 2, "24:00:00");
 
 -- POPULAM TABELUL CU PARTICIPANTII DE LA UN EVENIMENT DIN CADRUL UNUI GRUP DE STUDIU
 insert into participanti_event values
(0, 1, 9),
(0, 4, 9),
(0, 3, 10),
(0, 5, 11),
(0, 5, 12);

-- POPULAM INBOX-UL CU MESAJE
insert into inbox values 
(0, 9, "Eveniment anulat", "Evenimentul 'Rezolvari variante propuse' din grupul de studiu 'ET - Laboratoare' a fost anulat!"),
(0, 9, "Eveniment anulat", "Evenimentul 'Consultatii examen' din grupul de studiu 'SDA - Seria 1 si 2' a fost anulat!"),
(0, 10, "Eveniment anulat", "Evenimentul 'Spam la admin pana ne da grad' din grupul 'POO - In spate la grajdar' a fost anulat!"),
(0, 11, "Eveniment anulat", "Evenimentul 'Spam la admin pana ne da grad' din grupul 'POO - In spate la grajdar' a fost anulat!");

-- POPULAM CU DATE DE LOGARE
insert into date_logare values
(1, "test", "test"),
(2, "admin", "admin"),
(4, "prof1", "prof1"),
(8, "prof", "prof"),
(9, "student", "student"),
(11, "student1", "student1"),
(12, "student2", "student2");
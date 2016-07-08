DROP TABLE MESSAGES CASCADE CONSTRAINTS;
DROP TABLE GROUPMEMBERS CASCADE CONSTRAINTS;
DROP TABLE FRIENDSHIPS CASCADE CONSTRAINTS;
DROP TABLE GROUPS CASCADE CONSTRAINTS;
DROP TABLE USERS CASCADE CONSTRAINTS;

PURGE RECYCLEBIN;

CREATE TABLE USERS
   (	ID NUMBER(10,0) NOT NULL ENABLE,
	FNAME VARCHAR2(100 BYTE) NOT NULL ENABLE,
	LNAME VARCHAR2(100 BYTE) NOT NULL ENABLE,
	EMAIL VARCHAR2(100 BYTE) NOT NULL ENABLE,
	DOB DATE,
	LASTLOGIN TIMESTAMP (6),
	 CONSTRAINT USERS_PK PRIMARY KEY (ID)
   );

CREATE TABLE GROUPS
   (	ID NUMBER(10,0) NOT NULL ENABLE,
	NAME VARCHAR2(100 BYTE) NOT NULL ENABLE,
	DESCRIPTION VARCHAR2(200 BYTE),
	LIMIT NUMBER(10,0),
	DATECREATED TIMESTAMP (6),
	 CONSTRAINT GROUPS_PK PRIMARY KEY (ID)
);

CREATE TABLE FRIENDSHIPS
(	USERID NUMBER(10,0) NOT NULL ENABLE,
   FRIENDID NUMBER(10,0) NOT NULL ENABLE,
   APPROVED NUMBER(1,0) NOT NULL ENABLE,
   DATEAPPROVED TIMESTAMP (6) NOT NULL ENABLE,
   CONSTRAINT FRIENDSHIPS_FK1 FOREIGN KEY (USERID)
    REFERENCES USERS (ID) ENABLE,
   CONSTRAINT FRIENDSHIPS_FK2 FOREIGN KEY (FRIENDID)
    REFERENCES USERS (ID) ENABLE
);

CREATE TABLE GROUPMEMBERS
(	GROUPID NUMBER(10,0) NOT NULL ENABLE,
   USERID NUMBER(10,0) NOT NULL ENABLE,
   DATEJOINED TIMESTAMP (6) NOT NULL ENABLE,
   CONSTRAINT GROUPMEMBERS_FK1 FOREIGN KEY (GROUPID)
    REFERENCES GROUPS (ID) ENABLE,
   CONSTRAINT GROUPMEMBERS_FK2 FOREIGN KEY (USERID)
    REFERENCES USERS (ID) ENABLE
);

CREATE TABLE MESSAGES
(	ID NUMBER(10,0) NOT NULL ENABLE,
  SENDERID NUMBER(10,0) NOT NULL ENABLE,
  SUBJECT VARCHAR2(30 BYTE) NOT NULL ENABLE,
  BODY VARCHAR2(100 BYTE) NOT NULL ENABLE,
  RECIPIENTID NUMBER(10,0),
  GROUPID NUMBER(10,0),
  DATECREATED TIMESTAMP (6),
   CONSTRAINT MESSAGES_PK PRIMARY KEY (ID),
   CONSTRAINT MESSAGES_FK1 FOREIGN KEY (SENDERID)
   REFERENCES USERS (ID) ENABLE,
   CONSTRAINT MESSAGES_FK2 FOREIGN KEY (RECIPIENTID)
   REFERENCES USERS (ID) ENABLE,
   CONSTRAINT MESSAGES_FK3 FOREIGN KEY (GROUPID)
   REFERENCES GROUPS (ID) ENABLE
);

INSERT INTO USERS VALUES( 1, 'Jason' , 'Tomei', 'jtomei@gmail.com', TO_DATE('1-JAN-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 2, 'Garrett' , 'Norrell', 'gnorrell@gmail.com', TO_DATE('2-JAN-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 3, 'Jamie' , 'Mewborn', 'jmewborn@gmail.com', TO_DATE('3-JAN-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 4, 'Landon' , 'Ambriz', 'lambriz@gmail.com', TO_DATE('4-JAN-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 5, 'Monroe' , 'Midgett', 'mmidgett@gmail.com', TO_DATE('5-JAN-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 6, 'Perry' , 'Pendergraft', 'ppendergraft@gmail.com', TO_DATE('6-JAN-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 7, 'Sid' , 'Mcmullan', 'smcmullan@gmail.com', TO_DATE('7-JAN-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 8, 'Raleigh' , 'Bacote', 'rbacote@gmail.com', TO_DATE('8-JAN-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 9, 'Hugh' , 'Foster', 'hfoster@gmail.com', TO_DATE('9-JAN-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 10, 'Darell' , 'Foree', 'dforee@gmail.com', TO_DATE('10-JAN-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 11, 'Ike' , 'Keebler', 'ikeebler@gmail.com', TO_DATE('11-JAN-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 12, 'Ronnie' , 'Yu', 'ryu@gmail.com', TO_DATE('12-JAN-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 13, 'Darwin' , 'Ricks', 'dricks@gmail.com', TO_DATE('13-JAN-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 14, 'Nickolas' , 'Stayer', 'nstayer@gmail.com', TO_DATE('14-JAN-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 15, 'Bert' , 'Parkison', 'bparkison@gmail.com', TO_DATE('15-JAN-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 16, 'Vaughn' , 'Setser', 'vsetser@gmail.com', TO_DATE('16-JAN-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 17, 'Dion' , 'Souder', 'dsouder@gmail.com', TO_DATE('17-JAN-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 18, 'Shelton' , 'Sgro', 'ssgro@gmail.com', TO_DATE('18-JAN-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 19, 'Jamison' , 'Jury', 'jjury@gmail.com', TO_DATE('19-JAN-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 20, 'Lucio' , 'Watwood', 'lwatwood@gmail.com', TO_DATE('20-JAN-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 21, 'Nathanael' , 'Down', 'ndown@gmail.com', TO_DATE('21-JAN-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 22, 'Darryl' , 'Menard', 'dmenard@gmail.com', TO_DATE('22-JAN-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 23, 'Barney' , 'Clow', 'bclow@gmail.com', TO_DATE('23-JAN-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 24, 'Loyd' , 'Montrose', 'lmontrose@gmail.com', TO_DATE('24-JAN-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 25, 'Edison' , 'Greenwald', 'egreenwald@gmail.com', TO_DATE('25-JAN-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 26, 'Bruce' , 'Jiminez', 'bjiminez@gmail.com', TO_DATE('26-JAN-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 27, 'Raphael' , 'Kaur', 'rkaur@gmail.com', TO_DATE('27-JAN-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 28, 'Reynaldo' , 'Gilham', 'rgilham@gmail.com', TO_DATE('28-FEB-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 29, 'Tony' , 'Byrns', 'tbyrns@gmail.com', TO_DATE('1-FEB-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 30, 'Cameron' , 'Llanos', 'cllanos@gmail.com', TO_DATE('2-FEB-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 31, 'Lynn' , 'Gillison', 'lgillison@gmail.com', TO_DATE('3-FEB-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 32, 'Forest' , 'Raimondi', 'fraimondi@gmail.com', TO_DATE('4-FEB-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 33, 'Efren' , 'Domina', 'edomina@gmail.com', TO_DATE('5-FEB-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 34, 'Shane' , 'Nadel', 'snadel@gmail.com', TO_DATE('6-FEB-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 35, 'Darrel' , 'Luby', 'dluby@gmail.com', TO_DATE('7-FEB-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 36, 'Stanton' , 'Dixon', 'sdixon@gmail.com', TO_DATE('8-FEB-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 37, 'Johnathon' , 'Kemmerer', 'jkemmerer@gmail.com', TO_DATE('9-FEB-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 38, 'Dewey' , 'Alderson', 'dalderson@gmail.com', TO_DATE('10-FEB-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 39, 'Christoper' , 'Ackerson', 'cackerson@gmail.com', TO_DATE('11-FEB-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 40, 'Dino' , 'Antunez', 'dantunez@gmail.com', TO_DATE('12-FEB-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 41, 'Dwayne' , 'Plummer', 'dplummer@gmail.com', TO_DATE('13-FEB-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 42, 'Shaun' , 'Larabee', 'slarabee@gmail.com', TO_DATE('14-FEB-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 43, 'Lincoln' , 'Fairman', 'lfairman@gmail.com', TO_DATE('15-FEB-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 44, 'Demarcus' , 'Vannatta', 'dvannatta@gmail.com', TO_DATE('16-FEB-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 45, 'Cary' , 'Hilts', 'chilts@gmail.com', TO_DATE('17-FEB-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 46, 'Mauricio' , 'Vanvalkenburg', 'mvanvalkenburg@gmail.com', TO_DATE('18-FEB-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 47, 'Lynwood' , 'Canada', 'lcanada@gmail.com', TO_DATE('19-FEB-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 48, 'Whitney' , 'Rabalais', 'wrabalais@gmail.com', TO_DATE('20-FEB-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 49, 'Eusebio' , 'Hofmann', 'ehofmann@gmail.com', TO_DATE('21-FEB-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 50, 'Harvey' , 'Denver', 'hdenver@gmail.com', TO_DATE('22-FEB-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 51, 'Roxane' , 'Selby', 'rselby@gmail.com', TO_DATE('23-FEB-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 52, 'Nyla' , 'Hillebrand', 'nhillebrand@gmail.com', TO_DATE('24-FEB-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 53, 'Luciana' , 'Nellum', 'lnellum@gmail.com', TO_DATE('25-FEB-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 54, 'Yolando' , 'Cienfuegos', 'ycienfuegos@gmail.com', TO_DATE('26-FEB-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 55, 'Sharen' , 'Negron', 'snegron@gmail.com', TO_DATE('27-FEB-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 56, 'Mamie' , 'Clukey', 'mclukey@gmail.com', TO_DATE('28-MAR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 57, 'Lashon' , 'Glickman', 'lglickman@gmail.com', TO_DATE('1-MAR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 58, 'Adrianne' , 'Loe', 'aloe@gmail.com', TO_DATE('2-MAR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 59, 'Ophelia' , 'Eisenberg', 'oeisenberg@gmail.com', TO_DATE('3-MAR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 60, 'Kacie' , 'Callahan', 'kcallahan@gmail.com', TO_DATE('4-MAR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 61, 'Chante' , 'Gallman', 'cgallman@gmail.com', TO_DATE('5-MAR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 62, 'Francoise' , 'Barks', 'fbarks@gmail.com', TO_DATE('6-MAR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 63, 'Trinh' , 'Patricio', 'tpatricio@gmail.com', TO_DATE('7-MAR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 64, 'Mimi' , 'Flint', 'mflint@gmail.com', TO_DATE('8-MAR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 65, 'Lucila' , 'Laplante', 'llaplante@gmail.com', TO_DATE('9-MAR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 66, 'Victoria' , 'Massingale', 'vmassingale@gmail.com', TO_DATE('10-MAR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 67, 'Zofia' , 'Hufnagel', 'zhufnagel@gmail.com', TO_DATE('11-MAR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 68, 'Elida' , 'Townsel', 'etownsel@gmail.com', TO_DATE('12-MAR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 69, 'Deneen' , 'Hegarty', 'dhegarty@gmail.com', TO_DATE('13-MAR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 70, 'Alyce' , 'Masden', 'amasden@gmail.com', TO_DATE('14-MAR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 71, 'Casimira' , 'Vogelsang', 'cvogelsang@gmail.com', TO_DATE('15-MAR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 72, 'Hermina' , 'Wakeman', 'hwakeman@gmail.com', TO_DATE('16-MAR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 73, 'Mayra' , 'Towell', 'mtowell@gmail.com', TO_DATE('17-MAR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 74, 'Lynda' , 'Barbe', 'lbarbe@gmail.com', TO_DATE('18-MAR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 75, 'Lezlie' , 'Baxley', 'lbaxley@gmail.com', TO_DATE('19-MAR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 76, 'Willa' , 'Moir', 'wmoir@gmail.com', TO_DATE('20-MAR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 77, 'Deetta' , 'Wisdom', 'dwisdom@gmail.com', TO_DATE('21-MAR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 78, 'Shakira' , 'Kleiber', 'skleiber@gmail.com', TO_DATE('22-MAR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 79, 'Stacee' , 'Pon', 'spon@gmail.com', TO_DATE('23-MAR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 80, 'Kareen' , 'Chaput', 'kchaput@gmail.com', TO_DATE('24-MAR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 81, 'Chara' , 'Bonfiglio', 'cbonfiglio@gmail.com', TO_DATE('25-MAR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 82, 'Talia' , 'Chaloux', 'tchaloux@gmail.com', TO_DATE('26-MAR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 83, 'Graciela' , 'Ridings', 'gridings@gmail.com', TO_DATE('27-MAR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 84, 'Sofia' , 'Beaupre', 'sbeaupre@gmail.com', TO_DATE('28-APR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 85, 'Sadye' , 'Hughey', 'shughey@gmail.com', TO_DATE('1-APR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 86, 'Yetta' , 'Grunewald', 'ygrunewald@gmail.com', TO_DATE('2-APR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 87, 'Temika' , 'Mcquiston', 'tmcquiston@gmail.com', TO_DATE('3-APR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 88, 'Zenobia' , 'Nuttall', 'znuttall@gmail.com', TO_DATE('4-APR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 89, 'Meagan' , 'Tyer', 'mtyer@gmail.com', TO_DATE('5-APR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 90, 'Eusebia' , 'Lauterbach', 'elauterbach@gmail.com', TO_DATE('6-APR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 91, 'Ferne' , 'Haffey', 'fhaffey@gmail.com', TO_DATE('7-APR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 92, 'Melisa' , 'Finchum', 'mfinchum@gmail.com', TO_DATE('8-APR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 93, 'Destiny' , 'Durney', 'ddurney@gmail.com', TO_DATE('9-APR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 94, 'Lavon' , 'Dyer', 'ldyer@gmail.com', TO_DATE('10-APR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 95, 'Arlette' , 'Stmarie', 'astmarie@gmail.com', TO_DATE('11-APR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 96, 'Natividad' , 'Deese', 'ndeese@gmail.com', TO_DATE('12-APR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 97, 'Toccara' , 'Meas', 'tmeas@gmail.com', TO_DATE('13-APR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 98, 'Dorethea' , 'Curlin', 'dcurlin@gmail.com', TO_DATE('14-APR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 99, 'Tangela' , 'Wotton', 'twotton@gmail.com', TO_DATE('15-APR-90','DD-MON-YY'), current_timestamp);
INSERT INTO USERS VALUES( 100, 'Katerine' , 'Vanhooser', 'kvanhooser@gmail.com', TO_DATE('16-APR-90','DD-MON-YY'), current_timestamp);

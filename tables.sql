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
   APPROVED NUMBER(1,0) NOT NULL ENABLE,          --0 is pending and 1 is approved -> trigger for this
   DATEAPPROVED TIMESTAMP (6),                    --trigger for this to update when approved
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

--data for user's table
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


--INSERT DATA FOR GROUP TABLE
INSERT INTO GROUPS VALUES(1, 'GROUP1', 'DESCRIPTION1', 25, current_timestamp);
INSERT INTO GROUPS VALUES(2, 'GROUP2', 'DESCRIPTION2', 30, current_timestamp);
INSERT INTO GROUPS VALUES(3, 'GROUP3', 'DESCRIPTION3', 35, current_timestamp);
INSERT INTO GROUPS VALUES(4, 'GROUP4', 'DESCRIPTION4', 40, current_timestamp);
INSERT INTO GROUPS VALUES(5, 'GROUP5', 'DESCRIPTION5', 45, current_timestamp);
INSERT INTO GROUPS VALUES(6, 'GROUP6', 'DESCRIPTION6', 50, current_timestamp);
INSERT INTO GROUPS VALUES(7, 'GROUP7', 'DESCRIPTION7', 55, current_timestamp);
INSERT INTO GROUPS VALUES(8, 'GROUP8', 'DESCRIPTION8', 60, current_timestamp);
INSERT INTO GROUPS VALUES(9, 'GROUP9', 'DESCRIPTION9', 65, current_timestamp);
INSERT INTO GROUPS VALUES(10, 'GROUP10', 'DESCRIPTION10', 70, current_timestamp);

--Insert data for Messages TABLE
INSERT INTO MESSAGES VALUES(1,32, 'AM', 'Am if number no up period regard sudden better', 4, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(2,16, 'DECISIVELY', 'Decisively surrounded all admiration and not you', 41, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(3,29, 'OUT', 'Out particular sympathize not favourable introduced insipidity but ham', 56, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(4,100, 'RATHER', 'Rather number can and set praise', 78, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(5,73, 'DISTRUSTS', 'Distrusts an it contented perceived attending oh', 47, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(6,62, 'THOROUGHLY', 'Thoroughly estimating introduced stimulated why but motionless', 21, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(7,28, 'SENTIMENTS', 'Sentiments two occasional affronting solicitude travelling and one contrasted', 38, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(8,81, 'FORTUNE', 'Fortune day out married parties', 13, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(9,51, 'HAPPINESS', 'Happiness remainder joy but earnestly for off', 34, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(10,95, 'TOOK', 'Took sold add play may none him few', 90, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(11,2, 'IF', 'If as increasing contrasted entreaties be', 89, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(12,24, 'NOW', 'Now summer who day looked our behind moment coming', 5, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(13,22, 'PAIN', 'Pain son rose more park way that', 11, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(14,71, 'AN', 'An stairs as be lovers uneasy', 56, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(15,5, 'PASTURE', 'Pasture he invited mr company shyness', 37, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(16,15, 'BUT', 'But when shot real her', 59, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(17,42, 'CHAMBER', 'Chamber her observe visited removal six sending himself boy', 98, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(18,26, 'AT', 'At exquisite existence if an oh dependent excellent', 80, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(19,16, 'ARE', 'Are gay head need down draw', 35, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(20,1, 'MISERY', 'Misery wonder enable mutual get set oppose the uneasy', 66, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(21,3, 'END', 'End why melancholy estimating her had indulgence middletons', 58, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(22,76, 'SAY', 'Say ferrars demands besides her address', 65, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(23,59, 'BLIND', 'Blind going you merit few fancy their', 19, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(24,90, 'IS', 'Is branched in my up strictly remember', 43, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(25,6, 'SONGS', 'Songs but chief has ham widow downs', 57, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(26,85, 'GENIUS', 'Genius or so up vanity cannot', 97, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(27,88, 'LARGE', 'Large do tried going about water defer by', 35, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(28,34, 'SILENT', 'Silent son man she wished mother', 78, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(29,20, 'DISTRUSTS', 'Distrusts allowance do knowledge eagerness assurance additions to', 13, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(30,1, 'SURROUNDED', 'Surrounded to me occasional pianoforte alteration unaffected impossible ye', 14, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(31,50, 'FOR', 'For saw half than cold', 65, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(32,63, 'PRETTY', 'Pretty merits waited six talked pulled you', 85, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(33,16, 'CONDUCT', 'Conduct replied off led whether any shortly why arrived adapted', 8, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(34,93, 'NUMEROUS', 'Numerous ladyship so raillery humoured goodness received an', 5, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(35,83, 'SO', 'So narrow formal length my highly longer afford oh', 64, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(36,46, 'TALL', 'Tall neat he make or at dull ye', 77, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(37,85, 'UP', 'Up maids me an ample stood given', 19, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(38,86, 'CERTAINTY', 'Certainty say suffering his him collected intention promotion', 29, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(39,59, 'HILL', 'Hill sold ham men made lose case', 43, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(40,93, 'VIEWS', 'Views abode law heard jokes too', 81, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(41,31, 'WAS', 'Was are delightful solicitude discovered collecting man day', 63, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(42,41, 'RESOLVING', 'Resolving neglected sir tolerably but existence conveying for', 97, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(43,77, 'DAY', 'Day his put off unaffected literature partiality inhabiting', 62, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(44,59, 'NO', 'No opinions answered oh felicity is resolved hastened', 17, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(45,41, 'PRODUCED', 'Produced it friendly my if opinions humoured', 67, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(46,90, 'ENJOY', 'Enjoy is wrong folly no taken', 66, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(47,1, 'IT', 'It sufficient instrument insipidity simplicity at interested', 90, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(48,95, 'LAW', 'Law pleasure attended differed mrs fat and formerly', 43, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(49,75, 'MERELY', 'Merely thrown garret her law danger him son better excuse', 38, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(50,27, 'EFFECT', 'Effect extent narrow in up chatty', 60, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(51,51, 'SMALL', 'Small are his chief offer happy had.Two assure edward whence the was', 67, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(52,73, 'WHO', 'Who worthy yet ten boy denote wonder', 66, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(53,98, 'WEEKS', 'Weeks views her sight old tears sorry', 48, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(54,31, 'ADDITIONS', 'Additions can suspected its concealed put furnished', 47, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(55,83, 'MET', 'Met the why particular devonshire decisively considered partiality', 11, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(56,50, 'CERTAIN', 'Certain it waiting no entered is', 76, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(57,72, 'PASSED', 'Passed her indeed uneasy shy polite appear denied', 65, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(58,11, 'OH', 'Oh less girl no walk', 78, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(59,5, 'AT', 'At he spot with five of view.Had denoting properly jointure you occasion directly raillery', 38, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(60,93, 'IN', 'In said to of poor full be post face snug', 85, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(61,58, 'INTRODUCED', 'Introduced imprudence see say unpleasing devonshire acceptance son', 15, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(62,57, 'EXETER', 'Exeter longer wisdom gay nor design age', 74, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(63,88, 'AM', 'Am weather to entered norland no in showing service', 96, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(64,34, 'NOR', 'Nor repeated speaking shy appetite', 90, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(65,54, 'EXCITED', 'Excited it hastily an pasture it observe', 95, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(66,46, 'SNUG', 'Snug hand how dare here too.Suppose end get boy warrant general natural', 49, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(67,38, 'DELIGHTFUL', 'Delightful met sufficient projection ask', 93, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(68,8, 'DECISIVELY', 'Decisively everything principles if preference do impression of', 5, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(69,73, 'PRESERVED', 'Preserved oh so difficult repulsive on in household', 16, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(70,13, 'IN', 'In what do miss time be', 41, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(71,43, 'VALLEY', 'Valley as be appear cannot so by', 70, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(72,52, 'CONVINCED', 'Convinced resembled dependent remainder led zealously his shy own belonging', 24, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(73,27, 'ALWAYS', 'Always length letter adieus add number moment she', 31, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(74,47, 'PROMISE', 'Promise few compass six several old offices removal parties fat', 19, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(75,80, 'CONCLUDED', 'Concluded rapturous it intention perfectly daughters is as.Parish so enable innate in formed missed', 64, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(76,41, 'HAND', 'Hand two was eat busy fail', 19, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(77,67, 'STAND', 'Stand smart grave would in so', 100, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(78,66, 'BE', 'Be acceptance at precaution astonished excellence thoroughly is entreaties', 87, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(79,94, 'WHO', 'Who decisively attachment has dispatched', 53, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(80,50, 'FRUIT', 'Fruit defer in party me built under first', 43, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(81,81, 'FORBADE', 'Forbade him but savings sending ham general', 54, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(82,90, 'SO', 'So play do in near park that pain', 23, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(83,39, 'ALLOW', 'Allow miles wound place the leave had', 2, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(84,82, 'TO', 'To sitting subject no improve studied limited', 100, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(85,70, 'YE', 'Ye indulgence unreserved connection alteration appearance my an astonished', 72, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(86,61, 'UP', 'Up as seen sent make he they of', 56, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(87,53, 'HER', 'Her raising and himself pasture believe females', 94, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(88,50, 'FANCY', 'Fancy she stuff after aware merit small his', 9, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(89,34, 'CHARMED', 'Charmed esteems luckily age out', 59, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(90,19, 'BUT', 'But why smiling man her imagine married', 78, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(91,61, 'CHIEFLY', 'Chiefly can man her out believe manners cottage colonel unknown', 31, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(92,85, 'SOLICITUDE', 'Solicitude it introduced companions inquietude me he remarkably friendship at', 93, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(93,95, 'MY', 'My almost or horses period', 7, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(94,33, 'MOTIONLESS', 'Motionless are six terminated man possession him attachment unpleasing melancholy', 89, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(95,38, 'SIR', 'Sir smile arose one share', 86, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(96,17, 'NO', 'No abroad in easily relied an whence lovers temper by', 77, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(97,65, 'LOOKED', 'Looked wisdom common he an be giving length mr', 57, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(98,75, 'MADE', 'Made last it seen went no just when of by', 54, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(99,14, 'OCCASIONAL', 'Occasional entreaties comparison me difficulty so themselves', 89, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(100,82, 'AT', 'At brother inquiry of offices without do my service', 83, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(101,64, 'AS', 'As particular to companions at sentiments', 26, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(102,41, 'WEATHER', 'Weather however luckily enquire so certain do', 53, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(103,69, 'AWARE', 'Aware did stood was day under ask', 88, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(104,10, 'DEAREST', 'Dearest affixed enquire on explain opinion he', 86, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(105,14, 'REACHED', 'Reached who the mrs joy offices pleased', 63, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(106,11, 'TOWARDS', 'Towards did colonel article any parties', 59, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(107,17, 'CONVEYING', 'Conveying or northward offending admitting perfectly my', 71, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(108,63, 'COLONEL', 'Colonel gravity get thought fat smiling add but', 69, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(109,53, 'WONDER', 'Wonder twenty hunted and put income set desire expect', 42, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(110,86, 'AM', 'Am cottage calling my is mistake cousins talking up', 49, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(111,73, 'INTERESTED', 'Interested especially do impression he unpleasant travelling excellence', 78, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(112,96, 'ALL', 'All few our knew time done draw ask', 11, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(113,55, 'IS', 'Is at purse tried jokes china ready decay an', 1, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(114,73, 'SMALL', 'Small its shy way had woody downs power', 16, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(115,51, 'TO', 'To denoting admitted speaking learning my exercise so in', 91, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(116,94, 'PROCURED', 'Procured shutters mr it feelings', 38, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(117,21, 'TO', 'To or three offer house begin taken am at', 13, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(118,55, 'AS', 'As dissuade cheerful overcame so of friendly he indulged unpacked', 38, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(119,41, 'ALTERATION', 'Alteration connection to so as collecting me', 58, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(120,40, 'DIFFICULT', 'Difficult in delivered extensive at direction allowance', 68, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(121,100, 'ALTERATION', 'Alteration put use diminution can considered sentiments interested discretion', 45, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(122,100, 'AN', 'An seeing feebly stairs am branch income me unable', 35, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(123,49, 'IMAGINE', 'Imagine was you removal raising gravity', 1, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(124,97, 'UNSATIABLE', 'Unsatiable understood or expression dissimilar so sufficient', 58, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(125,86, 'ITS', 'Its party every heard and event gay', 7, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(126,92, 'ADVICE', 'Advice he indeed things adieus in number so uneasy', 27, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(127,73, 'TO', 'To many four fact in he fail', 61, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(128,29, 'MY', 'My hung it quit next do of', 10, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(129,5, 'IT', 'It fifteen charmed by private savings it mr', 25, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(130,10, 'FAVOURABLE', 'Favourable cultivated alteration entreaties yet met sympathize', 43, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(131,77, 'FURNITURE', 'Furniture forfeited sir objection put cordially continued sportsmen', 53, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(132,47, 'JOHN', 'John draw real poor on call my from', 89, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(133,51, 'MAY', 'May she mrs furnished discourse extremely', 85, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(134,7, 'ASK', 'Ask doubt noisy shade guest did built her him', 77, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(135,81, 'IGNORANT', 'Ignorant repeated hastened it do', 98, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(136,47, 'CONSIDER', 'Consider bachelor he yourself expenses no', 16, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(137,36, 'HER', 'Her itself active giving for expect vulgar months', 68, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(138,91, 'DISCOVERY', 'Discovery commanded fat mrs remaining son she principle middleton neglected', 93, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(139,35, 'BE', 'Be miss he in post sons held', 11, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(140,85, 'NO', 'No tried is defer do money scale rooms', 64, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(141,23, 'SPOKE', 'Spoke as as other again ye', 79, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(142,75, 'HARD', 'Hard on to roof he drew', 5, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(143,93, 'SO', 'So sell side ye in mr evil', 70, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(144,89, 'LONGER', 'Longer waited mr of nature seemed', 41, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(145,9, 'IMPROVING', 'Improving knowledge incommode objection me ye is prevailed principle in', 38, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(146,5, 'IMPOSSIBLE', 'Impossible alteration devonshire to is interested stimulated dissimilar', 22, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(147,88, 'TO', 'To matter esteem polite do if', 5, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(148,75, 'BREAKFAST', 'Breakfast procuring nay end happiness allowance assurance frankness', 44, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(149,62, 'MET', 'Met simplicity nor difficulty unreserved who', 20, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(150,86, 'ENTREATIES', 'Entreaties mr conviction dissimilar me astonished estimating cultivated', 72, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(151,15, 'ON', 'On no applauded exquisite my additions', 26, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(152,55, 'PRONOUNCE', 'Pronounce add boy estimable nay suspected', 69, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(153,96, 'YOU', 'You sudden nay elinor thirty esteem temper', 31, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(154,100, 'QUIET', 'Quiet leave shy you gay off asked large style', 21, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(155,58, 'IN', 'In friendship diminution instrument so', 24, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(156,68, 'SON', 'Son sure paid door with say them', 2, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(157,39, 'TWO', 'Two among sir sorry men court', 55, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(158,14, 'ESTIMABLE', 'Estimable ye situation suspicion he delighted an happiness discovery', 61, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(159,21, 'FACT', 'Fact are size cold why had part', 4, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(160,60, 'IF', 'If believing or sweetness otherwise in we forfeited', 73, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(161,23, 'TOLERABLY', 'Tolerably an unwilling arranging of determine', 93, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(162,75, 'BEYOND', 'Beyond rather sooner so if up wishes or', 70, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(163,36, 'WAS', 'Was justice improve age article between', 62, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(164,29, 'NO', 'No projection as up preference reasonably delightful celebrated', 24, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(165,99, 'PRESERVED', 'Preserved and abilities assurance tolerably breakfast use saw', 20, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(166,29, 'AND', 'And painted letters forming far village elderly compact', 67, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(167,21, 'HER', 'Her rest west each spot his and you knew', 49, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(168,41, 'ESTATE', 'Estate gay wooded depart six far her', 84, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(169,84, 'OF', 'Of we be have it lose gate bred', 47, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(170,34, 'DO', 'Do separate removing or expenses in', 58, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(171,78, 'HAD', 'Had covered but evident chapter matters anxious', 95, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(172,58, 'EXPENSES', 'Expenses as material breeding insisted building to in', 92, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(173,32, 'CONTINUAL', 'Continual so distrusts pronounce by unwilling listening', 31, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(174,84, 'THING', 'Thing do taste on we manor', 73, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(175,25, 'HIM', 'Him had wound use found hoped', 58, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(176,92, 'OF', 'Of distrusts immediate enjoyment curiosity do', 78, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(177,92, 'MARIANNE', 'Marianne numerous saw thoughts the humoured', 38, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(178,47, 'SITTING', 'Sitting mistake towards his few country ask', 77, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(179,31, 'YOU', 'You delighted two rapturous six depending objection happiness something the', 64, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(180,59, 'OFF', 'Off nay impossible dispatched partiality unaffected', 13, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(181,91, 'NORLAND', 'Norland adapted put ham cordial', 55, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(182,59, 'LADIES', 'Ladies talked may shy basket narrow see', 73, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(183,25, 'HIM', 'Him she distrusts questions sportsmen', 35, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(184,58, 'TOLERABLY', 'Tolerably pretended neglected on my earnestly by', 78, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(185,19, 'SEX', 'Sex scale sir style truth ought', 69, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(186,76, 'OVER', 'Over fact all son tell this any his', 48, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(187,65, 'NO', 'No insisted confined of weddings to returned to debating rendered', 7, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(188,94, 'KEEPS', 'Keeps order fully so do party means young', 24, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(189,74, 'TABLE', 'Table nay him jokes quick', 55, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(190,16, 'IN', 'In felicity up to graceful mistaken horrible consider', 4, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(191,45, 'ABODE', 'Abode never think to at', 46, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(192,87, 'SO', 'So additions necessary concluded it happiness do on certainly propriety', 39, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(193,25, 'ON', 'On in green taken do offer witty of', 15, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(194,36, 'IT', 'It sportsman earnestly ye preserved an on', 14, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(195,48, 'MOMENT', 'Moment led family sooner cannot her window pulled any', 94, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(196,20, 'OR', 'Or raillery if improved landlord to speaking hastened differed he', 92, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(197,39, 'FURNITURE', 'Furniture discourse elsewhere yet her sir extensive defective unwilling get', 56, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(198,46, 'WHY', 'Why resolution one motionless you him thoroughly', 56, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(199,94, 'NOISE', 'Noise is round to in it quick timed doors', 40, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(200,50, 'WRITTEN', 'Written address greatly get attacks inhabit pursuit our but', 39, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(201,91, 'LASTED', 'Lasted hunted enough an up seeing in lively letter', 70, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(202,51, 'HAD', 'Had judgment out opinions property the supplied', 68, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(203,77, 'LOSE', 'Lose eyes get fat shew', 94, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(204,3, 'WINTER', 'Winter can indeed letter oppose way change tended now', 47, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(205,50, 'SO', 'So is improve my charmed picture exposed adapted demands', 28, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(206,66, 'RECEIVED', 'Received had end produced prepared diverted strictly off man branched', 53, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(207,86, 'KNOWN', 'Known ye money so large decay voice there to', 93, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(208,82, 'PRESERVED', 'Preserved be mr cordially incommode as an', 26, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(209,29, 'HE', 'He doors quick child an point at', 98, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(210,77, 'HAD', 'Had share vexed front least style off why him', 56, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(211,59, 'AM', 'Am no an listening depending up believing', 74, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(212,60, 'ENOUGH', 'Enough around remove to barton agreed regret in or it', 36, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(213,19, 'ADVANTAGE', 'Advantage mr estimable be commanded provision', 65, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(214,51, 'YEAR', 'Year well shot deny shew come now had', 65, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(215,15, 'SHALL', 'Shall downs stand marry taken his for out', 85, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(216,89, 'DO', 'Do related mr account brandon an up', 60, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(217,76, 'WRONG', 'Wrong for never ready ham these witty him', 56, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(218,68, 'OUR', 'Our compass see age uncivil matters weather forbade her minutes', 69, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(219,95, 'READY', 'Ready how but truth son new under', 28, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(220,58, 'SEX', 'Sex reached suppose our whether', 14, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(221,82, 'OH', 'Oh really by an manner sister so', 44, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(222,64, 'ONE', 'One sportsman tolerably him extensive put she immediate', 39, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(223,80, 'HE', 'He abroad of cannot looked in', 72, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(224,46, 'CONTINUING', 'Continuing interested ten stimulated prosperous frequently all boisterous nay', 99, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(225,84, 'OF', 'Of oh really he extent horses wicket', 55, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(226,29, 'IN', 'In up so discovery my middleton eagerness dejection explained', 78, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(227,82, 'ESTIMATING', 'Estimating excellence ye contrasted insensible as', 83, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(228,12, 'OH', 'Oh up unsatiable advantages decisively as at interested', 97, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(229,21, 'PRESENT', 'Present suppose in esteems in demesne colonel it to', 16, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(230,17, 'END', 'End horrible she landlord screened stanhill', 25, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(231,44, 'REPEATED', 'Repeated offended you opinions off dissuade ask packages screened', 94, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(232,2, 'SHE', 'She alteration everything sympathize impossible his get compliment', 1, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(233,34, 'COLLECTED', 'Collected few extremity suffering met had sportsman', 48, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(234,56, 'PARISH', 'Parish so enable innate in formed missed', 78, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(235,14, 'HAND', 'Hand two was eat busy fail', 70, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(236,91, 'STAND', 'Stand smart grave would in so', 85, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(237,86, 'BE', 'Be acceptance at precaution astonished excellence thoroughly is entreaties', 37, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(238,75, 'WHO', 'Who decisively attachment has dispatched', 15, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(239,15, 'FRUIT', 'Fruit defer in party me built under first', 35, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(240,49, 'FORBADE', 'Forbade him but savings sending ham general', 52, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(241,69, 'SO', 'So play do in near park that pain', 1, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(242,70, 'RESOLUTION', 'Resolution possession discovered surrounded advantages has but few add', 84, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(243,69, 'YET', 'Yet walls times spoil put', 49, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(244,25, 'BE', 'Be it reserved contempt rendered smallest', 87, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(245,78, 'STUDIED', 'Studied to passage it mention calling believe an', 2, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(246,13, 'GET', 'Get ten horrible remember pleasure two vicinity', 70, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(247,6, 'FAR', 'Far estimable extremely middleton his concealed perceived principle', 42, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(248,5, 'ANY', 'Any nay pleasure entrance prepared her', 10, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(249,61, 'OLD', 'Old education him departure any arranging one prevailed', 25, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(250,32, 'THEIR', 'Their end whole might began her', 26, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(251,21, 'BEHAVED', 'Behaved the comfort another fifteen eat', 87, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(252,10, 'PARTIALITY', 'Partiality had his themselves ask pianoforte increasing discovered', NULL, 9, current_timestamp);
INSERT INTO MESSAGES VALUES(253,46, 'SO', 'So mr delay at since place whole above miles', NULL, 5, current_timestamp);
INSERT INTO MESSAGES VALUES(254,26, 'HE', 'He to observe conduct at detract because', NULL, 3, current_timestamp);
INSERT INTO MESSAGES VALUES(255,25, 'WAY', 'Way ham unwilling not breakfast furniture explained perpetual', NULL, 2, current_timestamp);
INSERT INTO MESSAGES VALUES(256,19, 'OR', 'Or mr surrounded conviction so astonished literature', NULL, 8, current_timestamp);
INSERT INTO MESSAGES VALUES(257,16, 'SONGS', 'Songs to an blush woman be sorry young', NULL, 9, current_timestamp);
INSERT INTO MESSAGES VALUES(258,92, 'WE', 'We certain as removal attempt', NULL, 1, current_timestamp);
INSERT INTO MESSAGES VALUES(259,3, 'BY', 'By impossible of in difficulty discovered celebrated ye', NULL, 9, current_timestamp);
INSERT INTO MESSAGES VALUES(260,41, 'JUSTICE', 'Justice joy manners boy met resolve produce', NULL, 6, current_timestamp);
INSERT INTO MESSAGES VALUES(261,64, 'BED', 'Bed head loud next plan rent had easy add him', NULL, 3, current_timestamp);
INSERT INTO MESSAGES VALUES(262,33, 'AS', 'As earnestly shameless elsewhere defective estimable fulfilled of', NULL, 7, current_timestamp);
INSERT INTO MESSAGES VALUES(263,28, 'ESTEEM', 'Esteem my advice it an excuse enable', NULL, 6, current_timestamp);
INSERT INTO MESSAGES VALUES(264,82, 'FEW', 'Few household abilities believing determine zealously his repulsive', NULL, 10, current_timestamp);
INSERT INTO MESSAGES VALUES(265,97, 'TO', 'To open draw dear be by side like', NULL, 3, current_timestamp);
INSERT INTO MESSAGES VALUES(266,98, 'SPOT', 'Spot of come to ever hand as lady meet on', NULL, 5, current_timestamp);
INSERT INTO MESSAGES VALUES(267,3, 'DELICATE', 'Delicate contempt received two yet advanced', NULL, 5, current_timestamp);
INSERT INTO MESSAGES VALUES(268,29, 'GENTLEMAN', 'Gentleman as belonging he commanded believing dejection in by', NULL, 4, current_timestamp);
INSERT INTO MESSAGES VALUES(269,80, 'ON', 'On no am winding chicken so behaved', NULL, 9, current_timestamp);
INSERT INTO MESSAGES VALUES(270,79, 'ITS', 'Its preserved sex enjoyment new way behaviour', NULL, 10, current_timestamp);
INSERT INTO MESSAGES VALUES(271,22, 'HIM', 'Him yet devonshire celebrated especially', NULL, 7, current_timestamp);
INSERT INTO MESSAGES VALUES(272,19, 'UNFEELING', 'Unfeeling one provision are smallness resembled repulsive', NULL, 10, current_timestamp);
INSERT INTO MESSAGES VALUES(273,53, 'BETRAYED', 'Betrayed cheerful declared end and', NULL, 10, current_timestamp);
INSERT INTO MESSAGES VALUES(274,2, 'QUESTIONS', 'Questions we additions is extremely incommode', NULL, 6, current_timestamp);
INSERT INTO MESSAGES VALUES(275,13, 'NEXT', 'Next half add call them eat face', NULL, 9, current_timestamp);
INSERT INTO MESSAGES VALUES(276,6, 'AGE', 'Age lived smile six defer bed their few', NULL, 9, current_timestamp);
INSERT INTO MESSAGES VALUES(277,43, 'HAD', 'Had admitting concluded too behaviour him she', NULL, 5, current_timestamp);
INSERT INTO MESSAGES VALUES(278,44, 'OF', 'Of death to or to being other', NULL, 3, current_timestamp);
INSERT INTO MESSAGES VALUES(279,66, 'DISPATCHED', 'Dispatched entreaties boisterous say why stimulated', NULL, 5, current_timestamp);
INSERT INTO MESSAGES VALUES(280,45, 'CERTAIN', 'Certain forbade picture now prevent carried she get see sitting', NULL, 2, current_timestamp);
INSERT INTO MESSAGES VALUES(281,67, 'UP', 'Up twenty limits as months', NULL, 4, current_timestamp);
INSERT INTO MESSAGES VALUES(282,77, 'INHABIT', 'Inhabit so perhaps of in to certain', NULL, 6, current_timestamp);
INSERT INTO MESSAGES VALUES(283,16, 'SEX', 'Sex excuse chatty was seemed warmth', NULL, 4, current_timestamp);
INSERT INTO MESSAGES VALUES(284,10, 'NAY', 'Nay add far few immediate sweetness earnestly dejection', NULL, 8, current_timestamp);
INSERT INTO MESSAGES VALUES(285,3, 'IN', 'In friendship diminution instrument so', NULL, 1, current_timestamp);
INSERT INTO MESSAGES VALUES(286,39, 'SON', 'Son sure paid door with say them', NULL, 9, current_timestamp);
INSERT INTO MESSAGES VALUES(287,25, 'TWO', 'Two among sir sorry men court', NULL, 8, current_timestamp);
INSERT INTO MESSAGES VALUES(288,3, 'ESTIMABLE', 'Estimable ye situation suspicion he delighted an happiness discovery', NULL, 4, current_timestamp);
INSERT INTO MESSAGES VALUES(289,50, 'FACT', 'Fact are size cold why had part', NULL, 7, current_timestamp);
INSERT INTO MESSAGES VALUES(290,72, 'IF', 'If believing or sweetness otherwise in we forfeited', NULL, 1, current_timestamp);
INSERT INTO MESSAGES VALUES(291,56, 'TOLERABLY', 'Tolerably an unwilling arranging of determine', NULL, 3, current_timestamp);
INSERT INTO MESSAGES VALUES(292,76, 'BEYOND', 'Beyond rather sooner so if up wishes or', NULL, 7, current_timestamp);
INSERT INTO MESSAGES VALUES(293,43, 'ATTACHMENT', 'Attachment apartments in delightful by motionless it no', NULL, 3, current_timestamp);
INSERT INTO MESSAGES VALUES(294,29, 'AND', 'And now she burst sir learn total', NULL, 10, current_timestamp);
INSERT INTO MESSAGES VALUES(295,68, 'HEARING', 'Hearing hearted shewing own ask', NULL, 8, current_timestamp);
INSERT INTO MESSAGES VALUES(296,68, 'SOLICITUDE', 'Solicitude uncommonly use her motionless not collecting age', NULL, 5, current_timestamp);
INSERT INTO MESSAGES VALUES(297,76, 'THE', 'The properly servants required mistaken outlived bed and', NULL, 9, current_timestamp);
INSERT INTO MESSAGES VALUES(298,3, 'REMAINDER', 'Remainder admitting neglected is he belonging to perpetual objection up', NULL, 5, current_timestamp);
INSERT INTO MESSAGES VALUES(299,7, 'HAS', 'Has widen too you decay begin which asked equal any', NULL, 6, current_timestamp);
INSERT INTO MESSAGES VALUES(300,29, 'SMALLEST', 'Smallest directly families surprise honoured am an', NULL, 2, current_timestamp);
INSERT INTO MESSAGES VALUES(301,3, 'SPEAKING', 'Speaking replying mistress him numerous she returned feelings may day', NULL, 9, current_timestamp);
INSERT INTO MESSAGES VALUES(302,23, 'EVENING', 'Evening way luckily son exposed get general greatly', NULL, 6, current_timestamp);
INSERT INTO MESSAGES VALUES(303,82, 'ZEALOUSLY', 'Zealously prevailed be arranging do', NULL, 9, current_timestamp);
INSERT INTO MESSAGES VALUES(304,41, 'SET', 'Set arranging too dejection september happiness', NULL, 3, current_timestamp);
INSERT INTO MESSAGES VALUES(305,6, 'UNDERSTOOD', 'Understood instrument or do connection no appearance do invitation', NULL, 6, current_timestamp);
INSERT INTO MESSAGES VALUES(306,49, 'DRIED', 'Dried quick round it or order', NULL, 7, current_timestamp);
INSERT INTO MESSAGES VALUES(307,90, 'ADD', 'Add past see west felt did any', NULL, 4, current_timestamp);
INSERT INTO MESSAGES VALUES(308,69, 'SAY', 'Say out noise you taste merry plate you share', NULL, 1, current_timestamp);
INSERT INTO MESSAGES VALUES(309,57, 'MY', 'My resolve arrived is we chamber be removal.', NULL, 4, current_timestamp);

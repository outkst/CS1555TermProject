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
INSERT INTO MESSAGES VALUES(1,75, 'AM', 'Am if number no up period regard sudden better', 63, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(2,87, 'DECISIVELY', 'Decisively surrounded all admiration and not you', 25, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(3,45, 'OUT', 'Out particular sympathize not favourable introduced insipidity but ham', 12, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(4,54, 'RATHER', 'Rather number can and set praise', 55, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(5,77, 'DISTRUSTS', 'Distrusts an it contented perceived attending oh', 75, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(6,41, 'THOROUGHLY', 'Thoroughly estimating introduced stimulated why but motionless', 75, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(7,64, 'SENTIMENTS', 'Sentiments two occasional affronting solicitude travelling and one contrasted', 49, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(8,69, 'FORTUNE', 'Fortune day out married parties', 17, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(9,98, 'HAPPINESS', 'Happiness remainder joy but earnestly for off', 8, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(10,60, 'TOOK', 'Took sold add play may none him few', 6, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(11,74, 'IF', 'If as increasing contrasted entreaties be', 95, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(12,91, 'NOW', 'Now summer who day looked our behind moment coming', 9, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(13,25, 'PAIN', 'Pain son rose more park way that', 61, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(14,75, 'AN', 'An stairs as be lovers uneasy', 32, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(15,54, 'PASTURE', 'Pasture he invited mr company shyness', 16, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(16,92, 'BUT', 'But when shot real her', 7, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(17,83, 'CHAMBER', 'Chamber her observe visited removal six sending himself boy', 53, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(18,33, 'AT', 'At exquisite existence if an oh dependent excellent', 46, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(19,96, 'ARE', 'Are gay head need down draw', 50, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(20,72, 'MISERY', 'Misery wonder enable mutual get set oppose the uneasy', 4, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(21,33, 'END', 'End why melancholy estimating her had indulgence middletons', 3, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(22,3, 'SAY', 'Say ferrars demands besides her address', 62, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(23,31, 'BLIND', 'Blind going you merit few fancy their', 87, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(24,45, 'IS', 'Is branched in my up strictly remember', 87, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(25,57, 'SONGS', 'Songs but chief has ham widow downs', 5, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(26,53, 'GENIUS', 'Genius or so up vanity cannot', 41, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(27,38, 'LARGE', 'Large do tried going about water defer by', 81, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(28,74, 'SILENT', 'Silent son man she wished mother', 19, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(29,67, 'DISTRUSTS', 'Distrusts allowance do knowledge eagerness assurance additions to', 30, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(30,47, 'SURROUNDED', 'Surrounded to me occasional pianoforte alteration unaffected impossible ye', 78, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(31,81, 'FOR', 'For saw half than cold', 29, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(32,94, 'PRETTY', 'Pretty merits waited six talked pulled you', 97, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(33,25, 'CONDUCT', 'Conduct replied off led whether any shortly why arrived adapted', 30, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(34,33, 'NUMEROUS', 'Numerous ladyship so raillery humoured goodness received an', 36, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(35,8, 'SO', 'So narrow formal length my highly longer afford oh', 67, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(36,19, 'TALL', 'Tall neat he make or at dull ye', 12, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(37,21, 'UP', 'Up maids me an ample stood given', 79, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(38,59, 'CERTAINTY', 'Certainty say suffering his him collected intention promotion', 10, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(39,79, 'HILL', 'Hill sold ham men made lose case', 10, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(40,82, 'VIEWS', 'Views abode law heard jokes too', 86, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(41,72, 'WAS', 'Was are delightful solicitude discovered collecting man day', 43, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(42,5, 'RESOLVING', 'Resolving neglected sir tolerably but existence conveying for', 56, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(43,11, 'DAY', 'Day his put off unaffected literature partiality inhabiting', 34, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(44,39, 'NO', 'No opinions answered oh felicity is resolved hastened', 32, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(45,38, 'PRODUCED', 'Produced it friendly my if opinions humoured', 5, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(46,35, 'ENJOY', 'Enjoy is wrong folly no taken', 20, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(47,36, 'IT', 'It sufficient instrument insipidity simplicity at interested', 79, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(48,67, 'LAW', 'Law pleasure attended differed mrs fat and formerly', 93, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(49,94, 'MERELY', 'Merely thrown garret her law danger him son better excuse', 90, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(50,15, 'EFFECT', 'Effect extent narrow in up chatty', 77, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(51,46, 'SMALL', 'Small are his chief offer happy had.Two assure edward whence the was', 27, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(52,53, 'WHO', 'Who worthy yet ten boy denote wonder', 7, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(53,99, 'WEEKS', 'Weeks views her sight old tears sorry', 58, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(54,72, 'ADDITIONS', 'Additions can suspected its concealed put furnished', 78, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(55,58, 'MET', 'Met the why particular devonshire decisively considered partiality', 90, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(56,64, 'CERTAIN', 'Certain it waiting no entered is', 6, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(57,28, 'PASSED', 'Passed her indeed uneasy shy polite appear denied', 95, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(58,19, 'OH', 'Oh less girl no walk', 10, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(59,87, 'AT', 'At he spot with five of view.Had denoting properly jointure you occasion directly raillery', 97, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(60,45, 'IN', 'In said to of poor full be post face snug', 59, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(61,79, 'INTRODUCED', 'Introduced imprudence see say unpleasing devonshire acceptance son', 15, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(62,3, 'EXETER', 'Exeter longer wisdom gay nor design age', 47, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(63,20, 'AM', 'Am weather to entered norland no in showing service', 33, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(64,32, 'NOR', 'Nor repeated speaking shy appetite', 67, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(65,73, 'EXCITED', 'Excited it hastily an pasture it observe', 33, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(66,34, 'SNUG', 'Snug hand how dare here too.Suppose end get boy warrant general natural', 57, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(67,10, 'DELIGHTFUL', 'Delightful met sufficient projection ask', 88, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(68,97, 'DECISIVELY', 'Decisively everything principles if preference do impression of', 21, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(69,55, 'PRESERVED', 'Preserved oh so difficult repulsive on in household', 39, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(70,88, 'IN', 'In what do miss time be', 55, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(71,72, 'VALLEY', 'Valley as be appear cannot so by', 15, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(72,46, 'CONVINCED', 'Convinced resembled dependent remainder led zealously his shy own belonging', 54, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(73,98, 'ALWAYS', 'Always length letter adieus add number moment she', 7, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(74,71, 'PROMISE', 'Promise few compass six several old offices removal parties fat', 91, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(75,73, 'CONCLUDED', 'Concluded rapturous it intention perfectly daughters is as.Parish so enable innate in formed missed', 81, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(76,27, 'HAND', 'Hand two was eat busy fail', 95, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(77,92, 'STAND', 'Stand smart grave would in so', 85, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(78,11, 'BE', 'Be acceptance at precaution astonished excellence thoroughly is entreaties', 13, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(79,21, 'WHO', 'Who decisively attachment has dispatched', 25, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(80,76, 'FRUIT', 'Fruit defer in party me built under first', 32, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(81,51, 'FORBADE', 'Forbade him but savings sending ham general', 30, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(82,24, 'SO', 'So play do in near park that pain', 22, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(83,4, 'ALLOW', 'Allow miles wound place the leave had', 36, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(84,29, 'TO', 'To sitting subject no improve studied limited', 96, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(85,27, 'YE', 'Ye indulgence unreserved connection alteration appearance my an astonished', 24, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(86,62, 'UP', 'Up as seen sent make he they of', 11, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(87,45, 'HER', 'Her raising and himself pasture believe females', 99, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(88,76, 'FANCY', 'Fancy she stuff after aware merit small his', 46, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(89,12, 'CHARMED', 'Charmed esteems luckily age out', 98, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(90,54, 'BUT', 'But why smiling man her imagine married', 50, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(91,85, 'CHIEFLY', 'Chiefly can man her out believe manners cottage colonel unknown', 31, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(92,96, 'SOLICITUDE', 'Solicitude it introduced companions inquietude me he remarkably friendship at', 12, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(93,27, 'MY', 'My almost or horses period', 79, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(94,78, 'MOTIONLESS', 'Motionless are six terminated man possession him attachment unpleasing melancholy', 85, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(95,63, 'SIR', 'Sir smile arose one share', 25, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(96,75, 'NO', 'No abroad in easily relied an whence lovers temper by', 95, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(97,29, 'LOOKED', 'Looked wisdom common he an be giving length mr', 3, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(98,85, 'MADE', 'Made last it seen went no just when of by', 13, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(99,4, 'OCCASIONAL', 'Occasional entreaties comparison me difficulty so themselves', 29, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(100,29, 'AT', 'At brother inquiry of offices without do my service', 89, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(101,100, 'AS', 'As particular to companions at sentiments', 10, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(102,49, 'WEATHER', 'Weather however luckily enquire so certain do', 77, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(103,27, 'AWARE', 'Aware did stood was day under ask', 97, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(104,33, 'DEAREST', 'Dearest affixed enquire on explain opinion he', 40, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(105,78, 'REACHED', 'Reached who the mrs joy offices pleased', 98, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(106,84, 'TOWARDS', 'Towards did colonel article any parties', 42, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(107,55, 'CONVEYING', 'Conveying or northward offending admitting perfectly my', 78, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(108,81, 'COLONEL', 'Colonel gravity get thought fat smiling add but', 50, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(109,41, 'WONDER', 'Wonder twenty hunted and put income set desire expect', 48, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(110,96, 'AM', 'Am cottage calling my is mistake cousins talking up', 21, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(111,92, 'INTERESTED', 'Interested especially do impression he unpleasant travelling excellence', 50, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(112,9, 'ALL', 'All few our knew time done draw ask', 23, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(113,88, 'IS', 'Is at purse tried jokes china ready decay an', 53, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(114,50, 'SMALL', 'Small its shy way had woody downs power', 7, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(115,65, 'TO', 'To denoting admitted speaking learning my exercise so in', 84, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(116,78, 'PROCURED', 'Procured shutters mr it feelings', 44, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(117,8, 'TO', 'To or three offer house begin taken am at', 83, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(118,93, 'AS', 'As dissuade cheerful overcame so of friendly he indulged unpacked', 44, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(119,58, 'ALTERATION', 'Alteration connection to so as collecting me', 89, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(120,100, 'DIFFICULT', 'Difficult in delivered extensive at direction allowance', 42, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(121,32, 'ALTERATION', 'Alteration put use diminution can considered sentiments interested discretion', 49, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(122,34, 'AN', 'An seeing feebly stairs am branch income me unable', 23, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(123,21, 'IMAGINE', 'Imagine was you removal raising gravity', 37, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(124,100, 'UNSATIABLE', 'Unsatiable understood or expression dissimilar so sufficient', 84, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(125,85, 'ITS', 'Its party every heard and event gay', 27, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(126,37, 'ADVICE', 'Advice he indeed things adieus in number so uneasy', 73, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(127,76, 'TO', 'To many four fact in he fail', 75, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(128,24, 'MY', 'My hung it quit next do of', 46, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(129,85, 'IT', 'It fifteen charmed by private savings it mr', 49, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(130,14, 'FAVOURABLE', 'Favourable cultivated alteration entreaties yet met sympathize', 16, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(131,31, 'FURNITURE', 'Furniture forfeited sir objection put cordially continued sportsmen', 55, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(132,61, 'JOHN', 'John draw real poor on call my from', 22, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(133,53, 'MAY', 'May she mrs furnished discourse extremely', 24, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(134,32, 'ASK', 'Ask doubt noisy shade guest did built her him', 46, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(135,18, 'IGNORANT', 'Ignorant repeated hastened it do', 27, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(136,91, 'CONSIDER', 'Consider bachelor he yourself expenses no', 54, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(137,51, 'HER', 'Her itself active giving for expect vulgar months', 12, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(138,2, 'DISCOVERY', 'Discovery commanded fat mrs remaining son she principle middleton neglected', 39, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(139,91, 'BE', 'Be miss he in post sons held', 38, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(140,50, 'NO', 'No tried is defer do money scale rooms', 15, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(141,61, 'SPOKE', 'Spoke as as other again ye', 24, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(142,99, 'HARD', 'Hard on to roof he drew', 23, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(143,53, 'SO', 'So sell side ye in mr evil', 91, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(144,5, 'LONGER', 'Longer waited mr of nature seemed', 15, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(145,1, 'IMPROVING', 'Improving knowledge incommode objection me ye is prevailed principle in', 57, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(146,64, 'IMPOSSIBLE', 'Impossible alteration devonshire to is interested stimulated dissimilar', 87, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(147,60, 'TO', 'To matter esteem polite do if', 63, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(148,61, 'BREAKFAST', 'Breakfast procuring nay end happiness allowance assurance frankness', 28, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(149,15, 'MET', 'Met simplicity nor difficulty unreserved who', 59, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(150,36, 'ENTREATIES', 'Entreaties mr conviction dissimilar me astonished estimating cultivated', 89, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(151,88, 'ON', 'On no applauded exquisite my additions', 10, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(152,24, 'PRONOUNCE', 'Pronounce add boy estimable nay suspected', 77, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(153,51, 'YOU', 'You sudden nay elinor thirty esteem temper', 61, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(154,24, 'QUIET', 'Quiet leave shy you gay off asked large style', 98, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(155,85, 'IN', 'In friendship diminution instrument so', 78, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(156,26, 'SON', 'Son sure paid door with say them', 67, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(157,83, 'TWO', 'Two among sir sorry men court', 68, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(158,21, 'ESTIMABLE', 'Estimable ye situation suspicion he delighted an happiness discovery', 86, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(159,3, 'FACT', 'Fact are size cold why had part', 5, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(160,7, 'IF', 'If believing or sweetness otherwise in we forfeited', 57, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(161,69, 'TOLERABLY', 'Tolerably an unwilling arranging of determine', 13, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(162,38, 'BEYOND', 'Beyond rather sooner so if up wishes or', 77, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(163,99, 'WAS', 'Was justice improve age article between', 16, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(164,36, 'NO', 'No projection as up preference reasonably delightful celebrated', 50, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(165,33, 'PRESERVED', 'Preserved and abilities assurance tolerably breakfast use saw', 13, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(166,94, 'AND', 'And painted letters forming far village elderly compact', 43, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(167,72, 'HER', 'Her rest west each spot his and you knew', 69, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(168,16, 'ESTATE', 'Estate gay wooded depart six far her', 63, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(169,61, 'OF', 'Of we be have it lose gate bred', 5, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(170,83, 'DO', 'Do separate removing or expenses in', 49, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(171,73, 'HAD', 'Had covered but evident chapter matters anxious', 20, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(172,18, 'EXPENSES', 'Expenses as material breeding insisted building to in', 77, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(173,62, 'CONTINUAL', 'Continual so distrusts pronounce by unwilling listening', 44, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(174,86, 'THING', 'Thing do taste on we manor', 60, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(175,59, 'HIM', 'Him had wound use found hoped', 48, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(176,84, 'OF', 'Of distrusts immediate enjoyment curiosity do', 54, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(177,73, 'MARIANNE', 'Marianne numerous saw thoughts the humoured', 61, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(178,77, 'SITTING', 'Sitting mistake towards his few country ask', 51, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(179,80, 'YOU', 'You delighted two rapturous six depending objection happiness something the', 40, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(180,63, 'OFF', 'Off nay impossible dispatched partiality unaffected', 67, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(181,34, 'NORLAND', 'Norland adapted put ham cordial', 92, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(182,70, 'LADIES', 'Ladies talked may shy basket narrow see', 14, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(183,91, 'HIM', 'Him she distrusts questions sportsmen', 46, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(184,43, 'TOLERABLY', 'Tolerably pretended neglected on my earnestly by', 18, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(185,93, 'SEX', 'Sex scale sir style truth ought', 75, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(186,4, 'OVER', 'Over fact all son tell this any his', 96, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(187,93, 'NO', 'No insisted confined of weddings to returned to debating rendered', 46, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(188,64, 'KEEPS', 'Keeps order fully so do party means young', 29, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(189,60, 'TABLE', 'Table nay him jokes quick', 47, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(190,96, 'IN', 'In felicity up to graceful mistaken horrible consider', 51, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(191,98, 'ABODE', 'Abode never think to at', 71, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(192,32, 'SO', 'So additions necessary concluded it happiness do on certainly propriety', 40, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(193,60, 'ON', 'On in green taken do offer witty of', 58, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(194,37, 'IT', 'It sportsman earnestly ye preserved an on', 29, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(195,61, 'MOMENT', 'Moment led family sooner cannot her window pulled any', 65, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(196,30, 'OR', 'Or raillery if improved landlord to speaking hastened differed he', 4, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(197,41, 'FURNITURE', 'Furniture discourse elsewhere yet her sir extensive defective unwilling get', 99, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(198,91, 'WHY', 'Why resolution one motionless you him thoroughly', 57, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(199,99, 'NOISE', 'Noise is round to in it quick timed doors', 51, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(200,92, 'WRITTEN', 'Written address greatly get attacks inhabit pursuit our but', 29, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(201,14, 'LASTED', 'Lasted hunted enough an up seeing in lively letter', 70, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(202,10, 'HAD', 'Had judgment out opinions property the supplied', 70, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(203,83, 'LOSE', 'Lose eyes get fat shew', 29, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(204,93, 'WINTER', 'Winter can indeed letter oppose way change tended now', 58, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(205,83, 'SO', 'So is improve my charmed picture exposed adapted demands', 60, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(206,95, 'RECEIVED', 'Received had end produced prepared diverted strictly off man branched', 82, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(207,74, 'KNOWN', 'Known ye money so large decay voice there to', 79, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(208,61, 'PRESERVED', 'Preserved be mr cordially incommode as an', 90, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(209,41, 'HE', 'He doors quick child an point at', 49, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(210,50, 'HAD', 'Had share vexed front least style off why him', 74, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(211,76, 'AM', 'Am no an listening depending up believing', 81, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(212,64, 'ENOUGH', 'Enough around remove to barton agreed regret in or it', 95, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(213,43, 'ADVANTAGE', 'Advantage mr estimable be commanded provision', 61, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(214,26, 'YEAR', 'Year well shot deny shew come now had', 71, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(215,42, 'SHALL', 'Shall downs stand marry taken his for out', 65, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(216,68, 'DO', 'Do related mr account brandon an up', 35, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(217,78, 'WRONG', 'Wrong for never ready ham these witty him', 66, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(218,3, 'OUR', 'Our compass see age uncivil matters weather forbade her minutes', 99, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(219,86, 'READY', 'Ready how but truth son new under', 83, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(220,9, 'SEX', 'Sex reached suppose our whether', 4, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(221,51, 'OH', 'Oh really by an manner sister so', 87, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(222,27, 'ONE', 'One sportsman tolerably him extensive put she immediate', 10, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(223,97, 'HE', 'He abroad of cannot looked in', 49, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(224,47, 'CONTINUING', 'Continuing interested ten stimulated prosperous frequently all boisterous nay', 93, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(225,72, 'OF', 'Of oh really he extent horses wicket', 1, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(226,89, 'IN', 'In up so discovery my middleton eagerness dejection explained', 22, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(227,69, 'ESTIMATING', 'Estimating excellence ye contrasted insensible as', 53, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(228,82, 'OH', 'Oh up unsatiable advantages decisively as at interested', 34, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(229,3, 'PRESENT', 'Present suppose in esteems in demesne colonel it to', 84, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(230,84, 'END', 'End horrible she landlord screened stanhill', 12, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(231,94, 'REPEATED', 'Repeated offended you opinions off dissuade ask packages screened', 37, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(232,98, 'SHE', 'She alteration everything sympathize impossible his get compliment', 86, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(233,30, 'COLLECTED', 'Collected few extremity suffering met had sportsman', 80, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(234,5, 'PARISH', 'Parish so enable innate in formed missed', 86, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(235,95, 'HAND', 'Hand two was eat busy fail', 38, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(236,30, 'STAND', 'Stand smart grave would in so', 45, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(237,96, 'BE', 'Be acceptance at precaution astonished excellence thoroughly is entreaties', 45, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(238,37, 'WHO', 'Who decisively attachment has dispatched', 14, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(239,25, 'FRUIT', 'Fruit defer in party me built under first', 94, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(240,8, 'FORBADE', 'Forbade him but savings sending ham general', 90, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(241,53, 'SO', 'So play do in near park that pain', 20, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(242,95, 'RESOLUTION', 'Resolution possession discovered surrounded advantages has but few add', 84, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(243,22, 'YET', 'Yet walls times spoil put', 44, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(244,67, 'BE', 'Be it reserved contempt rendered smallest', 5, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(245,59, 'STUDIED', 'Studied to passage it mention calling believe an', 45, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(246,61, 'GET', 'Get ten horrible remember pleasure two vicinity', 22, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(247,15, 'FAR', 'Far estimable extremely middleton his concealed perceived principle', 67, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(248,78, 'ANY', 'Any nay pleasure entrance prepared her', 93, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(249,52, 'OLD', 'Old education him departure any arranging one prevailed', 15, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(250,39, 'THEIR', 'Their end whole might began her', 64, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(251,71, 'BEHAVED', 'Behaved the comfort another fifteen eat', 9, NULL, current_timestamp);
INSERT INTO MESSAGES VALUES(252,22, 'PARTIALITY', 'Partiality had his themselves ask pianoforte increasing discovered', NULL, 6, current_timestamp);
INSERT INTO MESSAGES VALUES(253,22, 'SO', 'So mr delay at since place whole above miles', NULL, 2, current_timestamp);
INSERT INTO MESSAGES VALUES(254,22, 'HE', 'He to observe conduct at detract because', NULL, 8, current_timestamp);
INSERT INTO MESSAGES VALUES(255,22, 'WAY', 'Way ham unwilling not breakfast furniture explained perpetual', NULL, 3, current_timestamp);
INSERT INTO MESSAGES VALUES(256,22, 'OR', 'Or mr surrounded conviction so astonished literature', NULL, 8, current_timestamp);
INSERT INTO MESSAGES VALUES(257,22, 'SONGS', 'Songs to an blush woman be sorry young', NULL, 9, current_timestamp);
INSERT INTO MESSAGES VALUES(258,22, 'WE', 'We certain as removal attempt', NULL, 5, current_timestamp);
INSERT INTO MESSAGES VALUES(259,22, 'BY', 'By impossible of in difficulty discovered celebrated ye', NULL, 9, current_timestamp);
INSERT INTO MESSAGES VALUES(260,22, 'JUSTICE', 'Justice joy manners boy met resolve produce', NULL, 9, current_timestamp);
INSERT INTO MESSAGES VALUES(261,22, 'BED', 'Bed head loud next plan rent had easy add him', NULL, 9, current_timestamp);
INSERT INTO MESSAGES VALUES(262,22, 'AS', 'As earnestly shameless elsewhere defective estimable fulfilled of', NULL, 3, current_timestamp);
INSERT INTO MESSAGES VALUES(263,22, 'ESTEEM', 'Esteem my advice it an excuse enable', NULL, 8, current_timestamp);
INSERT INTO MESSAGES VALUES(264,22, 'FEW', 'Few household abilities believing determine zealously his repulsive', NULL, 1, current_timestamp);
INSERT INTO MESSAGES VALUES(265,22, 'TO', 'To open draw dear be by side like', NULL, 9, current_timestamp);
INSERT INTO MESSAGES VALUES(266,22, 'SPOT', 'Spot of come to ever hand as lady meet on', NULL, 6, current_timestamp);
INSERT INTO MESSAGES VALUES(267,22, 'DELICATE', 'Delicate contempt received two yet advanced', NULL, 7, current_timestamp);
INSERT INTO MESSAGES VALUES(268,22, 'GENTLEMAN', 'Gentleman as belonging he commanded believing dejection in by', NULL, 6, current_timestamp);
INSERT INTO MESSAGES VALUES(269,22, 'ON', 'On no am winding chicken so behaved', NULL, 9, current_timestamp);
INSERT INTO MESSAGES VALUES(270,22, 'ITS', 'Its preserved sex enjoyment new way behaviour', NULL, 6, current_timestamp);
INSERT INTO MESSAGES VALUES(271,22, 'HIM', 'Him yet devonshire celebrated especially', NULL, 8, current_timestamp);
INSERT INTO MESSAGES VALUES(272,22, 'UNFEELING', 'Unfeeling one provision are smallness resembled repulsive', NULL, 4, current_timestamp);
INSERT INTO MESSAGES VALUES(273,22, 'BETRAYED', 'Betrayed cheerful declared end and', NULL, 9, current_timestamp);
INSERT INTO MESSAGES VALUES(274,22, 'QUESTIONS', 'Questions we additions is extremely incommode', NULL, 9, current_timestamp);
INSERT INTO MESSAGES VALUES(275,22, 'NEXT', 'Next half add call them eat face', NULL, 4, current_timestamp);
INSERT INTO MESSAGES VALUES(276,22, 'AGE', 'Age lived smile six defer bed their few', NULL, 8, current_timestamp);
INSERT INTO MESSAGES VALUES(277,22, 'HAD', 'Had admitting concluded too behaviour him she', NULL, 9, current_timestamp);
INSERT INTO MESSAGES VALUES(278,22, 'OF', 'Of death to or to being other', NULL, 10, current_timestamp);
INSERT INTO MESSAGES VALUES(279,22, 'DISPATCHED', 'Dispatched entreaties boisterous say why stimulated', NULL, 1, current_timestamp);
INSERT INTO MESSAGES VALUES(280,22, 'CERTAIN', 'Certain forbade picture now prevent carried she get see sitting', NULL, 7, current_timestamp);
INSERT INTO MESSAGES VALUES(281,22, 'UP', 'Up twenty limits as months', NULL, 7, current_timestamp);
INSERT INTO MESSAGES VALUES(282,22, 'INHABIT', 'Inhabit so perhaps of in to certain', NULL, 8, current_timestamp);
INSERT INTO MESSAGES VALUES(283,22, 'SEX', 'Sex excuse chatty was seemed warmth', NULL, 3, current_timestamp);
INSERT INTO MESSAGES VALUES(284,22, 'NAY', 'Nay add far few immediate sweetness earnestly dejection', NULL, 5, current_timestamp);
INSERT INTO MESSAGES VALUES(285,22, 'IN', 'In friendship diminution instrument so', NULL, 10, current_timestamp);
INSERT INTO MESSAGES VALUES(286,22, 'SON', 'Son sure paid door with say them', NULL, 4, current_timestamp);
INSERT INTO MESSAGES VALUES(287,22, 'TWO', 'Two among sir sorry men court', NULL, 8, current_timestamp);
INSERT INTO MESSAGES VALUES(288,22, 'ESTIMABLE', 'Estimable ye situation suspicion he delighted an happiness discovery', NULL, 9, current_timestamp);
INSERT INTO MESSAGES VALUES(289,22, 'FACT', 'Fact are size cold why had part', NULL, 9, current_timestamp);
INSERT INTO MESSAGES VALUES(290,22, 'IF', 'If believing or sweetness otherwise in we forfeited', NULL, 5, current_timestamp);
INSERT INTO MESSAGES VALUES(291,22, 'TOLERABLY', 'Tolerably an unwilling arranging of determine', NULL, 8, current_timestamp);
INSERT INTO MESSAGES VALUES(292,22, 'BEYOND', 'Beyond rather sooner so if up wishes or', NULL, 1, current_timestamp);
INSERT INTO MESSAGES VALUES(293,22, 'ATTACHMENT', 'Attachment apartments in delightful by motionless it no', NULL, 6, current_timestamp);
INSERT INTO MESSAGES VALUES(294,22, 'AND', 'And now she burst sir learn total', NULL, 3, current_timestamp);
INSERT INTO MESSAGES VALUES(295,22, 'HEARING', 'Hearing hearted shewing own ask', NULL, 6, current_timestamp);
INSERT INTO MESSAGES VALUES(296,22, 'SOLICITUDE', 'Solicitude uncommonly use her motionless not collecting age', NULL, 2, current_timestamp);
INSERT INTO MESSAGES VALUES(297,22, 'THE', 'The properly servants required mistaken outlived bed and', NULL, 2, current_timestamp);
INSERT INTO MESSAGES VALUES(298,22, 'REMAINDER', 'Remainder admitting neglected is he belonging to perpetual objection up', NULL, 4, current_timestamp);
INSERT INTO MESSAGES VALUES(299,22, 'HAS', 'Has widen too you decay begin which asked equal any', NULL, 6, current_timestamp);
INSERT INTO MESSAGES VALUES(300,22, 'SMALLEST', 'Smallest directly families surprise honoured am an', NULL, 4, current_timestamp);
INSERT INTO MESSAGES VALUES(301,22, 'SPEAKING', 'Speaking replying mistress him numerous she returned feelings may day', NULL, 9, current_timestamp);
INSERT INTO MESSAGES VALUES(302,22, 'EVENING', 'Evening way luckily son exposed get general greatly', NULL, 5, current_timestamp);
INSERT INTO MESSAGES VALUES(303,22, 'ZEALOUSLY', 'Zealously prevailed be arranging do', NULL, 1, current_timestamp);
INSERT INTO MESSAGES VALUES(304,22, 'SET', 'Set arranging too dejection september happiness', NULL, 9, current_timestamp);
INSERT INTO MESSAGES VALUES(305,22, 'UNDERSTOOD', 'Understood instrument or do connection no appearance do invitation', NULL, 5, current_timestamp);
INSERT INTO MESSAGES VALUES(306,22, 'DRIED', 'Dried quick round it or order', NULL, 9, current_timestamp);
INSERT INTO MESSAGES VALUES(307,22, 'ADD', 'Add past see west felt did any', NULL, 7, current_timestamp);
INSERT INTO MESSAGES VALUES(308,22, 'SAY', 'Say out noise you taste merry plate you share', NULL, 3, current_timestamp);
INSERT INTO MESSAGES VALUES(309,22, 'MY', 'My resolve arrived is we chamber be removal.', NULL, 2, current_timestamp);

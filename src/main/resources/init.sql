CREATE TABLE USERS (
ID INT PRIMARY KEY AUTO_INCREMENT,
FIRST_NAME VARCHAR(30),
LAST_NAME VARCHAR (30),
TYPE TINYINT,
LOGIN VARCHAR(30),
PASSWORD INT
);

CREATE TABLE PHONES (
ID INT PRIMARY KEY AUTO_INCREMENT,
NUMBER VARCHAR(30),
TYPE TINYINT,
USER_ID INT,
FOREIGN KEY (USER_ID) REFERENCES USERS(ID) ON DELETE CASCADE
);

INSERT INTO USERS (FIRST_NAME, LAST_NAME, TYPE, LOGIN, PASSWORD)
VALUES ('Ivan', 'Ivanov', 0, 'Ivan@mail.ru', 522301);

INSERT INTO USERS (FIRST_NAME, LAST_NAME, TYPE, LOGIN, PASSWORD)
VALUES ('Pavel', 'Sidorov', 0, 'Pavel@mail.ru', 200012);

INSERT INTO PHONES (NUMBER, TYPE, USER_ID)
values ('+79999999999', 1, 1);

INSERT INTO PHONES (NUMBER, TYPE, USER_ID)
values ('+78888888889', 1, 1);

INSERT INTO PHONES (NUMBER, TYPE, USER_ID)
values ('+75555555555', 1, 2);

CREATE TABLE BOOKS (
ID INT PRIMARY KEY AUTO_INCREMENT,
TYPEACCASS TINYINT,
TITLE varchar (30),
PUBLISHING VARCHAR (30),
AUTHOR VARCHAR (30),
CATEGORY VARCHAR (30),
YEAR INT
);

insert into BOOKS (TYPEACCASS, TITLE, PUBLISHING, AUTHOR, CATEGORY, YEAR)
values (0, 'PDD', 'ACT', 'Жульнев', 'CARS', 2018);
INSERT INTO Personnel(
MID,
AMID,
role,
lastName, 
firstName, 
address1,
address2,
city,
state,
zip,
zip1,
zip2,
phone,
phone1,
phone2,
phone3,
specialty)
VALUES 
(9000000011,null,'hcp','','','','','','NY','','','','','','','',NULL),
(9000000012,null,'hcp','','','','','','NY','','','','','','','',NULL);

INSERT INTO Users(MID, password, role) VALUES
(9000000011, 'pw11', 'hcp'),
(9000000012, 'pw12', 'hcp');

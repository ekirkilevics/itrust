DELETE FROM Personnel WHERE MID = 9000000000;
DELETE FROM Users WHERE MID = 9000000000;
DELETE FROM HcpAssignedHos WHERE HCPID = 9000000000 AND HosID = '9191919191';
DELETE FROM HcpAssignedHos WHERE HCPID = 9000000000 AND HosID = '8181818181';


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
specialty,
email)
VALUES (
9000000000,
null,
'hcp',
'Doctor',
'Kelly',
'4321 My Road St',
'PO BOX 2',
'CityName',
'NY',
'12345-1234',
'12345',
'1234',
'999-888-7777',
'999',
'888',
'7777',
'surgeon',
'kdoctor@iTrust.org'
);

INSERT INTO Users(MID, password, role, sQuestion, sAnswer) VALUES(9000000000, 'pw', 'hcp', 'first letter?', 'a');

INSERT INTO HCPAssignedHos(HCPID, HosID) VALUES(9000000000,'9191919191'), (9000000000,'8181818181');

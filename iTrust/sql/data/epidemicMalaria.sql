INSERT INTO Patients (MID,firstName,lastName,zip1,state) 
VALUES  (52,'Disease-Ridden', 'Person 52','27607', 'NC');

INSERT INTO OfficeVisits(id,visitDate,notes,PatientID, hcpid, hospitalid)
VALUES 	(1,'2005-6-09','Office visit for malaria epidemic test.',52, 9000000000, '1'),
		(2,'2005-6-16','Office visit for malaria epidemic test.',52, 9000000000, '1'),
		(3,'2006-6-09','Office visit for malaria epidemic test.',52, 9000000000, '1'),
		(4,'2006-6-16','Office visit for malaria epidemic test.',52, 9000000000, '1'),
		(6,'2007-6-09','Office visit for malaria epidemic test.',52, 9000000000, '1'),
		(7,'2007-6-16','Office visit for malaria epidemic test.',52, 9000000000, '1');

INSERT INTO ICDCodes(Code, Description) VALUES (84, '');

INSERT INTO OVDiagnosis(ICDCode, VisitID) 
VALUES 	(84, 1), (84, 1),
		(84, 2), (84, 2),
		(84, 3), (84, 3), (84, 3),
		(84, 4), (84, 4), (84, 4),
		(84, 6), (84, 6), (84, 6), (84, 6),
		(84, 7), (84, 7), (84, 7), (84, 7);
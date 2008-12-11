INSERT INTO Patients (MID,firstName,lastName,zip1,state) 
VALUES  (52,'Disease-Ridden', 'Person 52','27607', 'NC'),
		(53,'Disease-Ridden', 'Person 53','27695', 'NC'),
		(54,'Disease-Ridden', 'Person 54','27540', 'NC'),
		(55,'Disease-Ridden', 'Person 55','19003', 'PA');

INSERT INTO OfficeVisits(id,visitDate,notes,PatientID)
VALUES 	(1,'2007-6-09','Office visit for epidemic test.',52),
		(2,'2007-6-09','Office visit for epidemic test.',53),
		(3,'2007-6-09','Office visit for epidemic test.',54),
		(4,'2007-6-09','Office visit for epidemic test.',54),
		(6,'2007-6-09','Office visit for epidemic test.',55),
		(7,'2007-6-01','Office visit for epidemic test.',53),
		(8,'2007-6-17','Office visit for epidemic test.',53),
		(9,'2007-6-26','Office visit for epidemic test.',53),
		(11,'2006-6-25','Office visit for epidemic test.',53);

INSERT INTO OVDiagnosis(ICDCode, VisitID) 
VALUES 	(84, 1),
		(84.4, 2),
		(84.5, 2),
		(84.53, 3),
		(487.3, 4),
		(84.2, 6),
		(84.1, 7),
		(487.42, 6),
		(84.5, 8),
		(84.5, 8),
		(84.5, 8),
		(84.5, 9),
		(84.5, 9),
		(84.5, 9),
		(84.5, 11),
		(84.5, 11);
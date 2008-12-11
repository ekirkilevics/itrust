INSERT INTO Patients (MID,firstName,lastName,zip1,state) 
VALUES  (52,'Disease-Ridden', 'Person 52','27607', 'NC');

INSERT INTO OfficeVisits(id,visitDate,notes,PatientID)
VALUES 	(1,'1993-1-12','Office visit for influenza epidemic test.',52),
		(2,'1993-1-20','Office visit for influenza epidemic test.',52);

INSERT INTO OVDiagnosis(ICDCode, VisitID) 
VALUES 	(487, 1),(487, 1),(487, 1),(487, 1),
		(487, 2),(487, 2),(487, 2),(487, 2), (487, 2);
		
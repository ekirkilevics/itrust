INSERT INTO Patients (MID, FirstName, MotherMID, FatherMID) 
			  VALUES (3,'Person', 4, 5),
			         (4, 'Mom', 1, 0),
			         (5, 'Dad', 0, 0),
			         (6, 'Kid1', 3, 0),
			         (7, 'Kid2', 3, 0),
			         (8, 'Sib1', 4,0),
			         (9, 'Sib2', 0, 5),
			         (10, 'Sib3', 4,5);

INSERT INTO OfficeVisits(ID, PatientID) VALUES (50, 4),(51, 7);

INSERT INTO OVDiagnosis(ICDCode, VisitID) VALUES (250.3, 50),(250.3, 51);
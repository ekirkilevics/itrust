DELETE FROM Allergies WHERE PatientID = 2;
INSERT INTO Allergies(PatientID,Description, FirstFound) 
	VALUES (2, 'Aspirin', '1999-03-14 20:00:00');

INSERT INTO OfficeVisits(
	id,
	visitDate,
	HCPID,
	notes,
	HospitalID,
	PatientID
)
VALUES (381,'2011-2-11',9000000001,'Hates getting shots','3',2);

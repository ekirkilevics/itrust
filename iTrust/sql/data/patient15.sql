DELETE FROM Users WHERE MID = 15;
DELETE FROM OfficeVisits WHERE PatientID = 15;
DELETE FROM Patients WHERE MID = 15;
DELETE FROM DeclaredHCP WHERE PatientID = 15;
DELETE FROM OVDiagnosis WHERE VisitID = 1067;
DELETE FROM OVMedication WHERE VisitID = 1067;


INSERT INTO Users(MID, password, role, sQuestion, sAnswer) 
			VALUES (15, 'pw', 'patient', 'how you doin?', 'good');

INSERT INTO Patients (MID, firstName,lastName, email, phone1, phone2, phone3) 
VALUES (15, 'Malk', 'Flober', 'm@n.com', '919', '555', '4321');


INSERT INTO OfficeVisits(id,visitDate,HCPID,notes,HospitalID,PatientID)
VALUES (1067,'2007-6-09',9900000000,'Yet another office visit.','1',15);



INSERT INTO OVMedication(NDCode, VisitID, StartDate,EndDate,Dosage,Instructions)
	VALUES ('647641512', 1067, '2006-10-10', DATE_ADD(CURDATE(), INTERVAL -1 DAY), 5, 'Take twice daily');


INSERT INTO OVDiagnosis(ICDCode, VisitID) VALUES 
			(493.00, 1067);

INSERT INTO DeclaredHCP(PatientID, HCPID) VALUES (15, 9900000000);
DELETE FROM DeclaredHCP /* Please use DELETE FROM and not TRUNCATE, otherwise the auto_increment start value gets wiped out */; 
DELETE FROM FakeEmail;
DELETE FROM GlobalVariables;
DELETE FROM HCPAssignedHos;
DELETE FROM HCPRelations;
DELETE FROM LabProcedure;
DELETE FROM LoginFailures;
DELETE FROM LOINC;
DELETE FROM Message;
DELETE FROM referrals;
DELETE FROM OVDiagnosis;
DELETE FROM OVMedication;
DELETE FROM OVProcedure;
DELETE FROM OVSurvey;
DELETE FROM OfficeVisits;
DELETE FROM ReportRequests;
DELETE FROM Representatives;
DELETE FROM ResetPasswordFailures;
DELETE FROM TransactionLog;


DELETE FROM PersonalAllergies;
DELETE FROM PersonalHealthInformation;
DELETE FROM PersonalRelations;
DELETE FROM Allergies;
DELETE FROM ICDCodes;


DELETE FROM Personnel;
DELETE FROM Hospitals;
DELETE FROM NDCodes;

DELETE FROM CPTCodes;
DELETE FROM Patients;
DELETE FROM Users;
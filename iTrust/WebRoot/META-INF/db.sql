CREATE TABLE Users(
	MID                 BIGINT unsigned,
	Password            VARCHAR(20),
	Role                enum('patient','admin','hcp','uap','er','tester','pha', 'lt') NOT NULL DEFAULT 'admin',
	sQuestion           VARCHAR(100) DEFAULT '', 
	sAnswer             VARCHAR(30) DEFAULT '',

	PRIMARY KEY (MID)
	/* Please use the MyISAM backend with no foreign keys.*/
) ENGINE=MyISAM; 

CREATE TABLE Hospitals(
	HospitalID   varchar(10),
	HospitalName varchar(30) NOT NULL, 
	
	PRIMARY KEY (hospitalID)
) ENGINE=MyISAM;

CREATE TABLE Personnel(
	MID BIGINT unsigned default NULL,
	AMID BIGINT unsigned default NULL,
	role enum('admin','hcp','uap','er','tester','pha', 'lt') NOT NULL default 'admin',
	enabled tinyint(1) unsigned NOT NULL default '0',
	lastName varchar(20) NOT NULL default '',
	firstName varchar(20) NOT NULL default '',
	address1 varchar(20) NOT NULL default '',
	address2 varchar(20) NOT NULL default '',
	city varchar(15) NOT NULL default '',
	state enum('','AK','AL','AR','AZ','CA','CO','CT','DE','DC','FL','GA','HI','IA','ID','IL','IN','KS','KY','LA','MA','MD','ME','MI','MN','MO','MS','MT','NC','ND','NE','NH','NJ','NM','NV','NY','OH','OK','OR','PA','RI','SC','SD','TN','TX','UT','VA','VT','WA','WI','WV','WY') NOT NULL default '',
	zip varchar(10) NOT NULL default '',
	zip1 varchar(5)  default '',
	zip2 varchar(4)  default '',
	phone varchar(12) NOT NULL default '',
	phone1 varchar(3) default '',
    phone2 varchar(3) default '',
    phone3 varchar(4) default '',
	specialty varchar(40) default NULL,
	email varchar(55)  default '',
	MessageFilter varchar(60) default '',
	PRIMARY KEY  (MID)
) auto_increment=9000000000 ENGINE=MyISAM;

CREATE TABLE Patients(
	MID BIGINT unsigned  auto_increment, 
	lastName varchar(20)  default '', 
	firstName varchar(20)  default '', 
	email varchar(55)  default '', 
	address1 varchar(20)  default '', 
	address2 varchar(20)  default '', 
	city varchar(15)  default '', 
	state enum('AK','AL','AR','AZ','CA','CO','CT','DE','DC','FL','GA','HI','IA','ID','IL','IN','KS','KY','LA','MA','MD','ME','MI','MN','MO','MS','MT','NC','ND','NE','NH','NJ','NM','NV','NY','OH','OK','OR','PA','RI','SC','SD','TN','TX','UT','VA','VT','WA','WI','WV','WY')  default 'AK', 
	zip1 varchar(5)  default '', 
	zip2 varchar(4)  default '',
	phone1 varchar(3) default '',
    phone2 varchar(3) default '',
    phone3 varchar(4) default '',
	eName varchar(40)  default '', 
	ePhone1 varchar(3)  default '', 
	ePhone2 varchar(3)  default '', 		
	ePhone3 varchar(4)  default '', 	
	iCName varchar(20)  default '', 
	iCAddress1 varchar(20)  default '', 
	iCAddress2 varchar(20)  default '', 
	iCCity varchar(15)  default '', 
	ICState enum('AK','AL','AR','AZ','CA','CO','CT','DE','DC','FL','GA','HI','IA','ID','IL','IN','KS','KY','LA','MA','MD','ME','MI','MN','MO','MS','MT','NC','ND','NE','NH','NJ','NM','NV','NY','OH','OK','OR','PA','RI','SC','SD','TN','TX','UT','VA','VT','WA','WI','WV','WY')  default 'AK', 
	iCZip1 varchar(5)  default '', 
	iCZip2 varchar(4)  default '',
	iCPhone1 varchar(3)  default '',
	iCPhone2 varchar(3)  default '',
	iCPhone3 varchar(4)  default '',			
	iCID varchar(20)  default '', 
	DateOfBirth DATE,
	DateOfDeath DATE,
	CauseOfDeath VARCHAR(10) default '',
	MotherMID INTEGER(10) default 0,
	FatherMID INTEGER(10) default 0,
	BloodType VARCHAR(3) default '',
	Ethnicity VARCHAR(20) default '',
	Gender VARCHAR(13) default 'Not Specified',
	TopicalNotes VARCHAR(200) default '',
	CreditCardType VARCHAR(20) default '',
	CreditCardNumber VARCHAR(19) default '',
	MessageFilter varchar(60) default '',
	PRIMARY KEY (MID)
) ENGINE=MyISAM;

CREATE TABLE LoginFailures(
	ipaddress varchar(128) NOT NULL, 
	failureCount int NOT NULL default 0, 
	lastFailure TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (ipaddress)
) ENGINE=MyISAM;

CREATE TABLE ResetPasswordFailures(
	ipaddress varchar(128) NOT NULL, 
	failureCount int NOT NULL default 0, 
	lastFailure TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (ipaddress)
) ENGINE=MyISAM;

CREATE TABLE icdcodes (
  Code decimal(5,2) NOT NULL,
  Description TEXT NOT NULL,
  Chronic enum('no','yes') NOT NULL default 'no',
  PRIMARY KEY (Code)
) ENGINE=MyISAM;

CREATE TABLE CPTCodes(
	Code varchar(5) NOT NULL COMMENT 'Actual CPT Code', 
	Description varchar(30) NOT NULL COMMENT 'Description of the CPT Codes', 
	Attribute varchar(30),
	PRIMARY KEY (Code)
) ENGINE=MyISAM;

CREATE TABLE DrugReactionOverrideCodes(
	Code varchar(5) NOT NULL COMMENT 'Identifier for override reason',
	Description varchar(80) NOT NULL COMMENT 'Description of override reason',
	PRIMARY KEY (Code)
) ENGINE=MyISAM;
	
CREATE TABLE NDCodes(
	Code varchar(9) NOT NULL, 
	Description varchar(40) NOT NULL, 
	PRIMARY KEY  (Code)
) ENGINE=MyISAM;

CREATE TABLE DrugInteractions(
	FirstDrug varchar(9) NOT NULL,
	SecondDrug varchar(9) NOT NULL,
	Description varchar(100) NOT NULL,
	PRIMARY KEY  (FirstDrug,SecondDrug)
) ENGINE=MyISAM;

CREATE TABLE TransactionLog(
	transactionID int(10) unsigned NOT NULL auto_increment, 
	loggedInMID BIGINT unsigned NOT NULL DEFAULT '0', 
	secondaryMID BIGINT unsigned NOT NULL DEFAULT '0', 
	transactionCode int(10) UNSIGNED NOT NULL default '0', 
	timeLogged timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP, 
	addedInfo VARCHAR(255) default '',
	PRIMARY KEY (transactionID)
) ENGINE=MyISAM;

CREATE TABLE HCPRelations(
	HCP BIGINT unsigned NOT NULL default '0', 
	UAP BIGINT unsigned NOT NULL default '0',
	PRIMARY KEY (HCP, UAP)
) ENGINE=MyISAM;

CREATE TABLE PersonalRelations(
	PatientID BIGINT unsigned NOT NULL COMMENT 'MID of the patient',
	RelativeID BIGINT unsigned NOT NULL COMMENT 'MID of the Relative',
	RelativeType VARCHAR( 35 ) NOT NULL COMMENT 'Relation Type'
) ENGINE=MyISAM;

CREATE TABLE Representatives(
	representerMID BIGINT unsigned default 0, 
	representeeMID BIGINT unsigned default 0,
	PRIMARY KEY  (representerMID,representeeMID)
) ENGINE=MyISAM;

CREATE TABLE HCPAssignedHos(
	hosID VARCHAR(10) NOT NULL, 
	HCPID BIGINT unsigned NOT NULL, 
	PRIMARY KEY (hosID,HCPID)
) ENGINE=MyISAM;

CREATE TABLE DeclaredHCP(
	PatientID BIGINT unsigned NOT NULL default '0', 
	HCPID BIGINT unsigned NOT NULL default '0', 
	PRIMARY KEY  (PatientID,HCPID)
) ENGINE=MyISAM;

CREATE TABLE OfficeVisits(
	ID int(10) unsigned auto_increment,
	visitDate date default '0000-00-00',  
	HCPID BIGINT unsigned default '0', 
	notes mediumtext, 
	PatientID BIGINT unsigned default '0', 
	HospitalID VARCHAR(10) default '',
	PRIMARY KEY  (ID)
) ENGINE=MyISAM;

CREATE TABLE PersonalHealthInformation (
	PatientID BIGINT unsigned NOT NULL default '0',
	Height float default '0',  
	Weight float default '0',  
	Smoker tinyint(1) NOT NULL default '0' COMMENT 'Is the person a smoker',  
	BloodPressureN int(11) default '0',  
	BloodPressureD int(11) default '0',  
	CholesterolHDL int(11) default '0' COMMENT 'HDL Cholesterol',  
	CholesterolLDL int(11) default '0' COMMENT 'LDL Ccholesterol',  
	CholesterolTri int(11) default '0' COMMENT 'Cholesterol Triglyceride',  
	HCPID BIGINT unsigned default NULL,  
	AsOfDate timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP
) ENGINE=MyISAM;

CREATE TABLE PersonalAllergies(
	PatientID BIGINT unsigned NOT NULL COMMENT 'MID of the Patient',
	Allergy VARCHAR( 50 ) NOT NULL COMMENT 'Description of the allergy'
) ENGINE=MyISAM;

CREATE TABLE Allergies(
	ID INT(10) unsigned auto_increment primary key,
	PatientID BIGINT unsigned NOT NULL COMMENT 'MID of the Patient',
	Description VARCHAR( 50 ) NOT NULL COMMENT 'Description of the allergy',
	FirstFound TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=MyISAM;

CREATE TABLE OVProcedure(
	ID INT(10) auto_increment primary key,
	VisitID INT( 10 ) unsigned NOT NULL COMMENT 'ID of the Office Visit',
	CPTCode VARCHAR( 5 ) NOT NULL COMMENT 'CPTCode of the procedure',
	HCPID VARCHAR( 10 ) NOT NULL DEFAULT ''
) ENGINE=MyISAM;

CREATE TABLE OVMedication (
    ID INT(10)  auto_increment primary key,
	VisitID INT( 10 ) unsigned NOT NULL COMMENT 'ID of the Office Visit',
	NDCode VARCHAR( 9 ) NOT NULL COMMENT 'NDCode for the medication',
	StartDate DATE,
	EndDate DATE,
	Dosage INT DEFAULT 0 COMMENT 'Always in mg - this could certainly be changed later',
	Instructions VARCHAR(500) DEFAULT ''
) ENGINE=MyISAM;

CREATE TABLE OVReactionOverride (
	ID INT(10)  auto_increment primary key,
	OVMedicationID INT(10) NOT NULL COMMENT 'Must correspond to an ID in OVMedication table',
	OverrideCode VARCHAR(5) COMMENT 'Code identifier of the override reason',
	OverrideComment VARCHAR(255) DEFAULT '' COMMENT 'Optional reason for override',
	FOREIGN KEY (OVMedicationID) REFERENCES OVMedication (ID)
) ENGINE=MyISAM;

CREATE TABLE OVDiagnosis (
    ID INT(10) auto_increment primary key,
	VisitID INT( 10 ) unsigned NOT NULL COMMENT 'ID of the Office Visit',
	ICDCode DECIMAL( 5, 2 ) NOT NULL COMMENT 'Code for the Diagnosis'
) ENGINE=MyISAM;

CREATE TABLE GlobalVariables (
	Name VARCHAR(20) primary key,
	Value VARCHAR(20)
) ENGINE=MyISAM;

INSERT INTO GlobalVariables(Name,Value) VALUES ('Timeout', '20');

CREATE TABLE FakeEmail(
	ID INT(10) auto_increment primary key,
	ToAddr VARCHAR(100),
	FromAddr VARCHAR(100),
	Subject VARCHAR(500),
	Body VARCHAR(2000),
	AddedDate timestamp NOT NULL default CURRENT_TIMESTAMP
) ENGINE=MyISAM;

CREATE TABLE ReportRequests (
	ID INT(10) auto_increment primary key,
    RequesterMID BIGINT unsigned,
    PatientMID BIGINT unsigned,
    ApproverMID BIGINT unsigned,
    RequestedDate datetime,
    ApprovedDate datetime,
    ViewedDate datetime,
    Status varchar(30),
	Comment TEXT
) ENGINE=MyISAM;

CREATE TABLE OVSurvey (
	VisitID int(10) unsigned primary key COMMENT 'ID of the Office Visit',
	SurveyDate datetime not null COMMENT 'Date the survey was completed',
	WaitingRoomMinutes int(3) COMMENT 'How many minutes did you wait in the waiting room?',
	ExamRoomMinutes int(3) COMMENT 'How many minutes did you wait in the examination room before seeing your physician?',
    VisitSatisfaction int(1) COMMENT 'How satisfied were you with your office visit?',
	TreatmentSatisfaction int(1) COMMENT 'How satisfied were you with the treatment or information you received?'
) ENGINE=MyISAM;

CREATE TABLE LOINC (
	LaboratoryProcedureCode VARCHAR (7), 
	Component VARCHAR(100),
	KindOfProperty VARCHAR(100),
	TimeAspect VARCHAR(100),
	System VARCHAR(100),
	ScaleType VARCHAR(100),
	MethodType VARCHAR(100)
) ENGINE=MyISAM;

CREATE TABLE LabProcedure (
	LaboratoryProcedureID BIGINT(10) auto_increment primary key,
	PatientMID BIGINT unsigned, 
	LaboratoryProcedureCode VARCHAR (7), 
	Rights VARCHAR(10),
	Status VARCHAR(20),
	Commentary MEDIUMTEXT,
	Results MEDIUMTEXT,
	NumericalResults VARCHAR(20),
	UpperBound VARCHAR(20),
	LowerBound VARCHAR(20),	
	OfficeVisitID INT unsigned,
	LabTechID LONG,
	PriorityCode INT unsigned,
	ViewedByPatient BOOLEAN NOT NULL default FALSE,
	UpdatedDate timestamp NOT NULL default CURRENT_TIMESTAMP
) ENGINE=MyISAM;

CREATE TABLE message (
	message_id          INT UNSIGNED AUTO_INCREMENT,
	parent_msg_id       INT UNSIGNED,
	from_id             BIGINT UNSIGNED NOT NULL,
	to_id               BIGINT UNSIGNED NOT NULL,
	sent_date           DATETIME NOT NULL,
	message             TEXT,
	subject				TEXT,
	been_read			INT UNSIGNED,
	PRIMARY KEY (message_id)
) ENGINE=MyISAM;

CREATE TABLE Appointment (
	appt_id				INT UNSIGNED AUTO_INCREMENT primary key,
	doctor_id           BIGINT UNSIGNED NOT NULL,
	patient_id          BIGINT UNSIGNED NOT NULL,
	sched_date          DATETIME NOT NULL,
	appt_type           VARCHAR(30) NOT NULL,
	comment				TEXT
) ENGINE=MyISAM;

CREATE TABLE AppointmentType (
	apptType_id			INT UNSIGNED AUTO_INCREMENT primary key,
	appt_type           VARCHAR(30) NOT NULL,
	duration			INT UNSIGNED NOT NULL
) ENGINE=MyISAM;

CREATE TABLE referrals (
	id          INT UNSIGNED AUTO_INCREMENT,
	PatientID          BIGINT UNSIGNED NOT NULL,
	SenderID               BIGINT UNSIGNED NOT NULL,
	ReceiverID           BIGINT UNSIGNED NOT NULL,
	ReferralDetails             TEXT,
	OVID		BIGINT UNSIGNED NOT NULL,
	viewed_by_patient 	boolean NOT NULL,
	viewed_by_HCP 	boolean NOT NULL,
	TimeStamp DATETIME NOT NULL,
	PriorityCode INT unsigned,
	PRIMARY KEY (id)
) AUTO_INCREMENT=1 ENGINE=MyISAM;

CREATE TABLE RemoteMonitoringData (
	id          INT UNSIGNED AUTO_INCREMENT,
	PatientID          BIGINT UNSIGNED NOT NULL,
	systolicBloodPressure int(10) SIGNED default -1,
	diastolicBloodPressure int(10) SIGNED default -1,
	glucoseLevel int(10) SIGNED default -1,
	height float default -1,
	weight float default -1,
	pedometerReading int(10) SIGNED default -1,
	timeLogged timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
	ReporterRole		TEXT,
	ReporterID          BIGINT UNSIGNED NOT NULL,
	PRIMARY KEY (id)
) AUTO_INCREMENT=1 ENGINE=MyISAM;

CREATE TABLE RemoteMonitoringLists (
	PatientMID BIGINT unsigned default 0, 
	HCPMID BIGINT unsigned default 0,
	SystolicBloodPressure BOOLEAN default true,
	DiastolicBloodPressure BOOLEAN default true,
	GlucoseLevel BOOLEAN default true,
	Height BOOLEAN default true,
	Weight BOOLEAN default true,
	PedometerReading BOOLEAN default true,
	PRIMARY KEY  (PatientMID,HCPMID)
) ENGINE=MyISAM;

CREATE TABLE AdverseEvents (
	id INT UNSIGNED AUTO_INCREMENT primary key,
	Status VARCHAR(10) default "Active",
	PatientMID BIGINT unsigned default 0,
	PresImmu VARCHAR(50),
	Code VARCHAR(20),
	Comment VARCHAR(2000),
	Prescriber BIGINT unsigned default 0,
	TimeLogged timestamp NOT NULL default CURRENT_TIMESTAMP
) ENGINE=MyISAM;

CREATE TABLE ProfilePhotos (
	MID BIGINT (10) primary key,
	Photo LONGBLOB,
	UpdatedDate timestamp NOT NULL default CURRENT_TIMESTAMP
) ENGINE=MyISAM;

CREATE TABLE PatientSpecificInstructions (
    id BIGINT unsigned AUTO_INCREMENT primary key,
    VisitID BIGINT unsigned,
    Modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Name VARCHAR(100),
    URL VARCHAR(250),
    Comment VARCHAR(500)
) ENGINE=MyISAM;

CREATE TABLE ReferralMessage(
	messageID  INT unsigned NOT NULL, 
	referralID INT unsigned NOT NULL, 
	PRIMARY KEY (messageID,referralID)
) ENGINE=MyISAM;



INSERT INTO CPTCodes(Code, Description, Attribute) VALUES('1270F','Injection procedure', NULL),('87','Diagnostic Radiology', NULL),('90655', 'Influenza virus vaccine, split', 'immunization'),('90656', 'Influenza virus vaccine, split', 'immunization'),('90657', 'Influenza virus vaccine, split', 'immunization'),('90658', 'Influenza virus vaccine, split', 'immunization'),('90660', 'Influenza virus vaccine, live', 'immunization'),('90371', 'Hepatitis B', 'immunization'),('90681', 'Rotavirus', 'immunization'),('90696', 'Diphtheria, Tetanus, Pertussis', 'immunization'),('90645', 'Haemophilus influenzae', 'immunization'),('90669', 'Pneumococcal', 'immunization'),('90712', 'Poliovirus', 'immunization'),('90707', 'Measles, Mumps, Rubella', 'immunization'),('90396', 'Varicella', 'immunization'),('90633', 'Hepatitis A', 'immunization'),('90649', 'Human Papillomavirus', 'immunization')ON DUPLICATE KEY UPDATE Code = Code;
INSERT INTO ICDCodes(Code, Description, Chronic) VALUES('250.10', 'Diabetes with ketoacidosis', 'yes'),('250.30','Diabetes with other coma', 'yes'),('487.00', 'Influenza', 'no'),('79.10', 'Echovirus', 'no'),('84.50', 'Malaria', 'no'),('79.30', 'Coxsackie', 'yes'),('11.40', 'Tuberculosis of the lung', 'no'),('15.00', 'Tuberculosis of vertebral column', 'no'),('42.00', 'Human Immunodeficiency Virus', 'yes'),('70.10', 'Viral hepatitis A, infectious', 'yes'),('250.00','Acute Lycanthropy', 'yes'),('72.00','Mumps', 'no')  ON DUPLICATE KEY UPDATE Code = Code;
INSERT INTO NDCodes(Code, Description) VALUES('009042407','Tetracycline'),('081096','Aspirin'),('647641512','Prioglitazone'),('548684985', 'Citalopram Hydrobromide'),('00060431', 'Benzoyl Peroxide')ON DUPLICATE KEY UPDATE Code = Code;
INSERT INTO DrugReactionOverrideCodes(Code, Description) VALUES('00001','Alerted interaction not clincally significant'),('00002','Patient currently tolerates the medication or combination'),('00003','Benefit of drug outweighs disadvantages'),('00004', 'Patient has tolerated medication or combination in the past'),('00005', 'Medication list out of date'),('00006', 'Limited course of treatment'),('00007', 'No good alternatives available to alerted medication'),('00008', 'Allergy information inaccurate in patient''s record')ON DUPLICATE KEY UPDATE Code = Code;
INSERT INTO Hospitals(HospitalID, HospitalName) VALUES('9191919191','Test Hospital 9191919191'),('8181818181','Test Hospital 8181818181'),('1','Test Hospital 1'),('2','Central Hospital'),('3','Northern Hospital'),('4','Eastern Hospital'),('','Z Empty Hospital') ON DUPLICATE KEY UPDATE HospitalID = HospitalID;
INSERT INTO Personnel(MID,AMID,role,lastName, firstName, address1,address2,city,state,zip,zip1,zip2,phone,phone1,phone2,phone3,specialty,email)VALUES (9000000000,null,'hcp','Doctor','Kelly','4321 My Road St','PO BOX 2','CityName','NY','12345-1234','12345','1234','999-888-7777','999','888','7777','surgeon','kdoctor@iTrust.org')ON DUPLICATE KEY UPDATE MID = MID;
INSERT INTO Users(MID, password, role, sQuestion, sAnswer) VALUES(9000000000, 'pw', 'hcp', 'first letter?', 'a')ON DUPLICATE KEY UPDATE MID = MID;
INSERT INTO HCPAssignedHos(HCPID, HosID) VALUES(9000000000,'9191919191'), (9000000000,'8181818181')ON DUPLICATE KEY UPDATE HCPID = HCPID;
INSERT INTO Personnel(MID,AMID,role,lastName, firstName, address1,address2,city,state,zip,zip1,zip2,phone,phone1,phone2,phone3,specialty,email)VALUES (5000000001,null,'lt','Dude','Lab','4321 My Road St','PO BOX 2','CityName','NY','12345-1234','12345','1234','999-888-7777','999','888','7777','blood','ldude@iTrust.org')ON DUPLICATE KEY UPDATE MID = MID;
INSERT INTO Users(MID, password, role, sQuestion, sAnswer) VALUES(5000000001, 'pw', 'lt', 'first letter?', 'a')ON DUPLICATE KEY UPDATE MID = MID;
INSERT INTO HCPAssignedHos(HCPID, HosID) VALUES(5000000001,'9191919191')ON DUPLICATE KEY UPDATE HCPID = HCPID;
INSERT INTO Personnel(MID,AMID,role,lastName, firstName, address1,address2,city,state,zip,zip1,zip2,phone,phone1,phone2,phone3,specialty,email)VALUES (5000000002,null,'lt','Guy','Nice','4321 My Road St','PO BOX 2','CityName','NY','12345-1234','12345','1234','999-888-7777','999','888','7777','tissue','nguy@iTrust.org')ON DUPLICATE KEY UPDATE MID = MID;
INSERT INTO Users(MID, password, role, sQuestion, sAnswer) VALUES(5000000002, 'pw', 'lt', 'first letter?', 'a')ON DUPLICATE KEY UPDATE MID = MID;
INSERT INTO HCPAssignedHos(HCPID, HosID) VALUES(5000000002,'1')ON DUPLICATE KEY UPDATE HCPID = HCPID;
INSERT INTO Personnel(MID,AMID,role,lastName, firstName, address1,address2,city,state,zip,zip1,zip2,phone,phone1,phone2,phone3,specialty,email)VALUES (5000000003,null,'lt','Person','Cool','4321 My Road St','PO BOX 2','CityName','NY','12345-1234','12345','1234','999-888-7777','999','888','7777','general','cperson@iTrust.org')ON DUPLICATE KEY UPDATE MID = MID;
INSERT INTO Users(MID, password, role, sQuestion, sAnswer) VALUES(5000000003, 'pw', 'lt', 'first letter?', 'a')ON DUPLICATE KEY UPDATE MID = MID;
INSERT INTO HCPAssignedHos(HCPID, HosID) VALUES(5000000003,'2')ON DUPLICATE KEY UPDATE HCPID = HCPID;
INSERT INTO Personnel(MID,AMID,role,lastName, firstName, address1,address2,city,state,zip,zip1,zip2,phone,phone1,phone2,phone3,specialty,email)VALUES (9000000003,null,'hcp','Stormcrow','Gandalf','4321 My Road St','PO BOX 2','CityName','NY','12345-1234','12345','1234','999-888-7777','999','888','7777',NULL,'gstormcrow@iTrust.org') ON DUPLICATE KEY UPDATE MID = MID;
INSERT INTO Users(MID, password, role, sQuestion, sAnswer) VALUES(9000000003, 'pw', 'hcp', 'first letter?', 'a')ON DUPLICATE KEY UPDATE MID = MID;
INSERT INTO HCPAssignedHos(HCPID, HosID) VALUES(9000000003,'9191919191')ON DUPLICATE KEY UPDATE HCPID = HCPID;
INSERT INTO Personnel(MID,AMID,role,lastName, firstName, address1,address2,city,state,zip,zip1,zip2,phone,phone1,phone2,phone3,specialty,email)VALUES (9000000007,null,'hcp','Beaker','Beaker','Meep Meep Street','','Meep Meep Town','CA','12345-1234','12345','1234','999-888-7777','999','888','7777','Pediatrician','meepmeep@meep.org')ON DUPLICATE KEY UPDATE MID = MID;
INSERT INTO Users(MID, password, role, sQuestion, sAnswer) VALUES(9000000007, 'pw', 'hcp', 'first letter?', 'a')ON DUPLICATE KEY UPDATE MID = MID;
INSERT INTO HCPAssignedHos(HCPID, HosID) VALUES(9000000007,'4'), (9000000007,'4')ON DUPLICATE KEY UPDATE HCPID = HCPID;
INSERT INTO Personnel(MID,AMID,role,lastName, firstName, address1,address2,city,state,zip,zip1,zip2,phone,phone1,phone2,phone3,specialty,email)VALUES (9000000006,null,'er','Time','Justin','555 Wanahakalugi','','Honolulu','HI','96801','96801','','135-246-3579','123','246','3579','','jtime@iTrust.or');
INSERT INTO Users(MID, password, role, sQuestion, sAnswer) VALUES(9000000006, 'pw', 'er', 'first letter?', 'a');
INSERT INTO Personnel(MID,AMID,role,lastName, firstName, address1,address2,city,state,zip,zip1,zip2,phone,phone1,phone2,phone3,specialty,email)VALUES (7000000001,null,'pha','Boe','Joe','4321 My Road St','PO BOX 2','CityName','NY','12345-1234','12345','1234','999-888-7777','999','888','7777','human being','bob.joe@iTrust.org')ON DUPLICATE KEY UPDATE MID = MID;
INSERT INTO Users(MID, password, role, sQuestion, sAnswer) VALUES(7000000001, 'pw', 'pha', 'first letter?', 'a')ON DUPLICATE KEY UPDATE MID = MID;
INSERT INTO Patients(MID, lastName, firstName,email,address1,address2,city,state,zip1,zip2,phone1,phone2,phone3,eName,ePhone1,ePhone2,ePhone3,iCName,iCAddress1,iCAddress2,iCCity, ICState,iCZip1,iCZip2,iCPhone1,iCPhone2,iCPhone3,iCID,dateofbirth,mothermid,fathermid,bloodtype,ethnicity,gender, topicalnotes)VALUES(1,'Person', 'Random', 'nobody@gmail.com', '1247 Noname Dr', 'Suite 106', 'Raleigh', 'NC', '27606','1234', '919','971','0000', 'Mommy Person', '704','532','2117', 'Aetna', '1234 Aetna Blvd', 'Suite 602', 'Charlotte','NC', '28215','', '704','555','1234', 'ChetumNHowe', '1950-05-10',0,0,'AB+','African American','Female','') ON DUPLICATE KEY UPDATE MID = MID;
INSERT INTO Users(MID, password, role, sQuestion, sAnswer) 			VALUES (1, 'pw', 'patient', 'what is your favorite color?', 'blue') ON DUPLICATE KEY UPDATE MID = MID;
DELETE FROM PersonalHealthInformation WHERE PatientID = 1;
INSERT INTO PersonalHealthInformation(PatientID,Height,Weight,Smoker,BloodPressureN,BloodPressureD,CholesterolHDL,CholesterolLDL,CholesterolTri,HCPID, AsOfDate)VALUES ( 1,  72,   180,   0,      100,          100,           40,             100,         100,          9000000000, '2007-06-07 20:33:58');
INSERT INTO OfficeVisits(id,visitDate,HCPID,notes,HospitalID,PatientID)VALUES (11,'2005-10-10',9000000000,'Yet another office visit.','',1) ON DUPLICATE KEY UPDATE id = id;
INSERT INTO OVDiagnosis(ICDCode, VisitID) VALUES (350.0, 11),(250.0, 11) ON DUPLICATE KEY UPDATE ICDCode = VALUES(ICDCode), VisitID = VALUES(VisitID);
INSERT INTO DeclaredHCP(PatientID,HCPID) VALUE(1, 9000000003) ON DUPLICATE KEY UPDATE PatientID = PatientID;
INSERT INTO Patients(MID, firstName,lastName, email,address1,address2,city,state,zip1,zip2,phone1,phone2,phone3,eName,ePhone1,ePhone2,ePhone3,iCName,iCAddress1,iCAddress2,iCCity, ICState,iCZip1,iCZip2,iCPhone1,iCPhone2,iCPhone3,iCID,DateOfBirth,DateOfDeath,CauseOfDeath,MotherMID,FatherMID,BloodType,Ethnicity,Gender,TopicalNotes)VALUES (2,'Andy','Programmer','andy.programmer@gmail.com','344 Bob Street','','Raleigh','NC','27607','','555','555','5555','Mr Emergency','555','555','5551','IC','Street1','Street2','City','PA','19003','2715','555','555','5555','1','1984-05-19','2005-03-10','250.10',1,0,'O-','Caucasian','Male','This person is absolutely crazy. Do not touch them.')  ON DUPLICATE KEY UPDATE MID = MID;
INSERT INTO Users(MID, password, role, sQuestion, sAnswer) 			VALUES (2, 'pw', 'patient', 'how you doin?', 'good') ON DUPLICATE KEY UPDATE MID = MID;
DELETE FROM Allergies WHERE PatientID = 2;
INSERT INTO Allergies(PatientID,Description, FirstFound) 	VALUES (2, 'Pollen', '2007-06-05 20:33:58'),	       (2, 'Penicillin', '2007-06-04 20:33:58');
	       INSERT INTO NDCodes(Code, Description) VALUES('664662530','Penicillin')ON DUPLICATE KEY UPDATE Code = Code;
DELETE FROM PersonalHealthInformation WHERE PatientID = 2;
INSERT INTO PersonalHealthInformation(PatientID,Height,Weight,Smoker,BloodPressureN,BloodPressureD,CholesterolHDL,CholesterolLDL,CholesterolTri,HCPID, AsOfDate)VALUES ( 2,  60,   200,   0,      190,          100,           500,             239,         290,          9000000000, '2007-06-07 20:33:58'),	   ( 2,  62,   210,   1,      195,          250,             36,             215,           280,          9000000000, '2007-06-07 20:34:58');
INSERT INTO OfficeVisits(id,visitDate,HCPID,notes,HospitalID,PatientID)VALUES (952,'2007-6-09',9000000000,'Yet another office visit.','1',2),	   (953,'2005-10-10',9000000000,'Yet another office visit.','1',2),	   (954,'2005-10-10',9000000000,'Yet another office visit.','1',2),	   (955,'2007-6-10',9000000000,'Yet another office visit.','1',2),	   (956,'2005-10-10',9000000000,'Yet another office visit.','1',2),	   (957,'2005-10-10',9000000000,'Yet another office visit.','1',2),	   (958,'2005-10-10',9000000000,'Yet another office visit.','1',2),	   (959,'2006-10-10',9000000000,'Yet another office visit.','1',2),	   (960,'1985-10-10',9000000000,'Yet another office visit.','',2)		 ON DUPLICATE KEY UPDATE id = id;
INSERT INTO OVDiagnosis(ID, ICDCode, VisitID) 	VALUES  (100, 250.1, 955),			(101, 79.30, 960),			(102, 250.1, 960),			(103, 250.1, 960),			(104, 11.4, 960),			(105, 15.00, 960)			 ON DUPLICATE KEY UPDATE id = id;
DELETE FROM OVMedication WHERE VisitID = 955;
INSERT INTO OVMedication(NDCode, VisitID, StartDate,EndDate,Dosage,Instructions)	VALUES ('009042407', 955, '2006-10-10', '2006-10-11', 5, 'Take twice daily'),		   ('009042407', 955, '2006-10-10', '2006-10-11', 5, 'Take twice daily'),		   ('647641512', 955, '2006-10-10', '2020-10-11', 5, 'Take twice daily');
DELETE FROM OVProcedure WHERE VisitID = 955;
INSERT INTO OVProcedure(ID, VisitID, CPTCode) VALUES (955, 955, '1270F');
INSERT INTO DeclaredHCP(PatientID,HCPID) VALUE(2, 9000000003) ON DUPLICATE KEY UPDATE PatientID = PatientID;
INSERT INTO Representatives(RepresenterMID, RepresenteeMID) VALUES(2,1) ON DUPLICATE KEY UPDATE RepresenterMID = RepresenterMID;
INSERT INTO OVSurvey (VisitID, SurveyDate, WaitingRoomMinutes, ExamRoomMinutes, VisitSatisfaction, TreatmentSatisfaction)	VALUES (952, '2008-03-01 12:00:00', null, 20, 1, 5),		   (954, '2008-03-02 13:00:00', 25, 55, 2, 4)			 ON DUPLICATE KEY UPDATE VisitID = VisitID;
INSERT INTO Patients(MID,lastName, firstName,email,address1,address2,city,state,zip1,zip2,phone1,phone2,phone3,eName,ePhone1,ePhone2,ePhone3,iCName,iCAddress1,iCAddress2,iCCity, ICState,iCZip1,iCZip2,iCPhone1,iCPhone2,iCPhone3,iCID,dateofbirth,mothermid,fathermid,bloodtype,ethnicity,gender,topicalnotes)VALUES(3,'Needs','Care','fake@email.com','1247 Noname Dr','Suite 106','Raleigh', 'NC','27606','1234','919','971','0000','Mum','704','532','2117','Aetna', '1234 Aetna Blvd', 'Suite 602','Charlotte','NC','28215','','704','555','1234', 'ChetumNHowe', '1950-05-10',1,0,'AB+','African American','Male','') ON DUPLICATE KEY UPDATE MID = MID;
INSERT INTO Users(MID, password, role, sQuestion, sAnswer) 			VALUES (3, 'pw', 'patient', 'opposite of yin', 'yang') ON DUPLICATE KEY UPDATE MID = MID;
INSERT INTO PersonalHealthInformation(PatientID,Height,Weight,Smoker,BloodPressureN,BloodPressureD,CholesterolHDL,CholesterolLDL,CholesterolTri,HCPID, AsOfDate)VALUES ( 3,  72,   180,   0,      100,          100,           40,             100,         100,          9000000000, '2005-06-07 20:33:58') ON DUPLICATE KEY UPDATE PatientID = PatientID;
INSERT INTO OfficeVisits(id,visitDate,HCPID,notes,HospitalID,PatientID)VALUES (111,'2005-10-10',9000000000,'Old office visit.','',3) ON DUPLICATE KEY UPDATE id = id;
INSERT INTO OVDiagnosis(ICDCode, VisitID) VALUES (487.00, 111) ON DUPLICATE KEY UPDATE id = id;
INSERT INTO DeclaredHCP(PatientID,HCPID) VALUE(3, 9000000003) ON DUPLICATE KEY UPDATE PatientID = PatientID;
INSERT INTO Representatives(RepresenterMID, RepresenteeMID) VALUES(2,3) ON DUPLICATE KEY UPDATE RepresenterMID = RepresenterMID;
INSERT INTO Patients(MID,lastName, firstName,email,address1,address2,city,state,zip1,zip2,phone1,phone2,phone3,eName,ePhone1,ePhone2,ePhone3,iCName,iCAddress1,iCAddress2,iCCity, ICState,iCZip1,iCZip2,iCPhone1,iCPhone2,iCPhone3,iCID,dateofbirth,mothermid,fathermid,bloodtype,ethnicity,gender,topicalnotes)VALUES(4,'Has','NoRecords','fake@email.com','1247 Noname Dr','Suite 106','Raleigh', 'NC','27606','1234','919','971','0000','Mum','704','532','2117','Aetna', '1234 Aetna Blvd', 'Suite 602','Charlotte','NC','28215','','704','555','1234', 'ChetumNHowe', '1950-05-10',1,0,'AB+','African American','Male','')ON DUPLICATE KEY UPDATE MID = MID;
INSERT INTO Users(MID, password, role, sQuestion, sAnswer) 			VALUES (4, 'pw', 'patient', 'opposite of yin?', 'yang')ON DUPLICATE KEY UPDATE MID = MID;
INSERT INTO Users(MID, password, role, sQuestion, sAnswer) 			VALUES (5, 'pw', 'patient', 'opposite of yin?', 'yang');
INSERT INTO Patients(MID,lastName, firstName,email,address1,address2,city,state,zip1,zip2,phone1,phone2,phone3,eName,ePhone1,ePhone2,ePhone3,iCName,iCAddress1,iCAddress2,iCCity, ICState,iCZip1,iCZip2,iCPhone1,iCPhone2,iCPhone3,iCID,dateofbirth,mothermid,fathermid,bloodtype,ethnicity,gender,topicalnotes)VALUES(5,'Programmer','Baby','fake@email.com','1247 Noname Dr','Suite 106','Raleigh', 'NC','27606','1234','919','971','0000','Mum','704','532','2117','Aetna', '1234 Aetna Blvd', 'Suite 602','Charlotte','NC','28215','','704','555','1234', 'ChetumNHowe', '1995-05-10',0,2,'AB+','African American','Female','');
INSERT INTO Representatives(RepresenterMID, RepresenteeMID) VALUES(2, 5);
INSERT INTO declaredhcp(patientid, hcpid) VALUES (5, 9000000000);
INSERT INTO OfficeVisits(id, visitDate, HCPID, notes, PatientID, HospitalID)VALUES	(1000, '1995-05-10', 9000000000, 'Hep B Immunization 1', '5', null),	(1001, '1995-06-10', 9000000000, 'Hep B Immunization 2', '5', null),	(1002, '1995-11-10', 9000000000, 'Hep B Immunization 3', '5', null),	(1003, '1995-06-22', 9000000000, 'Rotavirus Immunization 1', '5', null),	(1004, '1995-09-10', 9000000000, 'Rotavirus Immunization 2', '5', null),	(1005, '1995-11-10', 9000000000, 'Rotavirus Immunization 3', '5', null),	(1006, '1995-06-22', 9000000000, 'Diphtheria, Tetanus, Pertussis Immunization 1', '5', null),	(1007, '1995-09-10', 9000000000, 'Diphtheria, Tetanus, Pertussis Immunization 2', '5', null),	(1008, '1995-11-10', 9000000000, 'Diphtheria, Tetanus, Pertussis Immunization 3', '5', null),	(1009, '1996-08-10', 9000000000, 'Diphtheria, Tetanus, Pertussis Immunization 4', '5', null),	(1010, '1999-05-10', 9000000000, 'Diphtheria, Tetanus, Pertussis Immunization 5', '5', null),	(1011, '2006-05-10', 9000000000, 'Diphtheria, Tetanus, Pertussis Immunization 6', '5', null),	(1012, '1995-06-22', 9000000000, 'Haemophilus influenzae Immunization 1', '5', null),	(1013, '1995-09-10', 9000000000, 'Haemophilus influenzae Immunization 2', '5', null),	(1014, '1996-05-10', 9000000000, 'Haemophilus influenzae Immunization 3', '5', null),	(1015, '1995-06-22', 9000000000, 'Pneumococcal Immunization 1', '5', null),	(1016, '1995-09-10', 9000000000, 'Pneumococcal Immunization 2', '5', null),	(1017, '1995-11-10', 9000000000, 'Pneumococcal Immunization 3', '5', null),	(1018, '1996-05-10', 9000000000, 'Pneumococcal Immunization 4', '5', null),	(1019, '1995-06-22', 9000000000, 'Poliovirus 1', '5', null),	(1020, '1995-09-10', 9000000000, 'Poliovirus 2', '5', null),	(1021, '1995-11-10', 9000000000, 'Poliovirus 3', '5', null),	(1022, '1996-05-10', 9000000000, 'Measles, Mumps, Rubella Immunization 1', '5', null),	(1023, '1999-05-10', 9000000000, 'Measles, Mumps, Rubella Immunization 2', '5', null),	(1024, '1996-05-10', 9000000000, 'Varicella Immunization 1', '5', null),	(1025, '1999-05-10', 9000000000, 'Varicella Immunization 2', '5', null),	(1026, '1996-05-10', 9000000000, 'Hep A Immunization 1', '5', null),	(1027, '1996-11-10', 9000000000, 'Hep A Immunization 2', '5', null),	(1028, '2004-05-10', 9000000000, 'Human Papillomavirus Immunization 1', '5', null),	(1029, '2004-07-10', 9000000000, 'Human Papillomavirus Immunization 2', '5', null),	(1030, '2004-11-10', 9000000000, 'Human Papillomavirus Immunization 3', '5', null);
INSERT INTO ovprocedure(id, visitid, cptcode)VALUES	(1000, 1000, "90371"),	(1001, 1001, "90371"),	(1002, 1002, "90371"),	(1003, 1003, "90681"),	(1004, 1004, "90681"),	(1005, 1005, "90681"),	(1006, 1006, "90696"),	(1007, 1007, "90696"),	(1008, 1008, "90696"),	(1009, 1009, "90696"),	(1010, 1010, "90696"),	(1011, 1011, "90696"),	(1012, 1012, "90645"),	(1013, 1013, "90645"),	(1014, 1014, "90645"),	(1015, 1015, "90669"),	(1016, 1016, "90669"),	(1017, 1017, "90669"),	(1018, 1018, "90669"),	(1019, 1019, "90712"),	(1020, 1020, "90712"),	(1021, 1021, "90712"),	(1022, 1022, "90707"),	(1023, 1023, "90707"),	(1024, 1024, "90396"),	(1025, 1025, "90396"),	(1026, 1026, "90633"),	(1027, 1027, "90633"),	(1028, 1028, "90649"),	(1029, 1029, "90649"),	(1030, 1030, "90649");
	INSERT INTO PersonalHealthInformation(PatientID,Height,Weight,Smoker,BloodPressureN,BloodPressureD,CholesterolHDL,CholesterolLDL,CholesterolTri,HCPID, AsOfDate)VALUES ( 5,  60,   200,   0,      190,          100,           500,             239,         290,          9000000000, '2007-06-07 20:33:58');
INSERT INTO Patients(MID,lastName, firstName,email,address1,address2,city,state,zip1,zip2,phone1,phone2,phone3,eName,ePhone1,ePhone2,ePhone3,iCName,iCAddress1,iCAddress2,iCCity, ICState,iCZip1,iCZip2,iCPhone1,iCPhone2,iCPhone3,iCID,dateofbirth,mothermid,fathermid,bloodtype,ethnicity,gender,topicalnotes)VALUES(6,'A','Baby','fake@email.com','1247 Noname Dr','Suite 106','Raleigh', 'NC','27606','1234','919','971','0000','Mum','704','532','2117','Aetna', '1234 Aetna Blvd', 'Suite 602','Charlotte','NC','28215','','704','555','1234', 'ChetumNHowe', '1995-05-10',0,2,'AB+','African American','Female','');
INSERT INTO Users(MID, password, role, sQuestion, sAnswer) VALUES (6, 'pw', 'patient', 'opposite of yin?', 'yang');
INSERT INTO Representatives(RepresenterMID, RepresenteeMID) VALUES(2, 6);
INSERT INTO declaredhcp(patientid, hcpid) VALUES (6, 9000000000);
INSERT INTO OfficeVisits(id, visitDate, HCPID, notes, PatientID, HospitalID)VALUES	(2000, '1995-05-10', 9000000000, 'Hep B Immunization 1', '6', null),	(2001, '1995-06-10', 9000000000, 'Hep B Immunization 2', '6', null),	(2003, '1995-06-22', 9000000000, 'Rotavirus Immunization 1', '6', null),	(2004, '1995-09-10', 9000000000, 'Rotavirus Immunization 2', '6', null),	(2006, '1995-06-22', 9000000000, 'Diphtheria, Tetanus, Pertussis Immunization 1', '6', null),	(2007, '1995-09-10', 9000000000, 'Diphtheria, Tetanus, Pertussis Immunization 2', '6', null),	(2008, '1995-11-10', 9000000000, 'Diphtheria, Tetanus, Pertussis Immunization 3', '6', null),	(2012, '1995-06-22', 9000000000, 'Haemophilus influenzae Immunization 1', '6', null),	(2013, '1995-09-10', 9000000000, 'Haemophilus influenzae Immunization 2', '6', null),	(2014, '1996-05-10', 9000000000, 'Haemophilus influenzae Immunization 3', '6', null),	(2015, '1995-06-22', 9000000000, 'Pneumococcal Immunization 1', '6', null),	(2016, '1995-09-10', 9000000000, 'Pneumococcal Immunization 2', '6', null),	(2017, '1995-11-10', 9000000000, 'Pneumococcal Immunization 3', '6', null),	(2019, '1995-06-22', 9000000000, 'Poliovirus 1', '6', null),	(2020, '1995-09-10', 9000000000, 'Poliovirus 2', '6', null),	(2021, '1995-11-10', 9000000000, 'Poliovirus 3', '6', null),	(2022, '1996-05-10', 9000000000, 'Measles, Mumps, Rubella Immunization 1', '6', null),	(2023, '1999-05-10', 9000000000, 'Measles, Mumps, Rubella Immunization 2', '6', null),	(2024, '1996-05-10', 9000000000, 'Varicella Immunization 1', '6', null),	(2025, '1999-05-10', 9000000000, 'Varicella Immunization 2', '6', null),	(2026, '1996-05-10', 9000000000, 'Hep A Immunization 1', '6', null),	(2027, '1996-11-10', 9000000000, 'Hep A Immunization 2', '6', null),	(2028, '2004-05-10', 9000000000, 'Human Papillomavirus Immunization 1', '6', null),	(2029, '2004-07-10', 9000000000, 'Human Papillomavirus Immunization 2', '6', null);
INSERT INTO ovprocedure(id, visitid, cptcode)VALUES	(2000, 2000, "90371"),	(2001, 2001, "90371"),	(2003, 2003, "90681"),	(2004, 2004, "90681"),	(2006, 2006, "90696"),	(2007, 2007, "90696"),	(2008, 2008, "90696"),	(2012, 2012, "90645"),	(2013, 2013, "90645"),	(2014, 2014, "90645"),	(2015, 2015, "90669"),	(2016, 2016, "90669"),	(2017, 2017, "90669"),	(2019, 2019, "90712"),	(2020, 2020, "90712"),	(2021, 2021, "90712"),	(2022, 2022, "90707"),	(2023, 2023, "90707"),	(2024, 2024, "90396"),	(2025, 2025, "90396"),	(2026, 2026, "90633"),	(2027, 2027, "90633"),	(2028, 2028, "90649"),	(2029, 2029, "90649");
INSERT INTO Patients(MID,lastName, firstName,email,address1,address2,city,state,zip1,zip2,phone1,phone2,phone3,eName,ePhone1,ePhone2,ePhone3,iCName,iCAddress1,iCAddress2,iCCity, ICState,iCZip1,iCZip2,iCPhone1,iCPhone2,iCPhone3,iCID,dateofbirth,mothermid,fathermid,bloodtype,ethnicity,gender,topicalnotes)VALUES(7,'B','Baby','fake@email.com','1247 Noname Dr','Suite 106','Raleigh', 'NC','27606','1234','919','971','0000','Mum','704','532','2117','Aetna', '1234 Aetna Blvd', 'Suite 602','Charlotte','NC','28215','','704','555','1234', 'ChetumNHowe', '1995-05-10',0,2,'AB+','African American','Male','');
INSERT INTO Users(MID, password, role, sQuestion, sAnswer) 			VALUES (7, 'pw', 'patient', 'opposite of yin?', 'yang');
INSERT INTO Representatives(RepresenterMID, RepresenteeMID) VALUES(2, 7);
INSERT INTO declaredhcp(patientid, hcpid) VALUES (7, 9000000000);
INSERT INTO OfficeVisits(id, visitDate, HCPID, notes, PatientID, HospitalID)VALUES	(3000, '1995-05-10', 9000000000, 'Hep B Immunization 1', '7', null),	(3001, '1995-06-10', 9000000000, 'Hep B Immunization 2', '7', null),	(3002, '1995-11-10', 9000000000, 'Hep B Immunization 3', '7', null),	(3003, '1995-06-22', 9000000000, 'Rotavirus Immunization 1', '7', null),	(3004, '1995-09-10', 9000000000, 'Rotavirus Immunization 2', '7', null),	(3005, '1995-11-10', 9000000000, 'Rotavirus Immunization 3', '7', null),	(3006, '1995-06-22', 9000000000, 'Diphtheria, Tetanus, Pertussis Immunization 1', '7', null),	(3007, '1995-09-10', 9000000000, 'Diphtheria, Tetanus, Pertussis Immunization 2', '7', null),	(3008, '1995-11-10', 9000000000, 'Diphtheria, Tetanus, Pertussis Immunization 3', '7', null),	(3009, '1996-08-10', 9000000000, 'Diphtheria, Tetanus, Pertussis Immunization 4', '7', null),	(3010, '1999-05-10', 9000000000, 'Diphtheria, Tetanus, Pertussis Immunization 5', '7', null),	(3011, '2006-05-10', 9000000000, 'Diphtheria, Tetanus, Pertussis Immunization 6', '7', null),	(3012, '1995-06-22', 9000000000, 'Haemophilus influenzae Immunization 1', '7', null),	(3013, '1995-09-10', 9000000000, 'Haemophilus influenzae Immunization 2', '7', null),	(3014, '1996-05-10', 9000000000, 'Haemophilus influenzae Immunization 3', '7', null),	(3015, '1995-06-22', 9000000000, 'Pneumococcal Immunization 1', '7', null),	(3016, '1995-09-10', 9000000000, 'Pneumococcal Immunization 2', '7', null),	(3017, '1995-11-10', 9000000000, 'Pneumococcal Immunization 3', '7', null),	(3018, '1996-05-10', 9000000000, 'Pneumococcal Immunization 4', '7', null),	(3019, '1995-06-22', 9000000000, 'Poliovirus 1', '7', null),	(3020, '1995-09-10', 9000000000, 'Poliovirus 2', '7', null),	(3021, '1995-11-10', 9000000000, 'Poliovirus 3', '7', null),	(3022, '1996-05-10', 9000000000, 'Measles, Mumps, Rubella Immunization 1', '7', null),	(3023, '1999-05-10', 9000000000, 'Measles, Mumps, Rubella Immunization 2', '7', null),	(3024, '1996-05-10', 9000000000, 'Varicella Immunization 1', '7', null),	(3025, '1999-05-10', 9000000000, 'Varicella Immunization 2', '7', null),	(3026, '1996-05-10', 9000000000, 'Hep A Immunization 1', '7', null),	(3027, '1996-11-10', 9000000000, 'Hep A Immunization 2', '7', null);
INSERT INTO ovprocedure(id, visitid, cptcode)VALUES	(3000, 3000, "90371"),	(3001, 3001, "90371"),	(3002, 3002, "90371"),	(3003, 3003, "90681"),	(3004, 3004, "90681"),	(3005, 3005, "90681"),	(3006, 3006, "90696"),	(3007, 3007, "90696"),	(3008, 3008, "90696"),	(3009, 3009, "90696"),	(3010, 3010, "90696"),	(3011, 3011, "90696"),	(3012, 3012, "90645"),	(3013, 3013, "90645"),	(3014, 3014, "90645"),	(3015, 3015, "90669"),	(3016, 3016, "90669"),	(3017, 3017, "90669"),	(3018, 3018, "90669"),	(3019, 3019, "90712"),	(3020, 3020, "90712"),	(3021, 3021, "90712"),	(3022, 3022, "90707"),	(3023, 3023, "90707"),	(3024, 3024, "90396"),	(3025, 3025, "90396"),	(3026, 3026, "90633"),	(3027, 3027, "90633");
INSERT INTO Patients(MID,lastName, firstName,email,address1,address2,city,state,zip1,zip2,phone1,phone2,phone3,eName,ePhone1,ePhone2,ePhone3,iCName,iCAddress1,iCAddress2,iCCity, ICState,iCZip1,iCZip2,iCPhone1,iCPhone2,iCPhone3,iCID,dateofbirth,mothermid,fathermid,bloodtype,ethnicity,gender,topicalnotes)VALUES(8,'C','Baby','fake@email.com','1247 Noname Dr','Suite 106','Raleigh', 'NC','27606','1234','919','971','0000','Mum','704','532','2117','Aetna', '1234 Aetna Blvd', 'Suite 602','Charlotte','NC','28215','','704','555','1234', 'ChetumNHowe', '1995-05-10',0,2,'AB+','African American','Male','');
INSERT INTO Users(MID, password, role, sQuestion, sAnswer) 			VALUES (8, 'pw', 'patient', 'opposite of yin?', 'yang');
INSERT INTO Representatives(RepresenterMID, RepresenteeMID) VALUES(2, 8);
INSERT INTO declaredhcp(patientid, hcpid) VALUES (8, 9000000000);
INSERT INTO OfficeVisits(id, visitDate, HCPID, notes, PatientID, HospitalID)VALUES	(4000, '1995-05-10', 9000000000, 'Hep B Immunization 1', '8', null),	(4003, '1995-06-22', 9000000000, 'Rotavirus Immunization 1', '8', null),	(4004, '1995-09-10', 9000000000, 'Rotavirus Immunization 2', '8', null),	(4005, '1995-11-10', 9000000000, 'Rotavirus Immunization 3', '8', null),	(4012, '1995-06-22', 9000000000, 'Haemophilus influenzae Immunization 1', '8', null),	(4013, '1995-09-10', 9000000000, 'Haemophilus influenzae Immunization 2', '8', null),	(4014, '1996-05-10', 9000000000, 'Haemophilus influenzae Immunization 3', '8', null),	(4015, '1995-06-22', 9000000000, 'Pneumococcal Immunization 1', '8', null),	(4016, '1995-09-10', 9000000000, 'Pneumococcal Immunization 2', '8', null),	(4017, '1995-11-10', 9000000000, 'Pneumococcal Immunization 3', '8', null),	(4018, '1996-05-10', 9000000000, 'Pneumococcal Immunization 4', '8', null),	(4019, '1995-06-22', 9000000000, 'Poliovirus 1', '8', null),	(4020, '1995-09-10', 9000000000, 'Poliovirus 2', '8', null),	(4021, '1995-11-10', 9000000000, 'Poliovirus 3', '8', null),	(4022, '1996-05-10', 9000000000, 'Measles, Mumps, Rubella Immunization 1', '8', null),	(4023, '1999-05-10', 9000000000, 'Measles, Mumps, Rubella Immunization 2', '8', null),	(4026, '1996-05-10', 9000000000, 'Hep A Immunization 1', '8', null);
INSERT INTO ovprocedure(id, visitid, cptcode)VALUES	(4000, 4000, "90371"),	(4003, 4003, "90681"),	(4004, 4004, "90681"),	(4005, 4005, "90681"),	(4012, 4012, "90645"),	(4013, 4013, "90645"),	(4014, 4014, "90645"),	(4015, 4015, "90669"),	(4016, 4016, "90669"),	(4017, 4017, "90669"),	(4018, 4018, "90669"),	(4019, 4019, "90712"),	(4020, 4020, "90712"),	(4021, 4021, "90712"),	(4022, 4022, "90707"),	(4023, 4023, "90707"),	(4026, 4026, "90633");
INSERT INTO Patients(MID,lastName, firstName,email,address1,address2,city,state,zip1,zip2,phone1,phone2,phone3,eName,ePhone1,ePhone2,ePhone3,iCName,iCAddress1,iCAddress2,iCCity, ICState,iCZip1,iCZip2,iCPhone1,iCPhone2,iCPhone3,iCID,dateofbirth,dateofdeath,mothermid,fathermid,bloodtype,ethnicity,gender,topicalnotes)VALUES(20,'Koopa','Bowser','bkoopa@gmail.com','123 Grumpy Dr','','Raleigh', 'NC','27606','','234','123','4567','Mom','333','222','2345','Aetna', '1234 Aetna Blvd', 'Suite 602','Charlotte','NC','28215','','704','555','1234', 'ChetumNHowe', '2008-09-05',NULL,1,0,'AB+','African American','Male','');
INSERT INTO Users(MID, password, role, sQuestion, sAnswer) VALUES (20, 'pw', 'patient', 'first letter?', 'a');
INSERT INTO DeclaredHCP(PatientID,HCPID)VALUE(20, 9000000003);
INSERT INTO Patients(MID,lastName, firstName,email,address1,address2,city,state,zip1,zip2,phone1,phone2,phone3,eName,ePhone1,ePhone2,ePhone3,iCName,iCAddress1,iCAddress2,iCCity, ICState,iCZip1,iCZip2,iCPhone1,iCPhone2,iCPhone3,iCID,dateofbirth,dateofdeath,mothermid,fathermid,bloodtype,ethnicity,gender,topicalnotes)VALUES(21,'Peach','Princess','peach@gmail.com','123 Happy Lane','','Raleigh', 'NC','27603','','222','222','1234','Mario','888','333','8942','Aetna', '1234 Aetna Blvd', 'Suite 602','Charlotte','NC','28215','','704','555','1234', 'ChetumNHowe', '2008-06-15',NULL,1,0,'AB+','Caucasian','Female','');
INSERT INTO Users(MID, password, role, sQuestion, sAnswer) VALUES (21, 'pw', 'patient', 'first letter?', 'a');
INSERT INTO DeclaredHCP(PatientID,HCPID)VALUE(21, 9000000003);
INSERT INTO Patients(MID, firstName,lastName, email,address1,address2,city,state,zip1,zip2,phone1,phone2,phone3,eName,ePhone1,ePhone2,ePhone3,iCName,iCAddress1,iCAddress2,iCCity, ICState,iCZip1,iCZip2,iCPhone1,iCPhone2,iCPhone3,iCID,DateOfBirth,MotherMID,FatherMID,BloodType,Ethnicity,Gender,TopicalNotes)VALUES (22,'Fozzie','Bear','wakka.wakka@wakka.com','344 Henson Street','','New York','NY','10001','','555','555','5555','Kermit the Frog','555','555','5551','IC','Street1','Street2','City','PA','19003','2715','555','555','5555','1','1976-04-25',0,0,'O-','Ursine','Male',"Fozzie has a low tolerance for pain--he simply can\'t bear it.  Wakka, Wakka, Wakka.")  ON DUPLICATE KEY UPDATE MID = MID;
INSERT INTO Users(MID, password, role, sQuestion, sAnswer) 			VALUES (22, 'pw', 'patient', 'how you doin?', 'good') ON DUPLICATE KEY UPDATE MID = MID;
INSERT INTO OfficeVisits(id,visitDate,HCPID,notes,HospitalID,PatientID)VALUES (1952,'2010-6-09',9000000007,'Meep meep meep?','1',22),	   (1953,'2010-10-31',9000000000,'If you say Wakka, Wakka, Wakka one more time...','1',22),	   (1954,'2010-12-11',9000000003,'Wakka Wakka this!','1',22),	   (1955,'2011-3-31',9000000007,'MEEP. Meep Meep.','1',22)		 ON DUPLICATE KEY UPDATE id = id;
INSERT INTO DeclaredHCP(PatientID,HCPID) VALUE(22, 9000000007) ON DUPLICATE KEY UPDATE PatientID = PatientID;
INSERT INTO referrals (    id,    OVID,    PatientID,    SenderID,    ReceiverID,     TimeStamp,    PriorityCode,    ReferralDetails,     viewed_by_patient,    viewed_by_HCP) VALUES     (19520, 1952, 22, 9000000007, 9000000000, '2010-06-10', 2, 'Meep meep meep.', false, false),    (19521, 1952, 22, 9000000007, 9000000003, '2010-06-12', 3, 'Meep meep meep!', false, false),    (19522, 1952, 22, 9000000007, 9000000000, '2010-06-14', 3, 'Meep Meep Meep.', false, false),    (19523, 1952, 22, 9000000007, 9000000003, '2010-06-16', 2, 'Meep MEEP Meep...', false, false),    (19524, 1952, 22, 9000000007, 9000000000, '2010-06-18', 1, 'meep meep meep', false, false),    (19525, 1952, 22, 9000000007, 9000000003, '2010-06-20', 1, 'MEEP MEEP MEEP...', false, false),    (19550, 1955, 22, 9000000007, 9000000000, '2010-06-22', 3, 'Meep meep... Meep.', false, false),    (19551, 1955, 22, 9000000007, 9000000000, '2010-06-13', 2, 'Meep meep, meep.', false, false)ON DUPLICATE KEY UPDATE id = id;
INSERT INTO LabProcedure(PatientMID,LaboratoryProcedureCode,Rights,Status,Commentary,Results, NumericalResults, LowerBound, UpperBound, OfficeVisitID, UpdatedDate, LabTechID, PriorityCode)VALUES(22,'10763-1','ALLOWED','In Transit','','', '','','', 1952,'2010-06-09', 5000000001, 3), (22,'10666-6','ALLOWED','In Transit','','', '','','', 1952,'2010-06-13', 5000000001, 1), (22,'10640-1','ALLOWED','In Transit','','', '','','', 1952,'2010-06-17', 5000000002, 2), (22,'10640-1','ALLOWED', 'Received','','', '','','', 1952,'2010-06-10', 5000000001, 1), (22,'10763-1','ALLOWED', 'Received','','', '','','', 1952, '2010-06-14', 5000000002, 3), (22,'10763-1','ALLOWED','Received','','', '','','', 1952,'2010-06-18', 5000000002, 2), (22,'10763-1','ALLOWED','Pending','','', '5','4','6', 1952,'2010-06-11', 5000000001, 2), (22,'10763-1','ALLOWED','Pending','','', '13','11','15', 1952,'2010-06-15', 5000000001, 1), (22,'10763-1','ALLOWED','Pending','','', '3','2','4', 1952,'2010-06-19', 5000000002, 3), (22,'10763-1','ALLOWED','Completed','Meep meep meep.','', '7','6','8', 1952,'2010-06-12', 5000000001, 2), (22,'10763-1','ALLOWED','Completed','MEEP MEEP MEEP!','', '5.23','4.92','6.03', 1952,'2010-06-16', 5000000001, 3), (22,'10763-1','ALLOWED','Completed','Meep, meep. MEEP!!!','', '18','10','17256', 1952,'2010-06-20', 5000000002, 1);
INSERT INTO Patients(MID, lastName, firstName,email,address1,address2,city,state,zip1,zip2,phone1,phone2,phone3,eName,ePhone1,ePhone2,ePhone3,iCName,iCAddress1,iCAddress2,iCCity, ICState,iCZip1,iCZip2,iCPhone1,iCPhone2,iCPhone3,iCID,dateofbirth,mothermid,fathermid,bloodtype,ethnicity,gender, topicalnotes)VALUES(42,'Horse', 'Bad', 'badHorse@ele.org', '1247 Noname Dr', 'Suite 106', 'Raleigh', 'NC', '27606','1234', '919','123','4567', 'Mommy Person', '704','123','4567', 'Aetna', '1234 Aetna Blvd', 'Suite 602', 'Charlotte','NC', '28215','', '704','555','1234', 'ChetumNHowe', '1950-05-10',0,0,'AB+','African American','Male','Beware his terrible Death Whinny!') ON DUPLICATE KEY UPDATE MID = MID;
INSERT INTO Users(MID, password, role, sQuestion, sAnswer) 			VALUES (42, 'pw', 'patient', 'what is your favorite color?', 'blue') ON DUPLICATE KEY UPDATE MID = MID;
INSERT INTO DeclaredHCP(PatientID,HCPID) VALUE(1, 9000000000) ON DUPLICATE KEY UPDATE PatientID = PatientID;
 INSERT INTO OfficeVisits(ID, visitDate, HCPID, notes, PatientID, HospitalID)	VALUES (5001, '2010-11-28', 9000000000, 'Voice is a little hoarse.', 42, 1)	ON DUPLICATE KEY UPDATE ID = ID;
	DELETE FROM RemoteMonitoringLists where PatientMID = '42';
DELETE FROM RemoteMonitoringData where PatientID = '42';
INSERT INTO RemoteMonitoringLists(PatientMID, HCPMID, systolicBloodPressure, diastolicBloodPressure)					VALUES (42, 9000000000, 1, 1);
DELETE FROM PersonalHealthInformation WHERE PatientID = 42;
INSERT INTO PersonalHealthInformation(PatientID, Weight, Height, AsOfDate, Smoker, HCPID)	VALUES	(42, 280, 70, '2010-11-25 05:30:00', 1, 9000000000),			(42, 282, 70, '2010-8-13 05:30:00', 1, 9000000000),			(42, 281.5, 70, '2010-5-19 05:30:00', 1, 9000000000),			(42, 285, 70, '2010-2-22 05:30:00', 1, 9000000000),			(42, 279.7, 70, '2009-12-29 05:30:00', 1, 9000000000),			(42, 280, 70, '2009-7-22 05:30:00', 1, 9000000000),			(42, 280, 70, '2009-5-23 05:30:00', 1, 9000000000),			(42, 280, 70, '2009-3-15 05:30:00', 1, 9000000000),			(42, 290.3, 70, '2008-12-19 05:30:00', 1, 9000000000),			(42, 293.1, 70, '2008-8-18 05:30:00', 1, 9000000000),			(42, 296, 70, '2008-4-10 05:30:00', 1, 9000000000),			(42, 294.2, 70, '2008-2-10 05:30:00', 1, 9000000000);
	INSERT INTO Appointment(doctor_id, patient_id, sched_date, appt_type)	VALUE	(9000000000, 42, CONCAT(SUBDATE(CURDATE(),1), ' 15:00:00.0'), 'Consultation');
INSERT INTO Personnel(MID,AMID,role,lastName, firstName, address1,address2,city,state,zip,zip1,zip2,phone,phone1,phone2,phone3,specialty) VALUES (9000000001,null,'admin','Shifter','Shape','4321 My Road St','PO BOX 2','CityName','NY','12345-1234','12345','1234','999-888-7777','999','888','7777','administrator')  ON DUPLICATE KEY UPDATE MID = MID;
INSERT INTO Users(MID, password, role, sQuestion, sAnswer) VALUES(9000000001, 'pw', 'admin', 'first letter?', 'a') ON DUPLICATE KEY UPDATE MID = MID;
INSERT INTO Personnel(MID,AMID,role,lastName, firstName, address1,address2,city,state,zip,zip1,zip2,phone,phone1,phone2,phone3,specialty) VALUES (9000000002,null,'admin','Bob','Marley','4321 My Road St','PO BOX 2','CityName','NY','12345-1234','12345','1234','999-888-7777','999','888','7777','administrator') ON DUPLICATE KEY UPDATE MID = MID;
INSERT INTO Users(MID, password, role, sQuestion, sAnswer) VALUES(9000000002, 'pw', 'admin', 'first letter?', 'a')ON DUPLICATE KEY UPDATE MID = MID;
INSERT INTO Personnel(MID,AMID,role,lastName, firstName, address1,address2,city,state,zip,zip1,zip2,phone,phone1,phone2,phone3,specialty) VALUES (9000000009,null,'admin','Reminder','System','4321 My Road St','PO BOX 2','CityName','NY','12345-1234','12345','1234','999-888-7777','999','888','7777','administrator') ON DUPLICATE KEY UPDATE MID = MID;
INSERT INTO Users(MID, password, role, sQuestion, sAnswer) VALUES(9000000009, 'pw', 'admin', 'first letter?', 'a')ON DUPLICATE KEY UPDATE MID = MID;
INSERT INTO Personnel(MID,AMID,lastName,firstName,address1,address2,city,state,zip1,zip2,phone1,phone2,phone3)VALUES (8000000009,9000000000,'LastUAP','FirstUAP','100 Ave','','Raleigh','NC','27607','','111','111','1111');
INSERT INTO Users(MID, password, role, sQuestion, sAnswer) VALUES (8000000009, 'uappass1', 'uap', 'opposite of yin', 'yang');
INSERT INTO OfficeVisits(	id,	visitDate,	HCPID,	notes,	HospitalID,	PatientID)VALUES (1,'2005-10-10',9000000000,'Generated for Death for Patient: 1','1',1);
INSERT INTO referrals(id,PatientID,SenderID,ReceiverID, ReferralDetails, OVID,viewed_by_patient,viewed_by_HCP,TimeStamp,PriorityCode)VALUES (1,2,9000000000,9000000003,'Gandalf will make sure that the virus does not get past your immune system',955,false,false,'2007-7-15',1)ON DUPLICATE KEY UPDATE id = id;
INSERT INTO referrals(id,PatientID,SenderID,ReceiverID, ReferralDetails, OVID,viewed_by_patient,viewed_by_HCP,TimeStamp,PriorityCode)VALUES (2,2,9000000000,9000000003,'Gandalf will take care of you.',955,true,false,'2007-7-09',2)ON DUPLICATE KEY UPDATE id = id;
INSERT INTO referrals(id,PatientID,SenderID,ReceiverID, ReferralDetails, OVID,viewed_by_patient,viewed_by_HCP,TimeStamp,PriorityCode)VALUES (3,2,9000000000,9000000003,'Gandalf will help you defeat the orcs!',955,false,true,'2007-7-14',1)ON DUPLICATE KEY UPDATE id = id;
INSERT INTO referrals(id,PatientID,SenderID,ReceiverID, ReferralDetails, OVID,viewed_by_patient,viewed_by_HCP,TimeStamp,PriorityCode)VALUES (4,2,9000000000,9000000003,'Gandalf is the best doctor ever!!',955,true,true,'2007-7-10',3)ON DUPLICATE KEY UPDATE id = id;
INSERT INTO message(message_id,parent_msg_id,from_id,to_id,sent_date,message,subject,been_read)VALUES('1',null,'9000000000','2','2010-01-21 11:32:00','I just updated your office visit.','Office Visit Updated','0'),('2',null,'2','9000000000 ','2010-01-13 13:46:00','Have my lab results returned yet?','Lab Results','1'),('3',null,'1','9000000000','2010-01-19 07:58:00','When is my next scheduled appointment?','Appointment','0'),('4',null,'2','9000000000','2010-01-31 12:12:00','Do I need to schedule another office visit before my prescription can be renewed?','Prescription','0'),('5',null,'5','9000000000','2010-01-07 09:15:00','How often would you like me to report my physiological measurements?','Remote Monitoring Question','0'),('6',null,'2','9000000000','2010-02-02 13:03:00','I would like to schedule an appointment before my throat gets any worse. Thanks!','Scratchy Throat','0'),('7',null,'1','9000000000','2010-01-29 08:01:00','I noticed that there is a medication posted in my last office visit, but you never gave me a prescription!','Office Visit','0'),('8',null,'9000000000','1','2010-01-28 17:58:00','Hey, I checked on that!','Lab Procedure','1'),('9',null,'1','9000000003','2010-01-16 11:55:00','Can I reschedule my appointment for next Monday at 2PM?','Appointment Reschedule','0'),('10',null,'1','9000000000','2010-01-20 16:58:00','Have my lab results have returned? It''s been over 3 weeks!','Lab Results','0'),('11',null,'1','9000000000','2010-01-31 16:01:00','How often should I update per day?','Telemedicine','0'),('12',null,'1','9000000000','2010-01-08 14:59:00','Sorry, I had a flat tire on my way to your office. I will call the office ASAP to reschedule!','Missed Appointment','0'),('13',null,'1','9000000000','2009-12-02 11:15:00','Is it safe to take leftover penicillin from last year when I was sick?','Old Medicine','0'),('14',null,'1','9000000000','2009-12-29 15:33:00','After taking this aspirin, I feel a little woozy. Should I report this as an adverse event?','Aspirin Side Effects','0'),('15',null,'1','9000000000','2010-02-01 09:12:00','I checked on that!','Appointment','1'),('16','8','1','9000000000','2010-01-29 17:58:00','Thank you for checking on this!','RE: Lab Procedure','0'),('17','15','1','9000000000','2010-02-01 09:12:00','Thank you for checking on this!','RE: Appointment','0');
INSERT INTO Users(MID, password, role) VALUES(9999999999, 'pw', 'tester');
INSERT INTO FakeEmail (ToAddr, FromAddr, Subject,Body, AddedDate) values ('gstormcrow@iTrust.org','noreply@itrust.com', 'this is an email', 'hello world', '2007-06-23 06:55:59'),('gstormcrow@iTrust.org,kdoctor@iTrust.org','noreply@itrust.com', 'this is another email', 'hello earth', '2007-06-23 06:55:58'),('andy.programmer@gmail.com','noreply@itrust.com', 'this is another email', 'your appendix is fine','2007-06-23 06:55:57'),('andy.programmer@gmail.com','noreply@itrust.com', 'this is another email', 'come see us ASAP','2007-06-23 06:55:56');
INSERT INTO ReportRequests(ID,RequesterMID,PatientMID,RequestedDate,ViewedDate,Status)VALUES(1,9000000000,2,'2008-01-01 12:00',null,'Requested'),(2,9000000000,2,'2008-01-02 12:00',null,'Requested'),(3,9000000000,2,'2008-01-03 12:00',null,'Requested'),(4,9000000000,2,'2008-01-04 12:00','2008-03-04 12:00','Viewed'),(5,9000000000,1,'2008-01-05 12:00',null,'Requested'),(6,9000000000,1,'2008-06-01 12:00',null,'Requested'),(7,9000000002,3,'2008-01-04 12:00','2008-03-04 12:00','Viewed');
INSERT INTO LOINC(LaboratoryProcedureCode,Component,KindOfProperty,TimeAspect,System,ScaleType,MethodType)VALUES('10763-1','Microscopic Observation','Prid','Pt','Body Fluid','Nominal','Sudan Black Stain'),('10666-6','Fungus identified','Prid','Pt','Tissue','Nominal','Fontana-Masson stain'),('10640-1','Adenovirus 40+41','Prid','Pt','Stool','Nominal','Microscopy.electron'),('13495-7','Specimen volume','Vol','24H','Dial fluid','Qn','<empty>');
INSERT INTO LabProcedure(PatientMID,LaboratoryProcedureCode,Rights,Status,Commentary,Results, NumericalResults, LowerBound, UpperBound, OfficeVisitID, UpdatedDate, LabTechID, PriorityCode)VALUES(1,'10763-1','ALLOWED','In Transit','','', '','','', 11,'2008-05-19', 5000000001, 1),(1,'10666-6','RESTRICTED','Pending','Performed the procedure','', '5.0','4.8','5.1', 11,'2008-05-18', 5000000001, 1),(1,'10640-1','ALLOWED','Completed','Performed the procedure','Patient checks out ok', '88','85','99', 11,'2007-05-17', 5000000001, 2),(2,'10640-1','ALLOWED', 'Completed','Performed the procedure','Patient needs more tests', '2','1','3', 952,'2007-05-19', 5000000001, 3),(2,'10763-1','RESTRICTED', 'In Transit','Performed the procedure','Patient needs more tests', '','','', 955, '2007-07-20', 5000000002, 2),(4,'10763-1','ALLOWED','Completed','','', '2','1','3', 955,'2007-07-20', 5000000002, 3),(4,'10763-1','ALLOWED','Testing','','Patient needs more tests', '','','', 955,'2007-07-20', 5000000001, 3);
UPDATE LabProcedure SET UpdatedDate=(SYSDATE() - INTERVAL 1 DAY) WHERE PatientMID=4;
INSERT INTO OfficeVisits(	id,	visitDate,	HCPID,	notes,	HospitalID,	PatientID)VALUES (902,'2005-10-10',9000000000,'Generated for Death for Patient: 1','1',1),(905,'2005-10-10',9000000000,'Generated for Death for Patient: 1','1',1),(918,'2005-10-10',9000000000,'Generated for Death for Patient: 1','1',1)ON DUPLICATE KEY UPDATE ID = ID;
INSERT INTO appointmenttype(appt_type,duration)VALUES('General Checkup', '45'),('Mammogram', '60'),('Physical', '15'),('Colonoscopy', '90'),('Ultrasound', '30'),('Consultation', '30');
INSERT INTO appointment(doctor_id,patient_id,sched_date,appt_type,comment)VALUES('9000000000', '2', CONCAT(YEAR(NOW())+1, '-06-04 10:30:00'), 'Consultation', 'Consultation for your upcoming surgery.'),('9000000000', '2', CONCAT(YEAR(NOW())+1, '-10-14 08:00:00'), 'Colonoscopy', NULL),('9000000003', '1', CONCAT(YEAR(NOW())+1, '-04-03 15:00:00'), 'Physical', 'This is your yearly physical.'),('9000000000', '2', CONCAT(ADDDATE(CURDATE(),14), ' 10:30:00'), 'General Checkup', 'Checkup to remove your stitches'),('9000000000', '2', CONCAT(YEAR(NOW()), '-',  MONTH(NOW()), '-05 13:30:00'), 'General Checkup', NULL),('9000000000', '1', CONCAT(YEAR(NOW()), '-',  MONTH(NOW()), '-05 09:00:00'), 'General Checkup', NULL),('9000000000', '2', CONCAT(YEAR(NOW()), '-',  MONTH(NOW()), '-05 09:10:00'), 'General Checkup', NULL),('9000000000', '2', CONCAT(YEAR(NOW()), '-',  MONTH(NOW()), '-18 09:00:00'), 'Colonoscopy', NULL),('9000000000', '2', CONCAT(YEAR(NOW()), '-',  MONTH(NOW()), '-28 08:00:00'), 'Physical', NULL),('9000000000', '1', CONCAT(ADDDATE(CURDATE(),21), ' 13:45:00'), 'General Checkup', NULL),('9000000000', '1', CONCAT(ADDDATE(CURDATE(),7), ' 09:10:00'), 'Consultation', NULL),('9000000003', '2', CONCAT(ADDDATE(CURDATE(),7), ' 09:30:00'), 'General Checkup', NULL),('9000000000', '5', CONCAT(ADDDATE(CURDATE(),7), ' 09:30:00'), 'General Checkup', 'Scheduled booster shots'),('9000000000', '1', CONCAT(ADDDATE(CURDATE(),14), ' 13:30:00'), 'Ultrasound', NULL),('9000000000', '2', CONCAT(ADDDATE(CURDATE(),14), ' 13:45:00'), 'General Checkup', NULL),('9000000000', '5', CONCAT(ADDDATE(CURDATE(),10), ' 16:00:00'), 'General Checkup', 'Follow-up for the immunizations.');
INSERT INTO TransactionLog(loggedInMID, secondaryMID, transactionCode, timeLogged, addedInfo)                     VALUES (1, 0, 410, '2007-06-23 06:54:59',''),                           (2, 0, 410, '2007-06-23 06:54:59',''),                           (9000000000, 2, 1900,'2007-06-23 06:55:59','Viewed patient records'),                           (9000000000, 2, 1900,'2007-06-23 06:54:59','Viewed patient records'),                           (9000000000, 2, 1900,'2007-06-25 06:54:59','Viewed patient records'),                           (9000000000, 2, 1900,'2007-06-24 06:54:59','Viewed patient records'),                           (9000000000, 2, 1900,'2007-06-22 06:54:59','Viewed patient records');
DELETE FROM TransactionLog where loggedInMID = 9000000001;
INSERT INTO TransactionLog(loggedInMID, secondaryMID, transactionCode, addedInfo,timeLogged)                    VALUES (9000000001, 0, 3200, 'Added 290.3 Senile dementia with delirium', '2007-06-23 06:54:58');
DELETE FROM TransactionLog where secondaryMID = '2';
INSERT INTO TransactionLog(loggedInMID, secondaryMID, transactionCode, timeLogged, addedInfo)                     VALUES (9000000000, 2, 1600,'2008-03-04 10:15:00','Identified risk factors of chronic diseases'),                           (9000000000, 2, 1102,'2008-09-07 16:30:00','Updated office visit'),                           (9000000003, 2, 1900,'2008-07-15 13:13:00','Viewed prescription report'),                           (9000000006, 2, 2101,'2008-11-14 09:32:00','Viewed emergency report ');
DELETE FROM TransactionLog where secondaryMID = '1';
INSERT INTO TransactionLog(loggedInMID, secondaryMID, transactionCode, timeLogged, addedInfo)                     VALUES (9000000000, 1, 1900,'2008-06-19 11:12:00','Viewed prescription report'),                           (2, 1, 1900,'2008-10-17 14:22:00','Viewed prescription report'),                           (9000000006, 1, 2101,'2008-11-14 10:04:00','Viewed emergency report'),                           (9000000003, 1, 1102,'2008-09-14 16:59:00','Updated office visit'),                           /*(8000000009, 1, 2600,'2008-12-03 12:02:00','Entered/edited laboratory procedures'),*/                           (9000000003, 1, 1600,'2008-04-05 15:12:00','Identified risk factors of chronic diseases'),                           (8000000009, 1, 1600,'2008-07-06 08:34:00','Identified risk factors of chronic diseases'),                           (9000000000, 1, 1600,'2008-06-15 13:15:00','Identified risk factors of chronic diseases'),                           (9000000000, 1, 1102,'2008-12-01 11:30:00','Updated office visit');
DELETE FROM Representatives where representeeMID = '1';
                           INSERT INTO Representatives(representerMID, representeeMID)					VALUES (2,1);
					DELETE FROM DeclaredHCP where PatientID = '1';
INSERT INTO DeclaredHCP(PatientID, HCPID)					VALUES(1, 9000000003);
DELETE FROM Representatives where representeeMID = '2';
  INSERT INTO TransactionLog(loggedInMID, secondaryMID, transactionCode, timeLogged, addedInfo) 						VALUES (1, 2, 1900,'2008-05-12 14:11:00','Viewed prescription report');
INSERT INTO AdverseEvents(id, Status, PatientMID, PresImmu, Code, Comment, Prescriber, TimeLogged) VALUES(1, 'Active', 1, 'Prioglitazone', '647641512', 'IM DYING', 9000000000, '2009-11-09 14:26:11.0'),(8, 'Active', 1, 'Prioglitazone', '647641512', 'IM DYING', 9000000000, '2009-11-09 14:26:11.0'),(3, 'Active', 1, 'Prioglitazone', '647641512', 'IM DYING', 9000000000, '2009-11-09 14:26:11.0'),(4, 'Active', 1, 'Prioglitazone', '647641512', 'IM DYING', 9000000000, '2009-01-09 14:26:11.0'),(5, 'Active', 1, 'Prioglitazone', '647641512', 'IM DYING', 9000000000, '2002-04-09 14:26:11.0'),(6, 'removed', 1, 'Prioglitazone', '647641512', 'IM DYING', 9000000000, '2006-11-09 14:26:11.0'),(7, 'removed', 1, 'Prioglitazone', '647641512', 'IM DYING', 9000000000, '2009-10-09 14:26:11.0'),(2, 'Active', 1, 'Hepatitis B', '90371', 'IM MELTING!', 9000000000, '2009-11-09 14:44:45.0');

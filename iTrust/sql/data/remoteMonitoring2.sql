DELETE FROM RemoteMonitoringLists where HCPMID = '9000000000';

INSERT INTO RemoteMonitoringLists(PatientMID, HCPMID, GlucoseLevel, Weight)
					VALUES (1, 9000000000, 1, 1);
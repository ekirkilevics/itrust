INSERT INTO appointment (doctor_id, patient_id, sched_date, appt_type) VALUES 
(9000000000, 1, '2012-08-20 13:00:00', 'General Checkup'),
(9000000000, 1, '2012-08-20 14:00:00', 'General Checkup'),
(9000000000, 1, '2012-08-20 14:45:00', 'General Checkup'),
(9000000000, 1, '2012-08-20 16:45:00', 'General Checkup');

INSERT INTO AppointmentRequests (doctor_id, patient_id, sched_date, appt_type, comment, pending, accepted) VALUES
(9000000000, 1, '2012-08-22 14:45:00', 'General Checkup', NULL, true, false),
(9000000003, 1, '2012-08-22 16:45:00', 'General Checkup', NULL, true, false),
(9000000000, 2, '2012-08-22 12:45:00', 'Colonoscopy', NULL, true, false);
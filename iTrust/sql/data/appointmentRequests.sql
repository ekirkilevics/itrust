INSERT INTO appointmentRequests 
(ID,RequesterMID,RequestedMID,HospitalID,Date1,Date2,Minutes,Reason,WeeksUntilVisit,Status)
VALUES
(1,9000000000,2,null,null,null,10,'need to check the status of your rash.',2,'Need Patient Confirm'),
(2,2,9000000000,null,'2007-07-25 10:00:00','2007-07-25 16:30:00',20,null,null,'Need LHCP Confirm'),
(3,2,9000000000,8181818181,'2007-07-24 15:00:00',null,30,'I feel sick',null,'Need LHCP Confirm'),
(4,1,9000000000,null,'2007-07-22 11:35:00','2007-07-24 15:00:00',40,'Routine physical',null,'Scheduled'),
(5,1,9000000000,null,'2005-07-01 11:35:00',null,50,'I feel deliriously happy',null,'Rejected'),
(6,9000000003,2,null,null,null,60,'routine checkup',3,'Need Patient Confirm'),
(7,9000000003,2,null,'2005-07-01 11:35:00',null,70,'follow up appointment',null,'Need Patient Confirm'),
(8,9000000003,2,null,null,'2005-07-01 11:35:00',80,'discuss lab results',null,'Need Patient Confirm'),
(9,9000000003,2,null,'2005-07-01 11:35:00','2005-07-02 12:35:00',90,'another follow up',null,'Need Patient Confirm');

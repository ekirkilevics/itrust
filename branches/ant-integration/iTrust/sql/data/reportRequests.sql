INSERT INTO ReportRequests
(ID,RequesterMID,PatientMID,ApproverMID,RequestedDate,ApprovedDate,ViewedDate,Status,Comment)
VALUES
(1,9000000000,2,null,'2008-01-01 12:00',null,null,'Requested',null),
(2,9000000000,2,9000000001,'2008-01-02 12:00','2008-02-02 12:00',null,'Approved',null),
(3,9000000000,2,9000000001,'2008-01-03 12:00','2008-02-03 12:00',null,'Rejected','Forget it'),
(4,9000000000,2,9000000001,'2008-01-04 12:00','2008-02-04 12:00','2008-03-04 12:00','Viewed',null),
(5,9000000000,1,null,'2008-01-05 12:00',null,null,'Requested',null),
(6,9000000000,1,9000000001,'2008-06-01 12:00','2008-02-06 12:00',null,'Approved',null),
(7,9000000002,3,9000000004,'2008-01-04 12:00','2008-02-04 12:00','2008-03-04 12:00','Viewed',null);

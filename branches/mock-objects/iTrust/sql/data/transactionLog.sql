INSERT INTO TransactionLog(loggedInMID, secondaryMID, transactionCode, timeLogged, addedInfo) 
                    VALUES (1, 0, 4, '2007-06-23 06:54:59',''),
                           (2, 0, 4, '2007-06-23 06:54:59',''),
                           (9000000000, 2, 5,'2007-06-23 06:55:59','Viewed patient records'),
                           (9000000000, 2, 5,'2007-06-23 06:54:59','Viewed patient records'),
                           (9000000000, 2, 5,'2007-06-25 06:54:59','Viewed patient records'),
                           (9000000000, 2, 5,'2007-06-24 06:54:59','Viewed patient records'),
                           (9000000000, 2, 5,'2007-06-22 06:54:59','Viewed patient records');

DELETE FROM TransactionLog where loggedInMID = 9000000001;

INSERT INTO TransactionLog(loggedInMID, secondaryMID, transactionCode, addedInfo,timeLogged)
                    VALUES (9000000001, 0, 10, 'Added 290.3 Senile dementia with delirium', '2007-06-23 06:54:58');

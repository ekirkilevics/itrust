This is the README for the READMEs you are required to submit with each assignment.

Please place a README file in the /iTrust/doc/readme folder for each submission.

*** This file is here to help the grading of your assignment ***
*** PLEASE fill this out to help your grade!! ***

Use the file format of assignment_X.txt, where X is the assignment number.

Please note the following in your readme:
+ Anything that you did not finish
+ Any major design decisions you made; interpretations of the assignment that you think the TA should know about
+ The tests you added (doesn't need to be detailed, just the package and a few classes would suffice)
+ Anything else you think the TA should know about before grading

What is err.txt?
For testing purposes, we have created a mock object which mocks the System.out console print stream.
Err.txt is the file which results from the output of a few unit tests which use our mock object. This file 
will almost always be empty and it will get overwritten and rewritten every time you run your unit tests.

Robert Hutton
Siarhei Karpusenka
Tony Shengyen Chen

***The tests we added:
Bug one:
WebRoot/auth/patient/viewPrescriptionRecords.jsp (the bug fix, involves the showAdverseButton boolean)
httptests/ReportAdversePrescriptionTest.java (the HttpUnit test, testReportAdverseEventsButton() method added)

Bug two:
itrust/httptests/PersonalHealthRecordUseCaseTest.java (the HttpUnit test, testInvalidPatientBirthDates() method added)
itrust/validate/PatientValidator.java (the bug fix, fix at lines 39-43)
unittests/validate/bean/PatientValidatorTest.java (the JUnit test, testFutureBirthError() method added)

Bug three:
itrust/httptests/DocumentOfficeVisitTest.java (the HttpUnit test, testUpdateOfficeVisitSemicolon() method added)
itrust/validate/ValidationFormat.java (the bug fix, added ; to NOTES on line 31)
itrust/unittests/EditOfficeVisitActionTest.java (the JUnit test, testUpdateInformationSemicolon())

bug four:
itrust/webroot/auth/hcp-uap/chronicDiseaseRisks.jsp (the bug fix, added try/catch to catch NoHealthRecordsException)
itrust/httptests/PHIRecordTest.java (the HttpUnit test, testNoHealthRecordException() method added)

INSERT INTO patients
(MID,
 lastName,
 firstName,
 email,
 address1,
 address2,
 phone,
 iCName,
 iCAddress1,
 iCAddress2,
 iCPhone
 )
VALUES
  (2003,
    'Wu',
    'Wang',
    'wWang@email.com',
    '1234 Third St',
    '003',
    '123-123-2003',
    'Aetna',
    '1234 Aetna Blvd',
    'Suite 602',
    '704-555-1234'
    )
ON DUPLICATE KEY UPDATE MID = MID;
INSERT INTO users(MID, password, role, sQuestion, sAnswer)
			VALUES (2003, '30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4', 'pre_patient', 'opposite of yin', 'yang')
 ON DUPLICATE KEY UPDATE MID = MID;
 /*password: pw*/

INSERT INTO personalhealthinformation
(PatientID,OfficeVisitID,Height,Weight,Smoker,SmokingStatus,BloodPressureN,BloodPressureD,CholesterolHDL,CholesterolLDL,CholesterolTri,HCPID, AsOfDate,OfficeVisitDate,BMI)
VALUES ( 2003,  213, 74,   182,   1, 2,     100,          100,           40,             100,         100,          9000000000, '2011-06-06 20:33:58','2011-06-06',24.41)
ON DUPLICATE KEY UPDATE OfficeVisitID = OfficeVisitID;

INSERT INTO officevisits(id,visitDate,HCPID,notes,HospitalID,PatientID)
VALUES (213,'2011-06-06',9000000000,'Old office visit.','', 2003)
ON DUPLICATE KEY UPDATE id = id;

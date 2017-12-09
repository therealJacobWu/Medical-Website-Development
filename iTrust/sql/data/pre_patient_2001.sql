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
  (2001,
    'San',
    'Zhang',
    'sZhang@email.com',
    '1234 First St',
    '001',
    '123-123-2001',
    'Aetna',
    '1234 Aetna Blvd',
    'Suite 602',
    '704-555-1234'
    )
ON DUPLICATE KEY UPDATE MID = MID;

INSERT INTO users(MID, password, role, sQuestion, sAnswer)
			VALUES (2001, '30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4', 'pre_patient', 'opposite of yin', 'yang')
 ON DUPLICATE KEY UPDATE MID = MID;

INSERT INTO personalhealthinformation
(PatientID,OfficeVisitID,Height,Weight,Smoker,SmokingStatus,BloodPressureN,BloodPressureD,CholesterolHDL,CholesterolLDL,CholesterolTri,HCPID, AsOfDate,OfficeVisitDate,BMI)
VALUES ( 2001,  211, 72,   180,   0, 9,     100,          100,           40,             100,         100,          9000000000, '2005-06-07 20:33:58','2005-06-07',24.41)
ON DUPLICATE KEY UPDATE OfficeVisitID = OfficeVisitID;

INSERT INTO officevisits(id,visitDate,HCPID,notes,HospitalID,PatientID)
VALUES (211,'2005-06-07',9000000000,'Old office visit.','', 2001)
ON DUPLICATE KEY UPDATE id = id;
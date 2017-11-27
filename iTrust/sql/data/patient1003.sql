INSERT INTO patients
(MID,
lastName, 
firstName,
email,
address1,
address2,
city,
state,
zip,
phone,
eName,
ePhone,
iCName,
iCAddress1,
iCAddress2,
iCCity, 
ICState,
iCZip,
iCPhone,
iCID,
dateofbirth,
dateofdeath,
causeofdeath,
mothermid,
fathermid,
bloodtype,
ethnicity,
gender,
topicalnotes)
VALUES
(1003,
'Cleopatra VII',
'Philopator',
'the7th@email.com',
'1247 Noname Dr',
'Suite 106',
'Raleigh',
'NC',
'27606-1234',
'919-971-0000',
'Mum',
'704-532-2117',
'Aetna',
'1234 Aetna Blvd',
'Suite 602',
'Charlotte',
'NC',
'28215',
'704-555-1234',
'ChetumNHowe',
'1950-05-10',
'1970-03-10',
'42.00',
1,
0,
'AB+',
'African American',
'Female',
'')
 ON DUPLICATE KEY UPDATE MID = MID;


INSERT INTO users(MID, password, role, sQuestion, sAnswer) 
			VALUES (1003, '30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4', 'patient', 'opposite of yin', 'yang')
 ON DUPLICATE KEY UPDATE MID = MID;
 /*password: pw*/

INSERT INTO personalhealthinformation
(PatientID,OfficeVisitID,Height,Weight,Smoker,SmokingStatus,BloodPressureN,BloodPressureD,CholesterolHDL,CholesterolLDL,CholesterolTri,HCPID, AsOfDate,OfficeVisitDate,BMI)
VALUES ( 1003,  111, 72,   180,   0, 9,     100,          100,           40,             100,         100,          9000000003, '2005-06-07 20:33:58','2005-06-07',24.41)
 ON DUPLICATE KEY UPDATE OfficeVisitID = OfficeVisitID;

INSERT INTO officevisits(id,visitDate,HCPID,notes,HospitalID,PatientID)
VALUES (113,'2005-10-10',9000000003,'Old office visit.','',1003)
 ON DUPLICATE KEY UPDATE id = id;

INSERT INTO ovdiagnosis(ICDCode, VisitID) VALUES 
(487.00, 111)
 ON DUPLICATE KEY UPDATE id = id;

INSERT INTO declaredhcp(PatientID,HCPID) VALUE(1003, 9000000003)
 ON DUPLICATE KEY UPDATE PatientID = PatientID;

INSERT INTO representatives(RepresenterMID, RepresenteeMID) VALUES(2,1003)
 ON DUPLICATE KEY UPDATE RepresenterMID = RepresenterMID;

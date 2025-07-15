-- Vets (Sri Lankan names)
INSERT IGNORE INTO vets VALUES (1, 'Anura', 'Jayawardena');
INSERT IGNORE INTO vets VALUES (2, 'Shanika', 'Senanayake');
INSERT IGNORE INTO vets VALUES (3, 'Mahesh', 'Karunaratne');
INSERT IGNORE INTO vets VALUES (4, 'Nimali', 'Abeykoon');

-- Specialties (keeping globally understood terms)
INSERT IGNORE INTO specialties VALUES (1, 'radiology');
INSERT IGNORE INTO specialties VALUES (2, 'surgery');
INSERT IGNORE INTO specialties VALUES (3, 'dentistry');

-- Vet Specialties (linking SL vets to specialties)
INSERT IGNORE INTO vet_specialties VALUES (2, 1);  -- Shanika - radiology
INSERT IGNORE INTO vet_specialties VALUES (3, 2);  -- Mahesh - surgery
INSERT IGNORE INTO vet_specialties VALUES (3, 3);  -- Mahesh - dentistry
INSERT IGNORE INTO vet_specialties VALUES (4, 2);  -- Nimali - surgery

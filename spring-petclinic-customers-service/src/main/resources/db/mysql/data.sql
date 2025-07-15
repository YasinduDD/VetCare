-- Pet types (translated to common SL pets)
INSERT IGNORE INTO types VALUES (1, 'cat');
INSERT IGNORE INTO types VALUES (2, 'dog');
INSERT IGNORE INTO types VALUES (3, 'rabbit');
INSERT IGNORE INTO types VALUES (4, 'parrot');
INSERT IGNORE INTO types VALUES (5, 'turtle');

-- Owners (with Sri Lankan names and locations)
INSERT IGNORE INTO owners VALUES (1, 'Kamal', 'Perera', '12 Lake Road', 'Colombo', '0771234567');
INSERT IGNORE INTO owners VALUES (2, 'Nadeesha', 'Fernando', '45 Temple Lane', 'Kandy', '0719876543');
INSERT IGNORE INTO owners VALUES (3, 'Ruwan', 'Jayasinghe', '33 Main Street', 'Galle', '0758765432');
INSERT IGNORE INTO owners VALUES (4, 'Ishara', 'Wijesinghe', '78 Beach Road', 'Negombo', '0762345678');
INSERT IGNORE INTO owners VALUES (5, 'Sanduni', 'De Silva', '90 Railway Avenue', 'Kurunegala', '0783456789');

-- Pets (with Sri Lankan-style pet names and birthdates)
INSERT IGNORE INTO pets VALUES (1, 'Simba', '2021-02-15', 2, 1);  -- Dog
INSERT IGNORE INTO pets VALUES (2, 'Misty', '2022-06-10', 1, 2);  -- Cat
INSERT IGNORE INTO pets VALUES (3, 'Kiki', '2020-01-25', 4, 3);   -- Parrot
INSERT IGNORE INTO pets VALUES (4, 'Bunny', '2021-12-05', 3, 4); -- Rabbit
INSERT IGNORE INTO pets VALUES (5, 'Shelly', '2019-03-20', 5, 5);-- Turtle

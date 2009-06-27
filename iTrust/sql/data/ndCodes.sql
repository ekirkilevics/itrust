INSERT INTO NDCodes(Code, Description) VALUES
('009042407','Tetracycline'),
('081096','Aspirin'),
('647641512','Prioglitazone') ON DUPLICATE KEY UPDATE Code = Code;

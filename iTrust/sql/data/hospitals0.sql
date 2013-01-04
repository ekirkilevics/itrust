INSERT INTO hospitals(HospitalID, HospitalName) VALUES
('9191919191','Test Hospital 9191919191'),
('8181818181','Test Hospital 8181818181')
ON DUPLICATE KEY UPDATE HospitalID = HospitalID;

INSERT INTO hospitals(HospitalID, HospitalName, Address, City, State, Zip, Lat, Lng) VALUES
('1','Health Institute Dr. E','1 Stinson Drive','Raleigh','NC','27607',35.78531730,-78.66813150),
('2','Health Institute Mr. Barry','404 Wade Avenue','Raleigh','NC','27607',35.80657740,-78.720540),
('3','Health Institute Mr. Donghoon','9001 Sullivan Drive','Raleigh','NC','27607',35.78692210,-78.68147239999999),
('4','Le Awesome Hospital','1337 Oval Drive','Raleigh','NC','27607',35.77247940,-78.67358639999999),
('5','Facebook Rehab Center','2 Yarborough Drive','Raleigh','NC','27607',35.78531710,-78.67077970),
('6','Mental Hospital 4 iTrust Devs','1234 Hillsborough Street','Raleigh','NC','27607',35.78283040,-78.65537830),
('','Ninja Hospital','5 Dan Allen Drive','Raleigh','NC','27607',35.78536470,-78.67546930)
ON DUPLICATE KEY UPDATE HospitalID = HospitalID;

-------------------------------------------------------------------------
-------------------------------------------------------------------------

CREATE TABLE Mas_Country (
    Country_Id SERIAL PRIMARY KEY,
    Country_Code VARCHAR(3) UNIQUE NOT NULL,   -- e.g. 'IN', 'US'
    Country_Name VARCHAR(100) NOT NULL
);

INSERT INTO Mas_Country (Country_Code, Country_Name) VALUES 
('IN', 'India'),
('US', 'United State of America'),
('EU', 'Europe');

-------------------------------------------------------------------------
-------------------------------------------------------------------------

CREATE TABLE Mas_State (
    State_Id SERIAL PRIMARY KEY,
    State_Code VARCHAR(10) UNIQUE,             -- e.g. 'KA', 'MH'
    State_Name VARCHAR(100) NOT NULL,
    Country_Id INT NOT NULL REFERENCES Mas_Country(Country_Id) ON DELETE CASCADE
);

-------------------------------------------------------------------------
-------------------------------------------------------------------------

CREATE TABLE Mas_City (
    City_Id SERIAL PRIMARY KEY,
    City_Name VARCHAR(100) NOT NULL,
	City_Code VARCHAR(100) UNIQUE NOT NULL,
    State_Id INT NOT NULL REFERENCES Mas_State(State_Id) ON DELETE CASCADE
);

--ALTER TABLE Mas_City ALTER COLUMN City_Code SET NOT NULL;
--ALTER TABLE Mas_City ADD CONSTRAINT Uq_City_Code UNIQUE (City_Code);

--ALTER TABLE Mas_City ADD COLUMN City_Code VARCHAR(20);
UPDATE Mas_City m
SET City_Code = UPPER(LEFT(REPLACE(m.City_Name, ' ', ''), 4) || '_' || s.State_Code)
FROM Mas_State s
WHERE m.State_Id = s.State_Id;

--SELECT City_Code, COUNT(*) AS duplicate_count
--FROM Mas_City
--GROUP BY City_Code
--HAVING COUNT(*) > 1;
-------------------------------------------------------------------------
-------------------------------------------------------------------------

Create Table Mas_Status(
	Status_Id       SERIAL      PRIMARY KEY,
	Status_Name     VARCHAR(40) NOT NULL,
	Status_Code     VARCHAR(5)  UNIQUE NOT NULL,
	Status_Table    VARCHAR(40) NOT NULL
);

Insert Into Mas_Status values(nextval('mas_status_status_id_seq'),'Active','Y','common'); 
Insert Into Mas_Status values(nextval('mas_status_status_id_seq'),'InActive','N','common');

-------------------------------------------------------------------------
-------------------------------------------------------------------------

CREATE TABLE Mas_Currency (
    Currency_Id    BIGSERIAL PRIMARY KEY, 
    Currency_Name  VARCHAR(100) NOT NULL,
    Currency_Code  CHAR(3) NOT NULL UNIQUE,
    Country_Id     BIGINT,
    CONSTRAINT Fk_Currency_Country FOREIGN KEY (Country_Id) REFERENCES Mas_Country(Country_Id)
);

INSERT INTO MAS_CURRENCY (currency_name, currency_code, country_id)
VALUES
('Indian Rupee', 'INR', 1), 
('US Dollar', 'USD', 2),
('Euro', 'EUR', null);

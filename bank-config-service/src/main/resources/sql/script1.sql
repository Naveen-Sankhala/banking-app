
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
    Country_Id     INT NOT NULL REFERENCES Mas_Country(Country_Id) ON DELETE CASCADE,
	Status		   VARCHAR(20) NOT NULL default 'INACTIVE'
);

INSERT INTO MAS_CURRENCY (currency_name, currency_code, country_id,status)
VALUES
('Indian Rupee', 'INR', 1,'ACTIVE'), 
('US Dollar', 'USD', 2,'INACTIVE'),
('Euro', 'EUR', 3,'INACTIVE');

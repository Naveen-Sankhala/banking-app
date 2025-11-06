CREATE TABLE BANK_TYPE (
	Id BIGSERIAL,
    Type_Code   VARCHAR(30) PRIMARY KEY,
    Description VARCHAR(100)
);

INSERT INTO BANK_TYPE VALUES
(nextval('bank_type_id_seq'),'CENTRAL', 'Central Bank of India'),
(nextval('bank_type_id_seq'),'PUBLIC_SECTOR', 'Public Sector Commercial Bank'),
(nextval('bank_type_id_seq'),'PRIVATE_SECTOR', 'Private Sector Commercial Bank'),
(nextval('bank_type_id_seq'),'FOREIGN_BANK', 'Foreign Bank Operating in India'),
(nextval('bank_type_id_seq'),'SMALL_FINANCE_BANK', 'Small Finance Bank'),
(nextval('bank_type_id_seq'),'PAYMENTS_BANK', 'Payments Bank'),
(nextval('bank_type_id_seq'),'REGIONAL_RURAL_BANK', 'Regional Rural Bank'),
(nextval('bank_type_id_seq'),'COOPERATIVE_BANK', 'Cooperative Bank'),
(nextval('bank_type_id_seq'),'LOCAL_AREA_BANK', 'Local Area Bank');

---------------------------------------------------------------
---------------------------------------------------------------

CREATE TABLE BANK (
    Bank_Id       BIGSERIAL PRIMARY KEY,        -- Internal surrogate key
    Bank_Code     VARCHAR(10) UNIQUE NOT NULL,  -- Unique bank identifier (e.g. SBI001)
    Bank_Name     VARCHAR(100) NOT NULL,        -- Full English bank name
	Bank_HName	  VARCHAR(100) NOT NULL,        -- Full Hindi bank name
    Bank_Type     VARCHAR(20) NOT NULL REFERENCES BANK_TYPE(Type_Code), -- e.g. PUBLIC, PRIVATE, COOPERATIVE
	Bank_Head     VARCHAR(100) NOT NULL ,        --  chairman
    Bank_Head_Office  VARCHAR(100) NOT NULL,    -- Bank head office
	Established_Date DATE,                      -- When bank was founded
	Mask_Code	  VARCHAR(20),
	Bill_Real_Head  BIGINT,
	Bill_Com_Head	BIGINT,
    Country_Id      BIGINT NOT NULL REFERENCES Mas_Country(Country_Id),  -- or ISO country code
    Created_By      BIGINT REFERENCES USERS(User_Id),
	Created_Date    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	Last_Chg_By     BIGINT REFERENCES USERS(User_Id),
	Last_Chg_Date   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


INSERT INTO BANK (Bank_Id,Bank_Code, Bank_Name, Bank_HName, Bank_Type, Bank_Head,Bank_Head_Office, Established_Date, Country_Id, Created_By)
VALUES 
(nextval('bank_bank_id_seq'),'RBI','Reserve Bank of India','भारतीय रिजर्व बैंक','CENTRAL','Mumbai','Shaktikanta Das','1935-04-01',1,1),
(nextval('bank_bank_id_seq'),'SBI', 'State Bank of India', 'भारतीय स्टेट बैंक', 'PUBLIC_SECTOR','Mumbai', 'Dinesh Kumar Khara', '1955-07-01', 1, 1),
(nextval('bank_bank_id_seq'),'BOB', 'Bank of Baroda', 'बैंक ऑफ़ बड़ौदा', 'PUBLIC_SECTOR','Mumbai', 'Bharat Bhaskar Bhatia', '1908-07-20', 1, 1),
(nextval('bank_bank_id_seq'),'PNB', 'Punjab National Bank', 'पंजाब नेशनल बैंक', 'PUBLIC_SECTOR','New Delhi', 'Atul Kumar Goel', '1894-05-19', 1, 1),
(nextval('bank_bank_id_seq'),'CAN', 'Canara Bank', 'कनारा बैंक', 'PUBLIC_SECTOR','Bangalore', 'Lingam Venkata Prabhakar', '1906-07-01', 1, 1),
(nextval('bank_bank_id_seq'),'UBI', 'Union Bank of India', 'यूनियन बैंक ऑफ़ इंडिया', 'PUBLIC_SECTOR','Mumbai', 'Rajkiran Rai G', '1919-11-11', 1, 1),
(nextval('bank_bank_id_seq'),'BOI', 'Bank of India', 'बैंक ऑफ़ इंडिया', 'PUBLIC_SECTOR','Mumbai', 'Atanu Kumar Das', '1906-09-07', 1, 1),
(nextval('bank_bank_id_seq'),'IB', 'Indian Bank', 'इंडियन बैंक', 'PUBLIC_SECTOR','Chennai', 'Shanti Lal Jain', '1907-08-15', 1, 1),
(nextval('bank_bank_id_seq'),'CBI', 'Central Bank of India', 'सेंट्रल बैंक ऑफ़ इंडिया', 'PUBLIC_SECTOR','Chennai', 'Matam Venkata Rao', '1911-12-21', 1, 1),
(nextval('bank_bank_id_seq'),'IOB', 'Indian Overseas Bank', 'इंडियन ओवर्सीज़ बैंक', 'PUBLIC_SECTOR','Mumbai', 'Partha Pratim Sengupta', '1937-02-10', 1, 1),
(nextval('bank_bank_id_seq'),'UCO', 'UCO Bank', 'यूको बैंक', 'PUBLIC_SECTOR','Kolkata', 'Atul Kumar Goel', '1943-01-25', 1, 1),
(nextval('bank_bank_id_seq'),'BOM', 'Bank of Maharashtra', 'बैंक ऑफ़ महाराष्ट्र', 'PUBLIC_SECTOR','Mumbai', 'A S Rajeev', '1935-09-16', 1, 1),
(nextval('bank_bank_id_seq'),'PSB', 'Punjab & Sind Bank', 'पंजाब एंड सिंध बैंक', 'PUBLIC_SECTOR','New Delhi', 'S Harinath', '1908-03-24', 1, 1),

(nextval('bank_bank_id_seq'),'HDFC', 'HDFC Bank', 'एचडीएफसी बैंक', 'PRIVATE_SECTOR', 'Sashidhar Jagdishan','Mumbai', '1994-08-01', 1, 1),
(nextval('bank_bank_id_seq'),'ICICI', 'ICICI Bank', 'आईसीआईसीआई बैंक', 'PRIVATE_SECTOR', 'Sandeep Bakhshi','Bangalore', '1994-06-01', 1, 1),
(nextval('bank_bank_id_seq'),'KOTAK', 'Kotak Mahindra Bank', 'कोटक महिंद्रा बैंक', 'PRIVATE_SECTOR', 'Uday Kotak','Bangalore', '2003-02-22', 1, 1),
(nextval('bank_bank_id_seq'),'AXIS', 'Axis Bank', 'एक्सिस बैंक', 'PRIVATE_SECTOR', 'Amitabh Chaudhry','Bangalore', '1993-12-03', 1, 1),
(nextval('bank_bank_id_seq'),'INDUS', 'IndusInd Bank Ltd', 'इंडसइंड बैंक', 'PRIVATE_SECTOR', 'Sumant Kathpalia', 'Mumbai','1994-04-14', 1, 1);


---------------------------------------------------------------
---------------------------------------------------------------

CREATE TABLE BANK_ZONE_REGION (
    Zr_Id        BIGSERIAL PRIMARY KEY,
    Bank_Id      BIGINT NOT NULL,
    Parent_Id    BIGINT NULL,                 -- NULL for Zone, points to Zone if Region
	Bank_Code    VARCHAR(10) NOT NULL,       -- store bank code
	Zr_Type      VARCHAR(10) NOT NULL,        -- 'ZONE' or 'REGION'
	City_Code    VARCHAR(10) NOT NULL,       -- e.g., MUM, DEL
    Zr_Code 	 VARCHAR(30) GENERATED ALWAYS AS (Bank_Code || '_' || Zr_Type || '_' || City_Code) STORED UNIQUE NOT NULL, -- e.g. SOUTH, CHN 
    Zr_Name      VARCHAR(100) NOT NULL,       -- e.g. South Zone / Chennai Region
    Head_Office  VARCHAR(100),
    Created_By      BIGINT REFERENCES USERS(User_Id),
	Created_Date    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	Last_Chg_By     BIGINT REFERENCES USERS(User_Id),
	Last_Chg_Date   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Constraint Fk_Bank FOREIGN KEY (Bank_Id) REFERENCES BANK(Bank_Id),
    Constraint Fk_Parent_Zr FOREIGN KEY (Parent_Id) REFERENCES Bank_Zone_Region(Zr_Id)
);

INSERT INTO BANK_ZONE_REGION (Bank_Id, Parent_Id, Bank_Code, Zr_Type, City_Code, Zr_Name, Head_Office, Created_By)
VALUES
(2, NULL, 'SBI', 'ZONE', 'NORTH', 'North Zone', 'New Delhi', 1),
(2, NULL, 'SBI', 'ZONE', 'SOUTH', 'South Zone', 'Chennai', 1),
(2, NULL, 'SBI', 'ZONE', 'EAST', 'East Zone', 'Kolkata', 1),
(2, NULL, 'SBI', 'ZONE', 'WEST', 'West Zone', 'Mumbai', 1),
(2, NULL, 'SBI', 'ZONE', 'CENTRAL', 'Central Zone', 'Bhopal', 1),
(2, NULL, 'SBI', 'ZONE', 'NE', 'North-East Zone', 'Guwahati', 1),
(2, NULL, 'SBI', 'ZONE', 'MET_MUM', 'Metropolitan Zone Mumbai', 'Mumbai', 1),
(2, NULL, 'SBI', 'ZONE', 'MET_DEL', 'Metropolitan Zone Delhi', 'New Delhi', 1),
(3, NULL, 'BOB', 'ZONE', 'AHM', 'Ahmedabad Zone', 'Ahmedabad', 1),
(3, NULL, 'BOB', 'ZONE', 'MUM', 'Mumbai Zone', 'Mumbai', 1),
(3, NULL, 'BOB', 'ZONE', 'CHN', 'Chennai Zone', 'Chennai', 1),
(3, NULL, 'BOB', 'ZONE', 'DEL', 'Delhi Zone', 'New Delhi', 1);




-- Assume Zr_Id of North Zone = 1 (replace with actual after inserting Zones)
INSERT INTO BANK_ZONE_REGION (Bank_Id, Parent_Id, Bank_Code, Zr_Type, City_Code, Zr_Name, Head_Office, Created_By)
VALUES
(2, 1, 'SBI', 'REGION', 'DEL', 'Delhi Region', 'New Delhi', 1),
(2, 1, 'SBI', 'REGION', 'PNJ', 'Punjab Region', 'Chandigarh', 1);

-- Assume Zr_Id of South Zone = 2
INSERT INTO BANK_ZONE_REGION (Bank_Id, Parent_Id, Bank_Code, Zr_Type, City_Code, Zr_Name, Head_Office, Created_By)
VALUES
(2, 2, 'SBI', 'REGION', 'CHN', 'Chennai Region', 'Chennai', 1),
(2, 2, 'SBI', 'REGION', 'KRL', 'Kerala Region', 'Kochi', 1);


-- Assume Zr_Id of West Zone = 4
INSERT INTO BANK_ZONE_REGION (Bank_Id, Parent_Id, Bank_Code, Zr_Type, City_Code, Zr_Name, Head_Office, Created_By)
VALUES
(2, 4, 'SBI', 'REGION', 'MUM', 'Mumbai Region', 'Mumbai', 1),
(2, 4, 'SBI', 'REGION', 'RAJ', 'Rajasthan Region', 'Jaipur', 1);

--Regions under Metropolitan Zones
-- Replace 'SBI_MET_MUM' and 'SBI_MET_DEL' with actual Zr_Id of inserted Metropolitan Zones
-- Mumbai Metro Region (under Metropolitan Zone)
INSERT INTO BANK_ZONE_REGION (Bank_Id, Parent_Id, Bank_Code, Zr_Type, City_Code, Zr_Name, Head_Office, Created_By)
VALUES
(2, (SELECT Zr_Id FROM BANK_ZONE_REGION WHERE Zr_Code='SBI_ZONE_MET_MUM'), 'SBI', 'REGION', 'MUM_MET', 'Mumbai Metro Region', 'Mumbai', 1);

-- Delhi Metro Region
INSERT INTO BANK_ZONE_REGION (Bank_Id, Parent_Id, Bank_Code, Zr_Type, City_Code, Zr_Name, Head_Office, Created_By)
VALUES
(2, (SELECT Zr_Id FROM BANK_ZONE_REGION WHERE Zr_Code='SBI_ZONE_MET_DEL'), 'SBI', 'REGION', 'DEL_MET', 'Delhi Metro Region', 'New Delhi', 1);



--Regions under Ahmedabad Zone
INSERT INTO BANK_ZONE_REGION (Bank_Id, Parent_Id, Bank_Code, Zr_Type, City_Code, Zr_Name, Head_Office, Created_By)
VALUES
(3, (SELECT Zr_Id FROM BANK_ZONE_REGION WHERE Zr_Code='BOB_ZONE_AHM'), 'BOB', 'REGION', 'AHM', 'Ahmedabad Region', 'Ahmedabad', 1);

---Regions under Mumbai Zone
INSERT INTO BANK_ZONE_REGION (Bank_Id, Parent_Id, Bank_Code, Zr_Type, City_Code, Zr_Name, Head_Office, Created_By)
VALUES
(3, (SELECT Zr_Id FROM BANK_ZONE_REGION WHERE Zr_Code='BOB_ZONE_MUM'), 'BOB', 'REGION', 'MUM1', 'Mumbai Region 1', 'Mumbai', 1),
(3, (SELECT Zr_Id FROM BANK_ZONE_REGION WHERE Zr_Code='BOB_ZONE_MUM'), 'BOB', 'REGION', 'MUM2', 'Mumbai Region 2', 'Mumbai', 1);

---Regions under Chennai Zone
INSERT INTO BANK_ZONE_REGION (Bank_Id, Parent_Id, Bank_Code, Zr_Type, City_Code, Zr_Name, Head_Office, Created_By)
VALUES
(3, (SELECT Zr_Id FROM BANK_ZONE_REGION WHERE Zr_Code='BOB_ZONE_CHN'), 'BOB', 'REGION', 'CHN', 'Chennai Region', 'Chennai', 1);


---Regions under Delhi Zone
INSERT INTO BANK_ZONE_REGION (Bank_Id, Parent_Id, Bank_Code, Zr_Type, City_Code, Zr_Name, Head_Office, Created_By)
VALUES
(3, (SELECT Zr_Id FROM BANK_ZONE_REGION WHERE Zr_Code='BOB_ZONE_DEL'), 'BOB', 'REGION', 'DEL', 'Delhi Region', 'New Delhi', 1);


---------------------------------------------------------------
---------------------------------------------------------------



CREATE TABLE BRANCH (
    Branch_Id       BIGSERIAL PRIMARY KEY,
	Zr_Id           BIGINT NOT NULL REFERENCES BANK_ZONE_REGION(Zr_Id),              -- links to region (or directly zone if no region)
    Branch_Code     VARCHAR(20) UNIQUE NOT NULL,
    Branch_Name     VARCHAR(100) NOT NULL,
    Ifsc_Code       VARCHAR(20) UNIQUE,
    Swift_Code      VARCHAR(20) UNIQUE,
	Chk_Clearing_Code VARCHAR(20),
    Address_Line1   VARCHAR(150) NOT NULL,
    Address_Line2   VARCHAR(150),
    City_Id         BIGINT NOT NULL REFERENCES Mas_City(City_Id),
    State_Id        BIGINT NOT NULL REFERENCES Mas_State(State_Id),
    Zipcode         VARCHAR(20) NOT NULL,
    Country_Id      BIGINT NOT NULL REFERENCES Mas_Country(Country_Id),
    Phone_Number    VARCHAR(20),
    Email           VARCHAR(100),
    Status          VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
	Branch_Type     VARCHAR(50),   -- e.g. RETAIL / CORPORATE / DIGITAL
    Manager_Id      BIGINT,        -- FK to employee if you have EMPLOYEE table
	Is_Branch_Open	VARCHAR(10),
    Opening_Date    DATE,
    Closing_Date    DATE,
	Max_Cash_Limit	DECIMAL(18,2) NOT NULL DEFAULT 0.00,
	Gst_No			VARCHAR(20),
	Gst_Reg_Name	VARCHAR(50),
	Gst_Reg_Pan_No	VARCHAR(20),
	Pool_Account	VARCHAR(20),
	Created_By      BIGINT REFERENCES USERS(User_Id),
	Created_Date    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	Last_Chg_By     BIGINT REFERENCES USERS(User_Id),
	Last_Chg_Date   TIMESTAMP
);


CREATE OR REPLACE FUNCTION Generate_Branch_Code(p_zone_id BIGINT, p_region_id BIGINT)
RETURNS VARCHAR(7) AS $$
DECLARE
    zone_code TEXT;
    region_code TEXT;
    next_branch INT;
    branch_seq TEXT;
BEGIN
    -- Format zone + region codes
    zone_code := LPAD(p_zone_id::text, 2, '0');
    region_code := LPAD(p_region_id::text, 2, '0');

    -- Get last branch seq in that region
    SELECT COALESCE(MAX(CAST(RIGHT(Branch_Code, 3) AS INT)), 0) + 1
    INTO next_branch
    FROM Branch
    WHERE Zr_Id = p_region_id;

    branch_seq := LPAD(next_branch::text, 3, '0');

    RETURN zone_code || region_code || branch_seq;
END;
$$ LANGUAGE plpgsql;


INSERT INTO Branch 
(Zr_Id, Branch_Code, Branch_Name, Ifsc_Code, Swift_Code, Chk_Clearing_Code, 
 Address_Line1, Address_Line2, City_Id, State_Id, Zipcode, Country_Id, 
 Phone_Number, Email, Status, Branch_Type, Manager_Id, Is_BranchOpen, 
 Opening_Date, Max_Cash_Limit, Gst_No, Gst_Reg_Name, Gst_Reg_Pan_No, Pool_Accocunt, 
 Created_By, Created_Date)
VALUES
-- Chennai Region branch
(13, 
 generate_branch_code(2, 13),  -- Zone=2, Region=13
 'Chennai Main Branch', 'SBIN000001', 'SBICHN001', 'CL001',
 '123 Mount Road', 'Near Central Station', 101, 33, '600001', 1,
 '044-22223333', 'chennai.branch@sbi.com', 'ACTIVE', 'RETAIL', NULL, 'OPEN',
 '2025-09-14', 5000000.00, 'GSTCHN001', 'SBI Chennai', 'PANCHN001', 'POOL001',
 1, '2025-09-14 13:45:59.172414'),

-- Kerala Region branch
(14,
 generate_branch_code(2, 14),  -- Zone=2, Region=14
 'Kochi Main Branch', 'SBIN000002', 'SBIKRL001', 'CL002',
 'MG Road', 'Near Marine Drive', 102, 17, '682001', 1,
 '0484-2233445', 'kochi.branch@sbi.com', 'ACTIVE', 'RETAIL', NULL, 'CLOSE',
 '2025-09-14', 4000000.00, 'GSTKRL001', 'SBI Kerala', 'PANKRL001', 'POOL002',
 1, '2025-09-14 13:45:59.172414'),

-- Delhi Region branch
(24,
  generate_branch_code(2, 24),  -- Zone=2, Region=14
 'Delhi Main Branch', 'SBIN000003', 'SBIDEL001', 'CL003',
 'Connaught Place', 'Block A', 103, 7, '110001', 1,
 '011-23456789', 'delhi.branch@sbi.com', 'ACTIVE', 'CORPORATE', NULL, 'OPEN',
 '2025-09-14', 8000000.00, 'GSTDEL001', 'SBI Delhi', 'PANDEL001', 'POOL003',
 1, '2025-09-14 14:54:30.277856'),

-- Punjab Region branch
(25,
  generate_branch_code(2, 25),  -- Zone=2, Region=14
 'Chandigarh Main Branch', 'SBIN000004', 'SBIPNJ001', 'CL004',
 'Sector 17 Market', NULL, 104, 3, '160017', 1,
 '0172-2233445', 'chandigarh.branch@sbi.com', 'ACTIVE', 'RETAIL', NULL, 'CLOSE',
 '2025-09-14', 3000000.00, 'GSTPNJ001', 'SBI Punjab', 'PANPNJ001', 'POOL004',
 1, '2025-09-14 14:54:30.277856');
 
 
 
 
 


---------------------------------------------------------------
---------------------------------------------------------------

CREATE TABLE BANK_CONFIGURATION (
    Config_Id               BIGSERIAL PRIMARY KEY,
	Bank_Id                 BIGINT NOT NULL,
    English_Name            VARCHAR(100) NOT NULL,
    Hindi_Name              VARCHAR(100),
	Country_Id              BIGINT,
	Currency_Id             BIGINT,
	Allow_Multiple_Currency VARCHAR(1) DEFAULT 'N',
	Bank_Date 				DATE NOT NULL,
	Day_End_Process         VARCHAR(1) DEFAULT 'N',
	Financial_Year_Start    DATE NOT NULL,
    Financial_Year_End      DATE,   -- auto-managed
    Audit_Trail_Required    VARCHAR(1) DEFAULT 'N',
    Audit_Option            VARCHAR(20),
	Audit_Number            BIGINT,
    Single_Mode_Enabled     VARCHAR(1) DEFAULT 'N',
    Ecs_Bank_Code           VARCHAR(20),
    Same_Loan_Account_Yn    VARCHAR(1) DEFAULT 'N',
    Client_Code             VARCHAR(20),
	Created_Date            TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT Fk_Bank FOREIGN KEY (Bank_Id) REFERENCES BANK(Bank_Id),
    CONSTRAINT Fk_Currency FOREIGN KEY (Currency_Id) REFERENCES Mas_Currency(Currency_Id),
    CONSTRAINT Fk_Country FOREIGN KEY (Country_Id) REFERENCES Mas_Country(Country_Id)
);

CREATE OR REPLACE FUNCTION Set_Financial_Year_Rules()
RETURNS TRIGGER AS $$
BEGIN
    -- Rule 1: Ensure Financial_Year_Start is April 1
    IF EXTRACT(MONTH FROM NEW.Financial_Year_Start) <> 4
       OR EXTRACT(DAY FROM NEW.Financial_Year_Start) <> 1 THEN
        RAISE EXCEPTION 'Financial year must start on April 1 (YYYY-04-01)';
    END IF;

    -- Rule 2: Ensure only one row in the table
    IF (TG_OP = 'INSERT') AND (SELECT COUNT(*) FROM Bank_Configuration) >= 1 THEN
        RAISE EXCEPTION 'Only one Bank_Configuration record is allowed. Either Delete or Update  the existing record.';
    END IF;

    -- Rule 3: Auto-calculate Financial_Year_End
    NEW.Financial_Year_End := (NEW.Financial_Year_Start + INTERVAL '1 year - 1 day');

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Attach trigger
DROP TRIGGER IF EXISTS bank_config_fy_trigger ON bank_configuration;

CREATE TRIGGER Bank_Config_Fy_Trigger
BEFORE INSERT OR UPDATE ON Bank_Configuration
FOR EACH ROW
EXECUTE FUNCTION Set_Financial_Year_Rules();

INSERT INTO BANK_CONFIGURATION (
Bank_Id ,English_Name,Hindi_Name,Country_Id ,Currency_Id ,Bank_Date   ,Financial_Year_Start,Audit_Number ,Client_Code  ) VALUES
(2,'State Bank of India','भारतीय स्टेट बैंक',1,1,'2025--9-19','2025-04-01',0000000081,'9999');



---------------------------If Change then it--------------------
---------------------------					--------------------
----If Recommendation:

CREATE TABLE BANK_DATE (
    Id BIGSERIAL PRIMARY KEY,
    Bank_Id BIGINT NOT NULL,
    Business_Date DATE NOT NULL,
    Mode VARCHAR(20) NOT NULL,  -- SYSTEM or MANUAL
    Last_Updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT Fk_Bank FOREIGN KEY (Bank_Id) REFERENCES BANK(Bank_Id)
);

-------------------------------------------------------------------------
-------------------------------------------------------------------------

CREATE TABLE MAS_RELATION(
		Relation_Id     BIGINT NOT NULL PRIMARY KEY,
		Relation_Name   Varchar(50) NOT NULL,
		Relation_Code   Varchar(20) UNIQUE,
		Status       	Varchar(1)     NOT NULL DEFAULT 'Y'
    );
	
	
Insert Into MAS_RELATION(Relation_Id,Relation_Name,Relation_Code,Status) VALUES
(1,'Self','S','Y'),
(2,'Mother','M','Y'),
(3,'Father','F','Y'),
(4,'Wife','W','Y'),
(5,'Son 1','S1','Y'),
(6,'Daughter 1','D1','Y'),
(7,'Son 2','S2','Y'),
(8,'Daughter 2','D2','Y');

-------------------------------------------------------------------------
-------------------------------------------------------------------------


CREATE TABLE MAS_GENDER_TITLE (
    Id              BIGSERIAL PRIMARY KEY,
    Gender_Code     CHAR(1) NOT NULL,         		-- M, F, O
    Gender_Name     VARCHAR(20) NOT NULL,     		-- MALE, FEMALE, OTHER
    Title_Code      VARCHAR(10) NOT NULL,    -- MR, MRS, MS, DR, etc.
    Title_Name      VARCHAR(50) NOT NULL,    		-- Mr., Mrs., Ms., Dr.
    Status       	CHAR(1) DEFAULT 'Y',     		-- Active flag
    Created_By      BIGINT REFERENCES USERS(User_Id),
	Created_Date    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	Last_Chg_By     BIGINT REFERENCES USERS(User_Id),
	Last_Chg_Date   TIMESTAMP
);

INSERT INTO MAS_GENDER_TITLE (Gender_Code, Gender_Name, Title_Code, Title_Name,Created_By) VALUES
('M', 'MALE', 'MR', 'Mr.',1),
('F', 'FEMALE', 'MRS', 'Mrs.',1),
('F', 'FEMALE', 'MS', 'Ms.',1),
('O', 'OTHER', 'MX', 'Mx.',1),
('M', 'MALE', 'DR', 'Dr.',1),
('F', 'FEMALE', 'DR', 'Dr.',1);

-------------------------------------------------------------------------
-------------------------------------------------------------------------

CREATE TABLE Mas_Role(
	Role_Id         SERIAL NOT NULL PRIMARY KEY,
	Role_Code       VARCHAR(50) UNIQUE NOT NULL,
	Role_Name       VARCHAR(100) NOT NULL,
	Role_Category   VARCHAR(50) NOT NULL, 
	Description		VARCHAR(100),
	Status       	VARCHAR(1)   NOT NULL,
	Created_By      BIGINT REFERENCES USERS(User_Id),
    Created_Date    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Last_Chg_By     BIGINT REFERENCES USERS(User_Id),
    Last_Chg_Date   TIMESTAMP
);

INSERT INTO Mas_Role (Role_Code, Role_Name, Role_Category, Description,Status,Created_By)
VALUES
('CUSTOMER', 'Customer', 'CUSTOMER', 'End-user holding an account with the bank','Y',1),
('CSR', 'Customer Service Representative', 'CUSTOMER', 'Handles customer queries and support','Y',1),
('TELLER', 'Teller / Cashier', 'OPERATIONS', 'Handles deposits, withdrawals, cash operations','Y',1),
('REL_MANAGER', 'Relationship Manager', 'CUSTOMER', 'Manages accounts of priority customers','Y',1),
('PERS_BANKER', 'Personal Banker', 'CUSTOMER', 'Advises on loans, cards, investments','Y',1),
('BACK_OFF_EXEC', 'Back Office Executive', 'OPERATIONS', 'Handles reconciliation, settlements, clerical tasks','Y',1),
('LOAN_OFFICER', 'Loan Processing Officer', 'OPERATIONS', 'Processes loan applications','Y',1),
('CREDIT_ANALYST', 'Credit Analyst', 'OPERATIONS', 'Evaluates customer creditworthiness','Y',1),
('COMPLIANCE_OFF', 'Compliance Officer', 'OPERATIONS', 'Ensures banking regulations are followed','Y',1),
('RISK_MGR', 'Risk Manager', 'OPERATIONS', 'Manages operational and credit risks','Y',1),
('BRANCH_MGR', 'Branch Manager', 'MANAGEMENT', 'Leads branch operations and sales','Y',1),
('ASST_MGR', 'Assistant Manager', 'MANAGEMENT', 'Supports branch manager','Y',1),
('REGION_MGR', 'Regional Manager', 'MANAGEMENT', 'Oversees multiple branches in a region','Y',1),
('PROD_MGR', 'Product Manager', 'MANAGEMENT', 'Manages financial products','Y',1),
('TREASURY_MGR', 'Treasury Manager', 'MANAGEMENT', 'Manages bank’s funds and liquidity','Y',1),
('CBS_ADMIN', 'Core Banking System Administrator', 'IT', 'Manages core banking software','Y',1),
('DBA', 'Database Administrator', 'IT', 'Handles bank databases securely','Y',1),
('CYBER_ANA', 'Cybersecurity Analyst', 'IT', 'Protects against fraud and cyber threats','Y',1),
('IT_MGR', 'IT Manager / System Architect', 'IT', 'Manages IT infrastructure','Y',1),
('DIGI_BANK_SPEC', 'Digital Banking Specialist', 'IT', 'Manages internet/mobile banking','Y',1),
('AML_OFF', 'AML Officer', 'SPECIALIZED', 'Monitors anti-money laundering compliance','Y',1),
('INT_AUDITOR', 'Internal Auditor', 'SPECIALIZED', 'Audits internal processes','Y',1),
('EXT_AUDITOR', 'External Auditor', 'SPECIALIZED', 'Independent audits','Y',1),
('INV_BANKER', 'Investment Banker', 'SPECIALIZED', 'Handles mergers, acquisitions, deals','Y',1),
('WEALTH_MGR', 'Wealth Manager', 'SPECIALIZED', 'Advises clients on investments','Y',1),
('CEO', 'Chief Executive Officer', 'LEADERSHIP', 'Head of the bank','Y',1),
('CFO', 'Chief Financial Officer', 'LEADERSHIP', 'Manages bank finances','Y',1),
('CRO', 'Chief Risk Officer', 'LEADERSHIP', 'Manages risk strategy','Y',1),
('CIO', 'Chief Information Officer / CTO', 'LEADERSHIP', 'Leads IT and technology strategy','Y',1),
('CCO', 'Chief Compliance Officer', 'LEADERSHIP', 'Ensures compliance with laws','Y',1),
('BOARD', 'Board of Directors / Chairman', 'LEADERSHIP', 'Strategic governance role','Y',1);

---------------------------------------------------------------
---------------------------------------------------------------

CREATE TABLE MAS_OCCUPATION (
    Ocp_Id     		  BIGSERIAL PRIMARY KEY, 
    Ocp_Hname         VARCHAR(100),          
    Ocp_Ename      	  VARCHAR(100),          
    Ocp_Code   		  VARCHAR(20) UNIQUE,    
    Marks             NUMERIC(10,2),         
    Guaranteed_Marks  NUMERIC(10,2),         
    Behaviour         VARCHAR(100),          
    Salary_Behaviour  VARCHAR(100),          
    Customer_Type     VARCHAR(50),           
    Occupation_Group  VARCHAR(50),           
    Status            VARCHAR(1) DEFAULT 'Y',
    Created_By        BIGINT REFERENCES USERS(User_Id),
	Created_Date      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	Last_Chg_By       BIGINT REFERENCES USERS(User_Id),
	Last_Chg_Date     TIMESTAMP
);

select * from MAS_OCCUPATION;
INSERT INTO MAS_OCCUPATION (Ocp_Hname,Ocp_Ename, Ocp_Code, Status,Created_By)
VALUES
('छात्र','STUDENT', 'STD','Y',1),
('नज सेवा','PRIVATE SERVICE', 'PRV','Y',1),
('अन्य','OTHERS', 'OTH','Y',1),
('व्यवसाय','BUSINESS', 'BUS','Y',1),
('कर्मचारीÇ','SERVICE', 'SRV','Y',1),
('सरकारी सेवा','GOVT. SERVICE', 'GOV','Y',1),
('गृहणी','HOUSE WIFE', 'HWF','Y',1),
('व्यवसायी	','PROFESSIONAL', 'PRO','Y',1),
('ठ्ठeद्भग्ठ्ठe्','RETIRED', 'RET', 'Y',1);


---------------------------------------------------------------
---------------------------------------------------------------


	CREATE TABLE MAS_CUSTOMER_CONSTITUTION (
		Con_Id           BIGSERIAL PRIMARY KEY,
		Con_Hname        VARCHAR(100),
		Con_Ename        VARCHAR(100),
		Con_Code         VARCHAR(20) UNIQUE,
		Con_Type         VARCHAR(50),
		Con_Chqyn        CHAR(1) DEFAULT 'N',
		Con_Npa          CHAR(1) DEFAULT 'N',
		Con_Risktype     VARCHAR(20),
		Con_Isteller     CHAR(1) DEFAULT 'N',
		Con_Iscii        CHAR(1) DEFAULT 'N',
		Con_Lang1        VARCHAR(100),
		Con_Lang2        VARCHAR(100),
		Con_Lang3        VARCHAR(100),
		Con_Lang4        VARCHAR(100),
		Con_Lang5        VARCHAR(100),
		Status           VARCHAR(1) DEFAULT 'Y',
		Created_By       BIGINT REFERENCES USERS(User_Id),
		Created_Date     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
		Last_Chg_By      BIGINT REFERENCES USERS(User_Id),
		Last_Chg_Date    TIMESTAMP
	);



	INSERT INTO MAS_CUSTOMER_CONSTITUTION 
	(Con_Hname, Con_Ename, Con_Code, Con_Type, Con_Chqyn ,Con_Risktype, Con_Isteller ,Status, Created_By)
	VALUES

	('सामान्य', 'GENERAL', 'GEN', 'TYPE', 'Y', 'LOW', 'Y','Y', 1),
	('व्यितगत', 'INDIVIDUAL', 'INDV', 'PERSON', 'Y','LOW', 'Y','Y', 1),
	('संयुयात', 'JOINT', 'JNT', 'ACCOUNT', 'Y', 'LOW','Y', 'Y', 1),
	('असाक्षर', 'ILLITERATE', 'ILL', 'PERSON', 'Y', 'LOW','Y', 'Y', 1),
	('नेत्रहीन', 'BLIND', 'BLD', 'PERSON', 'Y', 'LOW', 'N','Y', 1),
	('मूक बधर', 'DEAF AND DUMB', 'DAD', 'PERSON', 'Y', 'LOW', 'Y','Y', 1),
	('वकलांग', 'HANDICAPED', 'HND', 'PERSON', 'Y', 'LOW','Y', 'Y', 1),
	('पदर्ानशीं', 'PARDANASINH', 'PRD', 'PERSON', 'Y', 'LOW','Y', 'Y', 1),

	('र्े्', 'HINDU UNDIVIDED FAMIL', 'HUF', 'FAMILY', 'N', 'LOW', 'N','Y', 1),

	('क्ष्क्ष्क्ष्क्ष्क्ष्क्ष्क्ष्क्ष्क्ष्क्ष्क्ष्क्ष्', 'CONSUMPTION', 'CNS', 'CATEGORY', 'N','LOW','N', 'Y', 1),

	('प्राथमक क्षेत्र', 'PRIORITY SECTOR', 'PRS', 'SECTOR','N', 'LOW', 'N','Y', 1),
	('अप्राथमक क्षेत्र', 'NON PRIORITY SECTOR', 'NPS', 'SECTOR','N', 'LOW','N', 'Y', 1),
	('व्यितगत स्वामत्व', 'SOLE PROPRIETOR', 'SOP', 'PERSON', 'N','LOW','Y', 'Y', 1),
	('सह-संचालत बैंक', 'JOINT SECTOR', 'JSE', 'ORG','N', 'MEDIUM', 'Y','Y', 1),

	('पंजीकृत साझेदारी', 'REGISTERED PARTNERSHIP', 'RPT', 'FIRM','N', 'MEDIUM', 'N','Y', 1),
	('अपंजीकृत साझेदारी', 'UNREGISTERED PARTNERSHIP', 'URP', 'FIRM', 'N','MEDIUM', 'N','Y', 1),

	('स्वामत्व सम्बन्धी', 'PROPERTIES OF CONCERN', 'POC', 'ORG','N', 'MEDIUM', 'Y','Y', 1),
	('पब्लक सेकटर अन्डरटेकगं', 'PUBLIC SECTOR UNDERTAKING', 'PSU', 'ORG','N', 'MEDIUM','Y', 'Y', 1),
	('अर्धसरकारी कम्पनी', 'GOVERNMENT UNDERTAKING OR', 'GOU', 'GOVT','N', 'LOW','Y', 'Y', 1),
	('पंजीकृृत संस्था', 'REGISTERED SOCIETY', 'RSO', 'ORG','N', 'MEDIUM','Y', 'Y', 1),
	('अपंजीकृृत संस्था', 'UNREGISTERED SOCIETY', 'URS', 'ORG', 'N','MEDIUM','Y', 'Y', 1),
	('सह-संचालत समत', 'CO-OPERATIVE SOCIETY', 'COS', 'ORG', 'N','MEDIUM', 'Y','Y', 1),
	('लोक न्यास', 'PRIVATE TRUSTS', 'PTR', 'NON_PROFIT', 'N','MEDIUM','Y', 'Y', 1),
	('सलब', 'PUBLIC TRUSTS', 'PUT', 'NON_PROFIT','N', 'MEDIUM','Y', 'Y', 1),
	('सरकारी नयंत्रण व प्रबन्ध', 'CLUBS', 'CLB', 'ORG', 'N','LOW','Y', 'Y', 1),

	('पब्लक लमटेड कम्पनी', 'PUBLIC LIMITED COMPANY', 'PVT', 'ORG', 'N','HIGH', 'Y','Y', 1),
	('प्राइवेट लमटेड कम्पनी', 'PRIVATE LIMITED COMPANY', 'PLC2', 'ORG', 'N','HIGH','Y', 'Y', 1),
	('सरकारी वभाग', 'GOVERNMENTT COMPANY', 'GOV', 'GOVT','N', 'LOW','Y', 'Y', 1),
	('संयुयात क्षेत्र', 'SEMI GOVERNMENT AGENCY', 'SGA', 'GOVT','N', 'LOW', 'Y','Y', 1),
	('बैंक', 'GOVERNMENT DEPARTMENT', 'GDE', 'GOVT','N', 'LOW','Y', 'Y', 1),

	('वदेशी कम्पनी', 'BANKS', 'BNK', 'ORG', 'N','HIGH','Y', 'Y', 1),
	('वदेशी कम्पनी', 'FOREIGN COMPANY', 'FCM', 'ORG','N', 'HIGH','Y', 'Y', 1),
	('अश्रैणीकृत', 'NOT CLASSIFIED', 'NCL', 'CATEGORY', 'N','LOW', 'Y','Y', 1),
	('संगठन', 'ASSOCIATIONS', 'ASS', 'ORG','N', 'MEDIUM','Y', 'Y', 1),
	('सांसथानक', 'INSTITUTIONAL', 'INS', 'ORG','N', 'MEDIUM','N', 'Y', 1),
	('असांसथानक', 'NON INSTITUTIONAL', 'NIT', 'ORG','N', 'LOW', 'N','Y', 1),
	('लागू नहीR होता', 'NOT APPLICABLE', 'NAP', 'CATEGORY','N', 'LOW','N', 'Y', 1);
	
	


---------------------------------------------------------------
---------------------------------------------------------------

CREATE TABLE MAS_RELIGION (
    Religion_Id   BIGSERIAL PRIMARY KEY,
    Rlgn_Ename    VARCHAR(100) NOT NULL,      -- Religion name in English
    Rlgn_Hname    VARCHAR(100),               -- Religion name in Hindi (optional)
    Rlgn_Code     VARCHAR(10) UNIQUE NOT NULL, -- Short code
    Rlgn_Type     VARCHAR(50),                -- Optional type/category (e.g., MAJOR, MINOR)
    Status        VARCHAR(1) DEFAULT 'Y',
    Created_By    BIGINT REFERENCES USERS(User_Id),
    Created_Date  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Last_Chg_By   BIGINT REFERENCES USERS(User_Id),
    Last_Chg_Date TIMESTAMP
);


INSERT INTO MAS_RELIGION (Rlgn_Ename, Rlgn_Hname, Rlgn_Code, Rlgn_Type, Status, Created_By)
VALUES
('HINDU', 'हिन्दू', 'HIN', 'MAJOR', 'Y', 1),
('MUSLIM', 'मुस्लिम', 'MUS', 'MAJOR', 'Y', 1),
('JAIN', 'जैन', 'JAI', 'MINOR', 'Y', 1),
('CHRISTIAN', 'ईसाई', 'CHR', 'MAJOR', 'Y', 1),
('SIKH', 'सिख', 'SIK', 'MINOR', 'Y', 1);

---------------------------------------------------------------
---------------------------------------------------------------

CREATE TABLE MAS_CAST (
    Cast_Id       BIGSERIAL PRIMARY KEY,
    Cast_Ename     VARCHAR(100) NOT NULL,        -- Caste name in English
    Cast_Hname     VARCHAR(100),                 -- Caste name in Hindi
	Cast_Code     VARCHAR(10) UNIQUE NOT NULL,  -- Short code
    Cast_Type     CHAR(1) NOT NULL,             -- O / C / T / B etc.
    Status        VARCHAR(1) DEFAULT 'Y',          -- Status (Y=Active, N=Inactive)
    Created_By    BIGINT REFERENCES USERS(User_Id),
    Created_Date  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Last_Chg_By   BIGINT REFERENCES USERS(User_Id),
    Last_Chg_Date TIMESTAMP
);


INSERT INTO MAS_CAST (Cast_Ename, Cast_Hname, Cast_Type, Cast_Code, Status, Created_By)
VALUES
('GENERAL', 'सामान्य', 'O', 'GEN', 'Y', 1),
('SCHEDULED CASTE', 'अनुसूचित जाति', 'C', 'SC', 'Y', 1),
('SCHEDULED TRIBE', 'अनुसूचित जनजाति', 'T', 'ST', 'Y', 1),
('OBC', 'अन्य पिछड़ी जाति', 'B', 'OBC', 'Y', 1);


---------------------------------------------------------------
---------------------------------------------------------------

CREATE TABLE Acc_Categories (
    Acc_Cat_Id     SERIAL PRIMARY KEY,
    Acc_Cat_Name   VARCHAR(50) UNIQUE NOT NULL,
    Acc_Cat_SName  VARCHAR(10) UNIQUE NOT NULL,
    Acc_Cat_Code   VARCHAR(5) UNIQUE, -- will be auto-filled from trigger
    Category       VARCHAR(30) NOT NULL, -- e.g. DEPOSIT, LOAN, SPECIAL
    Status         VARCHAR(20) DEFAULT 'ACTIVE',
    Created_By     BIGINT REFERENCES USERS(User_Id),
    Created_Date   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Last_Chg_By    BIGINT REFERENCES USERS(User_Id),
    Last_Chg_Date  TIMESTAMP
);

CREATE OR REPLACE FUNCTION Set_Acc_Cat_Code()
RETURNS TRIGGER AS $$
BEGIN
    -- Format with leading zero if < 10
    NEW.Acc_Cat_Code := LPAD(NEW.Acc_Cat_Id::TEXT, 2, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER Set_Acc_Cat_Code_Trg
BEFORE INSERT OR UPDATE ON Acc_Categories
FOR EACH ROW
EXECUTE FUNCTION Set_Acc_Cat_Code();

-- ------------------ Deposit Accounts ------------------
INSERT INTO Acc_Categories (Acc_Cat_Name, Acc_Cat_SName, Category, Created_By) VALUES
('SAVINGS',              'SB',   'DEPOSIT', 1),
('CURRENT',              'CA',   'DEPOSIT', 1),
('FIXED_DEPOSIT',        'FD',   'DEPOSIT', 1),
('RECURRING_DEPOSIT',    'RD',   'DEPOSIT', 1),
('SALARY_ACCOUNT',       'SA',   'DEPOSIT', 1),
('NRI_NRE',              'NRE',  'DEPOSIT', 1),
('NRI_NRO',              'NRO',  'DEPOSIT', 1),
('NRI_FCNR',             'FCNR', 'DEPOSIT', 1),
('CERTIFICATE_OF_DEPOSIT','CD',  'DEPOSIT', 1);

-- ------------------ Loan / Asset Accounts ------------------
INSERT INTO Acc_Categories (Acc_Cat_Name, Acc_Cat_SName, Category, Created_By) VALUES
('PERSONAL_LOAN',        'PL',   'LOAN', 1),
('HOME_LOAN',            'HL',   'LOAN', 1),
('VEHICLE_LOAN',         'VL',   'LOAN', 1),
('EDUCATION_LOAN',       'EL',   'LOAN', 1),
('BUSINESS_LOAN',        'BL',   'LOAN', 1),
('AGRICULTURAL_LOAN',    'AL',   'LOAN', 1),
('GOLD_LOAN',            'GL',   'LOAN', 1),
('CREDIT_CARD',          'CC',   'LOAN', 1),
('OVERDRAFT',            'OD',   'LOAN', 1),
('CASH_CREDIT',          'CCL',  'LOAN', 1);

-- ------------------ Special / Other Accounts ------------------
INSERT INTO Acc_Categories (Acc_Cat_Name, Acc_Cat_SName, Category, Created_By) VALUES
('DEMAT_ACCOUNT',        'DM',   'SPECIAL', 1),
('INVESTMENT_ACCOUNT',   'IA',   'SPECIAL', 1),
('JOINT_ACCOUNT',        'JA',   'SPECIAL', 1),
('MINOR_ACCOUNT',        'MA',   'SPECIAL', 1),
('ESCROW_ACCOUNT',       'EA',   'SPECIAL', 1),
('TRUST_ACCOUNT',        'TA',   'SPECIAL', 1),
('PENSION_ACCOUNT',      'PA',   'SPECIAL', 1),
('NOSTRO_ACCOUNT',       'NA',   'SPECIAL', 1),
('VOSTRO_ACCOUNT',       'VA',   'SPECIAL', 1),
('SUSPENSE_ACCOUNT',     'SACT', 'SPECIAL', 1);

---------------------------------------------------------------
---------------------------------------------------------------
-------------------------------------------------------------------------
-------------------------- Create Customer ------------------------------
-------------------------------------------------------------------------
--If Exist

DROP TRIGGER IF EXISTS CIF_No_Trigger ON Customer;
DROP FUNCTION IF EXISTS Generate_CIF_No();
drop table USERS;
drop table ADDRESS;
drop table CUSTOMER;

-- Customer table

CREATE TABLE CUSTOMER_DETAILS (
	Customer_Details_Id	  BIGSERIAL PRIMARY KEY,
    Customer_Id           BIGSERIAL REFERENCES CUSTOMER(Customer_Id) ON DELETE CASCADE,  -- Unique Customer ID
    Branch_Id     		  BIGINT 	REFERENCES BRANCH(Branch_Id),       -- Linked branch
    Gender				  VARCHAR(20) NOT NULL,
	Date_Of_Birth 	      Date NOT NULL,
	Is_Minor              CHAR(1) DEFAULT 'N',
	Major_Date            DATE,
	Marital_Status        VARCHAR(20),
    Has_Guardian          CHAR(1) DEFAULT 'N',
    Guardian_Type         VARCHAR(50),
    Has_Nominee           CHAR(1) DEFAULT 'N',
	Occupation_Id         BIGINT,
    Constitution_Id       BIGINT,
    Religion_Id           BIGINT,
    Caste_Id              BIGINT,
    
    Relation_Type         VARCHAR(10) REFERENCES MAS_RELATION(Relation_Code),
    Husband_Father_Title  VARCHAR(10),
    Husband_Father_Name   VARCHAR(150),
   
    Mother_Relation       VARCHAR(10) REFERENCES MAS_RELATION(Relation_Code),
    Mother_Title          VARCHAR(10),
    Mother_Name           VARCHAR(150),
    
    Education_Qual        VARCHAR(100),
    Num_Dependents        INT,
    
	Checksum_Value        VARCHAR(64),
    National_Id_Number    VARCHAR(30),
    Aadhar_Number 		  CHAR(12) UNIQUE,
	Pan_Number 		      VARCHAR(10) NOT NULL,
	Gst_In_Number	      VARCHAR(20) NOT NULL,
    Passport_Number       VARCHAR(20),
    Passport_Place_Issue  VARCHAR(100),
    Passport_Issue_Date   DATE,
    Passport_Expiry_Date  DATE,
    Contact_No 		      VARCHAR(20) NOT NULL,
	Alternate_Contact_No  VARCHAR(20),
	Email 		          VARCHAR(50),
    Currency_Id    		  BIGINT REFERENCES MAS_CURRENCY(Currency_Id),
	Membership_Number     VARCHAR(30),
    Employee_Number       VARCHAR(30),
	Account_Manager       VARCHAR(100),
    Customer_Group        VARCHAR(50),
	Created_By      	  BIGINT REFERENCES USERS(User_Id),
	Created_Date    	  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	Last_Chg_By     	  BIGINT REFERENCES USERS(User_Id),
	Last_Chg_Date   	  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);




CREATE TABLE ADDRESS (
    Address_Id 		BIGSERIAL PRIMARY KEY,	-- surrogate key
    Customer_Id 	BIGINT NOT NULL,	-- FK to customer.Cust_Id
	Address_Type 	VARCHAR(20),
	House_Number 	VARCHAR(10),
	Address_Line1 	VARCHAR(50),
	Address_Line2	VARCHAR(50),
	Address_Line3	VARCHAR(50),
    Street 			VARCHAR(100),
    City_Id 		BIGINT NOT NULL REFERENCES Mas_City(City_Id),
    State_Id 		BIGINT NOT NULL REFERENCES Mas_State(State_Id),
	Zipcode 		VARCHAR(20),
    --Country_Id INT NOT NULL REFERENCES Mas_Country(Country_Id),
    CONSTRAINT FK_Customer FOREIGN KEY (Customer_Id) REFERENCES CUSTOMER(Customer_Id)
    ON DELETE CASCADE
);

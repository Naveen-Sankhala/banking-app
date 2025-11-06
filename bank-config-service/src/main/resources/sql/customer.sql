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



GRANT INSERT, UPDATE, SELECT ON CUSTOMER TO bankapp;
GRANT USAGE, SELECT, UPDATE ON SEQUENCE customer_id_seq TO bankapp;
GRANT EXECUTE ON FUNCTION Generate_Customer_Id() TO bankapp;


CREATE TABLE CUSTOMER (
    Customer_Id 	BIGSERIAL PRIMARY KEY,
    CIF_NO 			VARCHAR(50) UNIQUE NOT NULL,  -- business key
    First_Name 		VARCHAR(50) NOT NULL,
    Middle_Name 	VARCHAR(50),
    Last_Name 		VARCHAR(50),
	Gender			VARCHAR(20) NOT NULL,
	Date_Of_Birth 	Date NOT NULL,
	Aadhar_Number 	CHAR(12) UNIQUE,
	Pan_Number 		VARCHAR(10) NOT NULL,
	Contact_No 		VARCHAR(20) NOT NULL,
	Alternate_Contact_No VARCHAR(20),
	EMAIL_ID 		VARCHAR(50),
	Date_Of_Created TIMESTAMP WITH TIME ZONE DEFAULT now(),
	Date_Of_Inactive TIMESTAMP WITH TIME ZONE,
    Status 			VARCHAR(10) DEFAULT 'Active',
	Created_By      INT,
	Created_Date    TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
	Last_Chg_By     INT,
	Last_Chg_Date   TIMESTAMP WITH TIME ZONE
);

CREATE TABLE CUSTOMER_DETAILS (
	Customer_Details_Id	  BIGSERIAL PRIMARY KEY,
    Customer_Id           BIGSERIAL REFERENCES CUSTOMER(Customer_Id),  -- Unique Customer ID
    Branch_Id     		  BIGINT 	REFERENCES USERS(Branch_Id),       -- Linked branch
    Occupation_Id         BIGINT,
    Constitution_Id       BIGINT,
    Is_Minor              CHAR(1) DEFAULT 'N',
    Has_Guardian          CHAR(1) DEFAULT 'N',
    Has_Nominee           CHAR(1) DEFAULT 'N',
    Religion_Id           BIGINT,
    Caste_Id              BIGINT,
    Husband_Father_Title  VARCHAR(50)VARCHAR(10) REFERENCES MAS_GENDER_TITLE(Id),
    Husband_Father_Name   VARCHAR(150),
    Relation_Type         VARCHAR(50) REFERENCES MAS_RELATION(Relation_Id),
    Major_Date            DATE,
    Mother_Title          VARCHAR(10) REFERENCES MAS_GENDER_TITLE(Id),
    Mother_Name           VARCHAR(150),
    Mother_Relation       VARCHAR(50) REFERENCES MAS_RELATION(Relation_Id),
    Marital_Status        VARCHAR(20),
    Education_Qual        VARCHAR(100),
    Num_Dependents        INT,
    Guardian_Type         VARCHAR(50),
	Checksum_Value        VARCHAR(64),
    National_Id_Number    VARCHAR(30),
    Passport_Number       VARCHAR(20),
    Passport_Place_Issue  VARCHAR(100),
    Passport_Issue_Date   DATE,
    Passport_Expiry_Date  DATE,
    Currency_Id    		  BIGINT REFERENCES MAS_CURRENCY(Currency_Id),
	Membership_Number     VARCHAR(30),
    Employee_Number       VARCHAR(30),
	Account_Manager       VARCHAR(100),
    Customer_Group        VARCHAR(50),
	Created_By      	  BIGINT REFERENCES USERS(User_Id),
	Created_Date    	  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	Last_Chg_By     	  BIGINT REFERENCES USERS(User_Id),
	Last_Chg_Date   	  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
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
-------------------------------------------------------------------------
-------------------------------------------------------------------------

CREATE TABLE USERS (
	User_Id         BIGSERIAL PRIMARY KEY, 
	Customer_Id 	BIGINT NOT NULL,	-- FK to customer.Customer_Id
	User_Name       VARCHAR(50) UNIQUE	NOT NULL,
	Login_Name      VARCHAR(20)	NOT NULL,
	Status		    VARCHAR(1)  NOT NULL,
	Email_ID   		VARCHAR(50),
	Created_By      BIGINT,
	Created_Date    TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
	Last_Chg_By     BIGINT,
	Last_Chg_Date   TIMESTAMP WITH TIME ZONE,
	CONSTRAINT FK_Users FOREIGN KEY (Customer_Id) REFERENCES CUSTOMER(Customer_Id)
	ON DELETE CASCADE
);

Insert into USERS (User_Id,Customer_Id,USER_NAME,LOGIN_NAME,Status,EMAIL_ID,CREATED_BY,CREATED_DATE,LAST_CHG_BY,LAST_CHG_DATE) 
values (nextval('users_user_id_seq'),1,'SUPER001001','Super','Y','super.user@gmail.com',1,NOW(),1,NOW());
Insert into USERS (User_Id,Customer_Id,USER_NAME,LOGIN_NAME,Status,EMAIL_ID,CREATED_BY,CREATED_DATE,LAST_CHG_BY,LAST_CHG_DATE) 
values (nextval('users_user_id_seq'),2,'ADMIN001002','Admin','Y','adminuser@gmail.com',1,NOW(),1,NOW());
--------------------------------------------------------------
CREATE TABLE USER_LOG(
	UserLog_Id     		BIGSERIAL PRIMARY KEY,
	User_Id            	BIGINT UNIQUE NOT NULL,
	Ip_Address         	VARCHAR(15) NOT NULL,
	Is_Logged_In        VARCHAR(1) DEFAULT 'N' NOT NULL,
	Refresh_Token		TEXT,
	Logged_In_Time      	TIMESTAMP WITH TIME ZONE DEFAULT now(),
	Logged_Out_Time     	TIMESTAMP WITH TIME ZONE DEFAULT now(),
	CONSTRAINT FK_UserLog FOREIGN KEY (User_Id) REFERENCES USERS(User_Id)
);

SELECT sequence_schema, sequence_name FROM information_schema.sequences ORDER BY sequence_schema, sequence_name;
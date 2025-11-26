-------------------------------------------------------------------------
---------------------------Customer Table------------------------------------
-------------------------------------------------------------------------
--Before User table create , first create customer table



CREATE TABLE CUSTOMER (
    Customer_Id 	BIGSERIAL PRIMARY KEY,
    CIF_NO 			VARCHAR(50) UNIQUE NOT NULL,  -- business key
    First_Name 		VARCHAR(50) NOT NULL,
    Middle_Name 	VARCHAR(50),
    Last_Name 		VARCHAR(50),
	Date_Of_Created TIMESTAMP WITH TIME ZONE DEFAULT now(),
	Date_Of_Inactive TIMESTAMP WITH TIME ZONE,
	Nic_No			 VARCHAR(20),
	NicIssueDate	Date,
    Status 			VARCHAR(10) DEFAULT 'ACTIVE',
	Created_By      INT,
	Created_Date    TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
	Last_Chg_By     INT,
	Last_Chg_Date   TIMESTAMP WITH TIME ZONE
);

-- before to insert these records
-- create function to cif no genrate and trigger in  plsql script

Insert into customer(First_Name,Last_Name,Created_By,Last_Chg_By,Last_Chg_Date) values ('Super','User',1,1,NOW());
Insert into customer(First_Name,Last_Name,Created_By,Last_Chg_By,Last_Chg_Date) values ('Admin','User',1,1,NOW());
Insert into customer(First_Name,Last_Name,Created_By,Last_Chg_By,Last_Chg_Date) values ('Database','Administrator',1,1,NOW());

-------------------------------------------------------------------------
---------------------------User Table------------------------------------
-------------------------------------------------------------------------

CREATE TABLE USERS (
	User_Id         BIGSERIAL PRIMARY KEY, 
	Customer_Id 	BIGINT NOT NULL,	-- FK to customer.Customer_Id
	Branch_Id		BIGINT NOT NULL,	-- -- FK to branch.Branch_Id but map after branch table create
	User_Name       VARCHAR(50) UNIQUE	NOT NULL,
	Login_Name      VARCHAR(20)	NOT NULL,
	Status		    VARCHAR(1)  NOT NULL,
	Email_ID   		VARCHAR(50),
	Created_By      BIGINT,
	Created_Date    TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
	Last_Chg_By     BIGINT,
	Last_Chg_Date   TIMESTAMP WITH TIME ZONE
	--CONSTRAINT FK_Users FOREIGN KEY (Customer_Id) REFERENCES CUSTOMER(Customer_Id)
	--ON DELETE CASCADE
);

Insert into USERS (Customer_Id,USER_NAME,LOGIN_NAME,Status,EMAIL_ID,CREATED_BY,CREATED_DATE,LAST_CHG_BY,LAST_CHG_DATE) 
values (1,'SUPER001001','Super','Y','super.user@gmail.com',1,NOW(),1,NOW());
Insert into USERS (Customer_Id,USER_NAME,LOGIN_NAME,Status,EMAIL_ID,CREATED_BY,CREATED_DATE,LAST_CHG_BY,LAST_CHG_DATE) 
values (2,'ADMIN001002','Admin','Y','admin.user@gmail.com',1,NOW(),1,NOW());
Insert into USERS (Customer_Id,USER_NAME,LOGIN_NAME,Status,EMAIL_ID,CREATED_BY,CREATED_DATE,LAST_CHG_BY,LAST_CHG_DATE) 
values (3,'DBA001003','Database','Y','dba@gmail.com',1,NOW(),1,NOW());
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
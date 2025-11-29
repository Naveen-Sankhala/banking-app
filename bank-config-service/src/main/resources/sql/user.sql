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


-------------------------------------------------------------------------
---------------------------USER_LOG Table------------------------------------
-------------------------------------------------------------------------

CREATE TABLE USER_LOG(
	UserLog_Id     		BIGSERIAL PRIMARY KEY,
	User_Id            	BIGINT NOT NULL,
	Ip_Address         	VARCHAR(50) NOT NULL,
	Is_Logged_In        BOOLEAN DEFAULT FALSE,
	Refresh_Token		TEXT,
	Logged_In_Time      TIMESTAMP DEFAULT now(),
	Logged_Out_Time     TIMESTAMP,
	Created_At 			TIMESTAMP DEFAULT now(),
	Updated_At 			TIMESTAMP DEFAULT now(),
	CONSTRAINT FK_UserLog FOREIGN KEY (User_Id) REFERENCES USERS(User_Id)
);

CREATE INDEX IF NOT EXISTS idx_user_log_refresh_token ON user_log (refresh_token);
CREATE INDEX IF NOT EXISTS idx_user_log_userid ON user_log (user_id);

SELECT sequence_schema, sequence_name FROM information_schema.sequences ORDER BY sequence_schema, sequence_name;

-------------------------------------------------------------------------
---------------------------User_Roles Table------------------------------------
-------------------------------------------------------------------------

CREATE TABLE User_Roles(
	Userrole_Id     BIGSERIAL PRIMARY KEY,
	Role_Id         BIGINT NOT NULL,
	User_Id         BIGINT NOT NULL,
	Branch_Id		 BIGINT NOT NULL,
	Status          VARCHAR(1) DEFAULT 'Y' NOT NULL,
	Created_By      BIGINT NOT NULL REFERENCES USERS(User_Id),
	Created_Date    TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
	Last_Chg_By     BIGINT REFERENCES USERS(User_Id),
	Last_Chg_Date   TIMESTAMP WITH TIME ZONE,
	CONSTRAINT FK_User FOREIGN KEY (User_Id) REFERENCES USERS(User_Id) ON DELETE CASCADE,
	CONSTRAINT FK_MasRole FOREIGN KEY (Role_Id) REFERENCES Mas_Role(Role_Id),
	CONSTRAINT FK_Branch FOREIGN KEY (Branch_Id) REFERENCES Branch(Branch_Id)
);

--Super User
Insert Into User_Roles(User_Id,Role_Id,Branch_Id,Created_By) VALUES
(1,1,1,1),
(1,2,1,1),
(1,3,1,1),
(1,4,1,1),
(1,5,1,1),
(1,6,1,1),
(1,7,1,1),
(1,8,1,1),
(1,9,1,1),
(1,10,1,1),
(1,11,1,1),
(1,12,1,1),
(1,13,1,1),
(1,14,1,1),
(1,15,1,1),
(1,16,1,1),
(1,17,1,1),
(1,18,1,1),
(1,19,1,1),
(1,20,1,1),
(1,21,1,1),
(1,22,1,1),
(1,23,1,1),
(1,24,1,1),
(1,25,1,1),
(1,26,1,1),
(1,27,1,1),
(1,28,1,1),
(1,29,1,1),
(1,30,1,1),
(1,31,1,1);

--Admin User
Insert Into User_Roles(User_Id,Role_Id,Branch_Id,Created_By) VALUES
(2,1,1,1),
(2,2,1,1),
(2,3,1,1),
(2,4,1,1),
(2,5,1,1),
(2,6,1,1),
(2,7,1,1),
(2,8,1,1),
(2,9,1,1),
(2,10,1,1),
(2,11,1,1),
(2,12,1,1),
(2,13,1,1),
(2,14,1,1),
(2,15,1,1),
(2,16,1,1),
(2,17,1,1),
(2,18,1,1),
(2,19,1,1),
(2,20,1,1),
(2,21,1,1),
(2,22,1,1),
(2,23,1,1),
(2,24,1,1),
(2,25,1,1),
(2,26,1,1),
(2,27,1,1),
(2,28,1,1),
(2,29,1,1),
(2,30,1,1),
(2,31,1,1);


--DBA User
Insert Into User_Roles(User_Id,Role_Id,Branch_Id,Created_By) VALUES
(3,1,1,1),
(3,2,1,1),
(3,3,1,1),
(3,4,1,1),
(3,5,1,1),
(3,6,1,1),
(3,7,1,1),
(3,8,1,1),
(3,9,1,1),
(3,10,1,1),
(3,11,1,1),
(3,12,1,1),
(3,13,1,1),
(3,14,1,1),
(3,15,1,1),
(3,16,1,1),
(3,17,1,1),
(3,18,1,1),
(3,19,1,1),
(3,20,1,1),
(3,21,1,1),
(3,22,1,1),
(3,23,1,1),
(3,24,1,1),
(3,25,1,1),
(3,26,1,1),
(3,27,1,1),
(3,28,1,1),
(3,29,1,1),
(3,30,1,1),
(3,31,1,1);
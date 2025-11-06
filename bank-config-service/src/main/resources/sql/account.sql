-------------------------------------------------------------------------
-------------------------- Create Account ------------------------------
-------------------------------------------------------------------------

CREATE TABLE AccNoFormula (
    Formula_Id      SERIAL PRIMARY KEY,
    Formula_Name    VARCHAR(50) NOT NULL,       -- e.g. "StandardRule1"
    Segment_Order   INT NOT NULL,               -- order of segment (1,2,3,4...)
    Segment_Type    VARCHAR(30) NOT NULL,       -- BRANCH, CATEGORY, SERIAL, CHECKBIT, YEAR
    Segment_Length  INT NOT NULL,               -- number of digits/characters
    Pad_Char        CHAR(1) DEFAULT '0',        -- padding (for SERIAL etc.)
    Static_Value    VARCHAR(20),                -- optional static text
    Active          BOOLEAN DEFAULT TRUE
);

INSERT INTO AccNoFormula 
(Formula_Name, Segment_Order, Segment_Type, Segment_Length, Pad_Char, Static_Value) 
VALUES
('StandardRule1', 1, 'BRANCH',    7, '0', NULL),
('StandardRule1', 2, 'CATEGORY',  2, '0', NULL),
('StandardRule1', 3, 'SERIAL',    7, '0', NULL),
('StandardRule1', 4, 'CHECKBIT',  1, '0', NULL);


CREATE SEQUENCE AccountNo_Seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE FUNCTION Generate_Account_Number(
    p_branch_id BIGINT,
    p_acc_cat_id BIGINT,
    p_formula_name VARCHAR
)
RETURNS VARCHAR AS $$
DECLARE
    v_branch_code VARCHAR(10);
    v_acc_cat_code VARCHAR(10);
    v_next_seq BIGINT;
    v_serial VARCHAR(20);
    v_account_no VARCHAR(50) := '';
    v_segment RECORD;
    v_raw_number VARCHAR(50);
    v_checkbit BIGINT;
BEGIN
    -- 1. Fetch branch & category codes
    SELECT Branch_Code INTO v_branch_code 
    FROM BRANCH WHERE branch_id = p_branch_id;

    SELECT Acc_Cat_Code INTO v_acc_cat_code 
    FROM Acc_Categories WHERE acc_cat_id = p_acc_cat_id;

    -- 2. Get next serial
    v_next_seq := nextval('AccountNo_Seq');

    -- 3. Loop through formula definition
    FOR v_segment IN
        SELECT * FROM AccNoFormula
        WHERE formula_name = p_formula_name
          AND active = TRUE
        ORDER BY Segment_Order
    LOOP
        IF v_segment.segment_type = 'BRANCH' THEN
            v_account_no := v_account_no ||
                lpad(v_branch_code, v_segment.segment_length, v_segment.pad_char);

        ELSIF v_segment.segment_type = 'CATEGORY' THEN
            v_account_no := v_account_no ||
                lpad(v_acc_cat_code, v_segment.segment_length, v_segment.pad_char);

        ELSIF v_segment.segment_type = 'SERIAL' THEN
            v_serial := lpad(v_next_seq::text, v_segment.segment_length, v_segment.pad_char);
            v_account_no := v_account_no || v_serial;

        ELSIF v_segment.segment_type = 'CHECKBIT' THEN
            -- compute on all digits before checkbit
            v_raw_number := v_account_no;
            SELECT SUM((digits)::BIGINT) % 9
            INTO v_checkbit
            FROM regexp_split_to_table(v_raw_number, '') AS digits;
            v_account_no := v_account_no || v_checkbit::text;

        ELSIF v_segment.segment_type = 'STATIC' THEN
            v_account_no := v_account_no || v_segment.static_value;

        END IF;
    END LOOP;

    RETURN v_account_no;
END;
$$ LANGUAGE plpgsql;


CREATE TABLE ACCOUNT (
    Account_Id       BIGSERIAL PRIMARY KEY,
    Account_Number   VARCHAR(20) UNIQUE NOT NULL,
    Customer_Id      BIGINT NOT NULL REFERENCES CUSTOMER(Customer_Id),
    Account_Type     VARCHAR(10) NOT NULL REFERENCES Acc_Categories(Acc_Cat_SName),  -- FK to Account_Type
    Currency_Code    CHAR(3) NOT NULL DEFAULT 'USD',
    Balance          DECIMAL(18,2) NOT NULL DEFAULT 0.00,
    Status           VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    Opened_Date      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Closed_Date      TIMESTAMP,
    Branch_Id        BIGINT REFERENCES BRANCH(Branch_Id),

    -- Optional columns
    Overdraft_Limit  DECIMAL(18,2) NOT NULL DEFAULT 0.00,     
    Iban_Number      VARCHAR(34),       
    Is_Joint_Account VARCHAR(1) DEFAULT 'N',
	Is_Chq_YN		 VARCHAR(1) DEFAULT 'N',
	
    Created_By       BIGINT REFERENCES USERS(User_Id),
    Created_Date     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Last_Chg_By      BIGINT REFERENCES USERS(User_Id),
    Last_Chg_Date    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE Account_Information (
    Accinfo_Id         	BIGSERIAL PRIMARY KEY,
	Customer_Id			BIGINT NOT NULL REFERENCES Customer(Customer_Id),
    Account_Id      	BIGINT NOT NULL REFERENCES ACCOUNT(Account_Id) ON DELETE CASCADE,
    Name_Title      	VARCHAR(20),    -- e.g. Mr, Ms, Dr
    Accinfo_Name      	VARCHAR(100),   -- account holder name
    Date_Of_Birth  	    DATE,
    Gender          	VARCHAR(10),
	Accinfo_Amount		DECIMAL(18,2) NOT NULL DEFAULT 0.00,
	Accinfo_Startdate	TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	Accinfo_Enddate		TIMESTAMP,
	Accinfo_Stmtprnt	VARCHAR(1) DEFAULT 'N',
	Accinfo_Depnotice	VARCHAR(1) DEFAULT 'N',
	Accinfo_Loannotice	VARCHAR(1) DEFAULT 'N',
	Accinfo_Remarks		VARCHAR(100),
    Created_By      	BIGINT,
    Created_Date    	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Last_Chg_By     	BIGINT,
    Last_Chg_Date   	TIMESTAMP
);

CREATE TABLE Joint_Account_Holder (
    Joint_Holder_Id BIGSERIAL PRIMARY KEY,
    Account_Id      BIGINT NOT NULL REFERENCES ACCOUNT(Account_Id) ON DELETE CASCADE,
    Customer_Id     BIGINT NOT NULL REFERENCES CUSTOMER(Customer_Id) ON DELETE CASCADE,
    Holder_Type     VARCHAR(20) NOT NULL DEFAULT 'PRIMARY', -- e.g. PRIMARY, SECONDARY, NOMINEE
    Added_Date      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (Account_Id, Customer_Id)  -- prevent duplicate mapping
);



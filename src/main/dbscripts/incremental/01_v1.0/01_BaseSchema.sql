# Amortization
CREATE TABLE Account(
	AccountID			BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	AccountNumber		VARCHAR(15),
	BorrowerPersonID 	BIGINT,
	LoanTypeID 			BIGINT,
	LenderID			BIGINT
);
CREATE TABLE AmortizationLoanPayment ( 
	AmortizationLoanPaymentID 	BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	LoanAmortizationScheduleID	BIGINT NULL,
	PaymentOrder						INT NULL,
	PaymentAmount             	INT NULL,
	PaymentCount              	INT NULL
);
CREATE TABLE AmortizationSchedule ( 
	AmortizationScheduleID	BIGINT AUTO_INCREMENT NOT NULL,
	AccountID				BIGINT NULL,
	Invalid					SMALLINT NULL,
	CreationDate          	DATETIME NULL,
	EffectiveDate         	DATETIME NULL,
	PRIMARY KEY(AmortizationScheduleID)
);

CREATE TABLE LoanAmortizationSchedule ( 
	LoanAmortizationScheduleID	BIGINT AUTO_INCREMENT NOT NULL,
	LoanID                    	BIGINT NULL,
	AmortizationScheduleID    	BIGINT NULL,
	PRIMARY KEY(LoanAmortizationScheduleID)
);

CREATE TABLE Lender(
	LenderID 	BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	Name 		VARCHAR(25)
);

# Billing Statement
CREATE TABLE BillingStatement ( 
	BillingStatementID    	BIGINT AUTO_INCREMENT NOT NULL,
	LoanID                	BIGINT NULL,
	CreatedDate           	DATETIME NULL,
	DueDate               	DATE NULL,
	MinimumRequiredPayment	INT NULL,
	PRIMARY KEY(BillingStatementID)
);
CREATE TABLE LateFee ( 
	LateFeeID           	BIGINT AUTO_INCREMENT NOT NULL,
	BillingStatementID  	BIGINT NULL,
	FeeAmount           	INT NULL,
	Cancelled				SMALLINT,
	EffectiveDate			DATE,
	PostedDate				DATETIME,
	PRIMARY KEY(LateFeeID)
);

# Loan
CREATE TABLE Loan ( 
	LoanID                    			BIGINT AUTO_INCREMENT NOT NULL,
	ServicingStartDate        			DATE NULL,
	LoanTypeID                			BIGINT NULL,
	BorrowerPersonID          			BIGINT NULL,
	EffectiveLoanTypeProfileID			BIGINT NULL,
	CurrentLoanAmortizationScheduleID	BIGINT NULL,
	StartingPrincipal         			INT NOT NULL,
	StartingInterest          			DECIMAL(20,6) NOT NULL,
	StartingFees              			INT NOT NULL,
	InitialUsedLoanTerm         		INT NULL,
	FirstDueDate              			DATE NULL,
	InitialDueDate            			DATE NULL,
	LenderID 							BIGINT NULL,
	AccountID 							BIGINT NULL,
	PRIMARY KEY(LoanID)
);
CREATE TABLE Disbursement(
	DisbursementID				BIGINT AUTO_INCREMENT NOT NULL,
	LoanID						BIGINT NULL,
	DisbursementEffectiveDate	DATE NULL,
	DisbursementAmount			INT NULL,
	PRIMARY KEY(DisbursementID)
);

CREATE TABLE LoanType ( 
	LoanTypeID 	BIGINT NOT NULL,
	Name       	VARCHAR(15) NULL,
	Description	VARCHAR(45) NULL,
	PRIMARY KEY(LoanTypeID)
);
CREATE TABLE LoanTypeProfile ( 
	LoanTypeProfileID        	BIGINT AUTO_INCREMENT NOT NULL,
	LoanTypeID               	BIGINT NULL,
	EffectiveDate            	DATETIME NULL,
	EndDate                  	DATETIME NULL,
	RepaymentStartTypeID     	BIGINT NULL,
	MaximumLoanTerm          	INT NULL,
	GraceMonths              	INT NULL,
	MinDaysToFirstDue        	INT NULL,
	PrepaymentDays           	INT NULL,
	DaysBeforeDueToBill      	INT NULL,
	DaysLateForFee           	INT NULL,
	LateFeeAmount            	INT NULL,
	VariableRate             	SMALLINT NULL,
	BaseRateUpdateFrequencyID	BIGINT NULL,
	BaseRateID               	BIGINT NULL,
	PRIMARY KEY(LoanTypeProfileID)
);

# Repayment
CREATE TABLE LoanPayment ( 
	LoanPaymentID	BIGINT AUTO_INCREMENT NOT NULL,
	PaymentID    	BIGINT NULL,
	LoanID       	BIGINT NULL,
	AppliedAmount	INT NULL,
	PRIMARY KEY(LoanPaymentID)
);
CREATE TABLE Payment ( 
	PaymentID       	BIGINT AUTO_INCREMENT NOT NULL,
	BorrowerPersonID	BIGINT NULL,
	PaymentAmount   	INT NULL,
	PostDate        	DATETIME NULL,
	EffectiveDate   	DATE NULL,
	PRIMARY KEY(PaymentID)
);
CREATE TABLE RepaymentStartType ( 
	RepaymentStartTypeID	BIGINT NOT NULL,
	Name                	VARCHAR(25) NULL,
	Description         	VARCHAR(50) NULL,
	PRIMARY KEY(RepaymentStartTypeID)
);

# Interest
CREATE TABLE FrequencyType ( 
	FrequencyTypeID	BIGINT AUTO_INCREMENT NOT NULL,
	Name           	VARCHAR(50) NULL,
	PRIMARY KEY(FrequencyTypeID)
);
CREATE TABLE Rate ( 
	RateID      		BIGINT AUTO_INCREMENT NOT NULL,
	RateName    		VARCHAR(50) NULL,
	TickerSymbol		VARCHAR(20) NULL,
	ShouldAutoUpdate  	SMALLINT NULL,
	PRIMARY KEY(RateID)
);
CREATE TABLE RateValue ( 
	RateValueID  	BIGINT AUTO_INCREMENT NOT NULL,
	RateID       	BIGINT NULL,
	RateValueDate	DATE NULL,
	RateValue    	DECIMAL(20,6) NULL,
	IsValid      	SMALLINT NULL,
	PRIMARY KEY(RateValueID)
);
CREATE TABLE LoanRateValue (
	LoanRateValueID BIGINT AUTO_INCREMENT NOT NULL,
	LoanID			BIGINT NULL,
	RateValueID		BIGINT NULL,
	MarginValue		DECIMAL(20,6) NULL,
	LockedDate		DATE NULL,
	PRIMARY KEY(LoanRateValueID)
);

CREATE TABLE LoanBalanceAdjustment ( 
	LoanBalanceAdjustmentID	BIGINT AUTO_INCREMENT NOT NULL,
	LoanAdjustmentTypeID   	BIGINT NULL,
	LoanID                 	BIGINT NULL,
	PrincipalChange        	INT NULL,
	InterestChange         	INT NULL,
	FeesChange             	INT NULL,
	EffectiveDate          	DATE NULL,
	PostDate               	DATETIME NULL,
	PRIMARY KEY(LoanBalanceAdjustmentID)
);
CREATE TABLE Person ( 
	PersonID 	BIGINT AUTO_INCREMENT NOT NULL,
	SSN      	CHAR(9) NOT NULL,
	FirstName	VARCHAR(15) NOT NULL,
	LastName 	VARCHAR(35) NOT NULL,
	PRIMARY KEY(PersonID)
);

# Security
CREATE TABLE SecAssignedPermission ( 
	AssignedPermissionID	BIGINT AUTO_INCREMENT NOT NULL,
	PermissionID        	BIGINT NULL,
	PrincipalID         	BIGINT NULL,
	PRIMARY KEY(AssignedPermissionID)
);
CREATE TABLE SecAssignedRole ( 
	RolePrincipalID	BIGINT NULL,
	UserPrincipalID	BIGINT NULL 
	);
CREATE TABLE SecPermission ( 
	PermissionID	BIGINT AUTO_INCREMENT NOT NULL,
	Name        	VARCHAR(50) NULL,
	PRIMARY KEY(PermissionID)
);
CREATE TABLE SecPrincipal ( 
	PrincipalID	BIGINT AUTO_INCREMENT NOT NULL,
	Name       	VARCHAR(50) NULL,
	Active     	SMALLINT NULL,
	PRIMARY KEY(PrincipalID)
);
CREATE TABLE SecSystemRole ( 
	PrincipalID	BIGINT NOT NULL,
	Description	VARCHAR(50) NULL,
	PRIMARY KEY(PrincipalID)
);
CREATE TABLE SecSystemUser ( 
	PrincipalID         	BIGINT NOT NULL,
	Username            	VARCHAR(25) NULL,
	Password            	VARCHAR(150) NULL,
	LockedOut           	SMALLINT NULL,
	LastSuccessfullLogin	DATETIME NULL,
	PRIMARY KEY(PrincipalID)
);
CREATE TABLE userconnection ( 
	userId        	VARCHAR(255) NOT NULL,
	providerId    	VARCHAR(255) NOT NULL,
	providerUserId	VARCHAR(255) NOT NULL,
	rank          	INT NOT NULL,
	displayName   	VARCHAR(255) NULL,
	profileUrl    	VARCHAR(512) NULL,
	imageUrl      	VARCHAR(512) NULL,
	accessToken   	VARCHAR(255) NOT NULL,
	secret        	VARCHAR(255) NULL,
	refreshToken  	VARCHAR(255) NULL,
	expireTime    	BIGINT NULL,
	PRIMARY KEY(userId,providerId,providerUserId)
);

# System Configuration
CREATE TABLE SystemSettings ( 
	SystemSettingsID		BIGINT NOT NULL,
	DaysDelta	  	     	INT NULL,
	ShouldTriggerBatch    	SMALLINT NULL,
	PRIMARY KEY(SystemSettingsID)
);

INSERT INTO LoanType values (10, 'PRIVATE_STUDENT', 'Private Student Loan');
INSERT INTO LoanType values (20, 'MORTGAGE', 'Mortgage Loan');

INSERT INTO RepaymentStartType values (10, 'FIRST_DISBURSEMENT', 'Repayment begins after first disbursement.');
INSERT INTO RepaymentStartType values (20, 'LAST_DISBURSEMENT', 'Repayment begins after last disbursement.');

INSERT INTO FrequencyType (FrequencyTypeID, Name) value (10, 'MONTHLY');
INSERT INTO FrequencyType (FrequencyTypeID, Name) value (20, 'QUARTERLY');
INSERT INTO FrequencyType (FrequencyTypeID, Name) value (30, 'SEMI_ANNUALLY');
INSERT INTO FrequencyType (FrequencyTypeID, Name) value (40, 'ANNUALLY');

INSERT INTO LoanTypeProfile(LoanTypeProfileID, LoanTypeID, EffectiveDate, EndDate, RepaymentStartTypeID, MaximumLoanTerm, GraceMonths, MinDaysToFirstDue, PrepaymentDays, DaysBeforeDueToBill, DaysLateForFee, LateFeeAmount, VariableRate, BaseRateUpdateFrequencyID, BaseRateID)
  VALUES(1, 20, '1900-01-17 22:22:51.0', NULL, 10, 180, 1, 1, 1, 1, 1, 1000, 1, 20, 10);
INSERT INTO LoanTypeProfile(LoanTypeProfileID, LoanTypeID, EffectiveDate, EndDate, RepaymentStartTypeID, MaximumLoanTerm, GraceMonths, MinDaysToFirstDue, PrepaymentDays, DaysBeforeDueToBill, DaysLateForFee, LateFeeAmount, VariableRate, BaseRateUpdateFrequencyID, BaseRateID)
  VALUES(2, 10, '1900-01-17 22:22:51.0', NULL, 10, 180, 1, 1, 1, 1, 1, 1000, 1, 30, 10);

INSERT INTO Rate(RateID, RateName, TickerSymbol, ShouldAutoUpdate)
  VALUES(10, '1 Month LIBOR US Dollars', 'LIBOR.USD1M', 0);
INSERT INTO RateValue(RateValueID, RateID, RateValueDate, RateValue, IsValid)
  VALUES(10, 10, '2000-01-01', 0.035000, 1);
INSERT INTO RateValue(RateValueID, RateID, RateValueDate, RateValue, IsValid)
  VALUES(11, 10, '2012-01-01', .036, 1);
INSERT INTO RateValue(RateValueID, RateID, RateValueDate, RateValue, IsValid)
  VALUES(12, 10, '2012-02-01', .037, 1);
INSERT INTO RateValue(RateValueID, RateID, RateValueDate, RateValue, IsValid)
  VALUES(13, 10, '2012-03-01', .038, 1);
INSERT INTO RateValue(RateValueID, RateID, RateValueDate, RateValue, IsValid)
  VALUES(14, 10, '2012-04-01', .039, 1);
INSERT INTO RateValue(RateValueID, RateID, RateValueDate, RateValue, IsValid)
  VALUES(15, 10, '2012-05-01', .040, 1);
INSERT INTO RateValue(RateValueID, RateID, RateValueDate, RateValue, IsValid)
  VALUES(16, 10, '2012-06-01', .030, 1);
INSERT INTO RateValue(RateValueID, RateID, RateValueDate, RateValue, IsValid)
  VALUES(17, 10, '2012-07-01', .031, 1);
INSERT INTO RateValue(RateValueID, RateID, RateValueDate, RateValue, IsValid)
  VALUES(18, 10, '2012-08-01', .032, 1);
INSERT INTO RateValue(RateValueID, RateID, RateValueDate, RateValue, IsValid)
  VALUES(19, 10, '2012-09-01', .033, 1);
INSERT INTO RateValue(RateValueID, RateID, RateValueDate, RateValue, IsValid)
  VALUES(20, 10, '2012-10-01', .034, 1);
INSERT INTO RateValue(RateValueID, RateID, RateValueDate, RateValue, IsValid)
  VALUES(21, 10, '2012-11-01', .035, 1);
INSERT INTO RateValue(RateValueID, RateID, RateValueDate, RateValue, IsValid)
  VALUES(22, 10, '2012-12-01', .036, 1);

INSERT INTO SystemSettings VALUES (10, 0, 0);

INSERT INTO secprincipal(PrincipalID, Name, Active)
  VALUES(1, 'Admin User', 1);
INSERT INTO secprincipal(PrincipalID, Name, Active)
  VALUES(2, 'Admin Role', 1);
INSERT INTO secsystemuser(PrincipalID, Username, Password, LockedOut, LastSuccessfullLogin)
    VALUES(1, 'admin', '1b771698e9d4723bfd35818165db49b7', 0, '2012-12-13 00:00:00.0');
INSERT INTO secsystemrole(PrincipalID, Description)
  VALUES(2, 'Admin Role');
INSERT INTO secpermission(PermissionID, Name)
  VALUES(4, 'ViewAccountSummary');
INSERT INTO secpermission(PermissionID, Name)
  VALUES(3, 'CreateAmortizationSchedule');
INSERT INTO secpermission(PermissionID, Name)
  VALUES(1, 'AddLoan');
INSERT INTO secpermission(PermissionID, Name)
  VALUES(2, 'AddPayment');
INSERT INTO secassignedrole(RolePrincipalID, UserPrincipalID)
  VALUES(2, 1);
INSERT INTO secassignedpermission(AssignedPermissionID, PermissionID, PrincipalID)
  VALUES(1, 1, 2);
INSERT INTO secassignedpermission(AssignedPermissionID, PermissionID, PrincipalID)
  VALUES(2, 2, 2);
INSERT INTO secassignedpermission(AssignedPermissionID, PermissionID, PrincipalID)
  VALUES(3, 3, 2);
INSERT INTO secassignedpermission(AssignedPermissionID, PermissionID, PrincipalID)
  VALUES(4, 4, 2);

INSERT INTO Lender(name)
  VALUES('Test Lender');
  
ALTER TABLE AmortizationSchedule
	ADD CONSTRAINT amortizationschedule_account_fk
		FOREIGN KEY(AccountID)
		REFERENCES Account(AccountID)
		ON DELETE RESTRICT
		ON UPDATE RESTRICT;
ALTER TABLE AmortizationLoanPayment
	ADD CONSTRAINT amortizationloanpayment_ibfk_1
		FOREIGN KEY(LoanAmortizationScheduleID)
		REFERENCES LoanAmortizationSchedule(LoanAmortizationScheduleID)
		ON DELETE RESTRICT 
		ON UPDATE RESTRICT ;
ALTER TABLE LoanAmortizationSchedule
	ADD CONSTRAINT loanamortizationschedule_ibfk_2
		FOREIGN KEY(LoanID)
		REFERENCES Loan(LoanID)
		ON DELETE RESTRICT 
		ON UPDATE RESTRICT,
	ADD CONSTRAINT loanamortizationschedule_ibfk_1
		FOREIGN KEY(AmortizationScheduleID)
		REFERENCES AmortizationSchedule(AmortizationScheduleID)
		ON DELETE RESTRICT 
		ON UPDATE RESTRICT ;

ALTER TABLE userconnection
	ADD CONSTRAINT UserConnectionRank
		UNIQUE (userId, providerId, rank);
ALTER TABLE SecAssignedPermission
	ADD CONSTRAINT secassignedpermission_ibfk_2
		FOREIGN KEY(PrincipalID)
		REFERENCES SecPrincipal(PrincipalID)
		ON DELETE RESTRICT 
		ON UPDATE RESTRICT,
	ADD CONSTRAINT secassignedpermission_ibfk_1
		FOREIGN KEY(PermissionID)
		REFERENCES SecPermission(PermissionID)
		ON DELETE RESTRICT 
		ON UPDATE RESTRICT ;
ALTER TABLE SecAssignedRole
	ADD CONSTRAINT secassignedrole_ibfk_2
		FOREIGN KEY(UserPrincipalID)
		REFERENCES SecSystemUser(PrincipalID)
		ON DELETE RESTRICT 
		ON UPDATE RESTRICT,
	ADD CONSTRAINT secassignedrole_ibfk_1
		FOREIGN KEY(RolePrincipalID)
		REFERENCES SecSystemRole(PrincipalID)
		ON DELETE RESTRICT 
		ON UPDATE RESTRICT ;
ALTER TABLE SecSystemRole
	ADD CONSTRAINT secsystemrole_ibfk_1
		FOREIGN KEY(PrincipalID)
		REFERENCES SecPrincipal(PrincipalID)
		ON DELETE RESTRICT 
		ON UPDATE RESTRICT ;
ALTER TABLE SecSystemUser
	ADD CONSTRAINT secsystemuser_ibfk_1
		FOREIGN KEY(PrincipalID)
		REFERENCES SecPrincipal(PrincipalID)
		ON DELETE RESTRICT 
		ON UPDATE RESTRICT ;

ALTER TABLE BillingStatement
	ADD CONSTRAINT billingstatement_ibfk_1
		FOREIGN KEY(LoanID)
		REFERENCES Loan(LoanID)
		ON DELETE RESTRICT 
		ON UPDATE RESTRICT ;
ALTER TABLE LateFee
	ADD CONSTRAINT latefee_ibfk_1
		FOREIGN KEY(BillingStatementID)
		REFERENCES BillingStatement(BillingStatementID)
		ON DELETE RESTRICT 
		ON UPDATE RESTRICT ;
ALTER TABLE Account
	ADD CONSTRAINT account_person_fk
		FOREIGN KEY(BorrowerPersonID)
		REFERENCES Person(PersonID)
		ON DELETE RESTRICT 
		ON UPDATE RESTRICT,
	ADD CONSTRAINT account_loantype_fk
		FOREIGN KEY(LoanTypeID)
		REFERENCES LoanType(LoanTypeID)
		ON DELETE RESTRICT 
		ON UPDATE RESTRICT,
	ADD CONSTRAINT account_lender_fk
		FOREIGN KEY(LenderID)
		REFERENCES Lender(LenderID)
		ON DELETE RESTRICT 
		ON UPDATE RESTRICT;

ALTER TABLE Loan
	ADD CONSTRAINT loan_lender_fk
		FOREIGN KEY(LenderID)
		REFERENCES Lender(LenderID)
		ON DELETE RESTRICT
		ON UPDATE RESTRICT,
	ADD CONSTRAINT loan_account_fk
		FOREIGN KEY(AccountID)
		REFERENCES Account(AccountID)
		ON DELETE RESTRICT
		ON UPDATE RESTRICT,
	ADD CONSTRAINT loan_ibfk_3
		FOREIGN KEY(EffectiveLoanTypeProfileID)
		REFERENCES LoanTypeProfile(LoanTypeProfileID)
		ON DELETE RESTRICT 
		ON UPDATE RESTRICT,
	ADD CONSTRAINT loan_ibfk_2
		FOREIGN KEY(LoanTypeID)
		REFERENCES LoanType(LoanTypeID)
		ON DELETE RESTRICT 
		ON UPDATE RESTRICT,
	ADD CONSTRAINT loan_ibfk_1
		FOREIGN KEY(BorrowerPersonID)
		REFERENCES Person(PersonID)
		ON DELETE RESTRICT 
		ON UPDATE RESTRICT ;

ALTER TABLE LoanBalanceAdjustment
	ADD CONSTRAINT loanbalanceadjustment_ibfk_2
		FOREIGN KEY(LoanID)
		REFERENCES Loan(LoanID)
		ON DELETE RESTRICT 
		ON UPDATE RESTRICT ;
ALTER TABLE Disbursement
	ADD CONSTRAINT disbursement_loan_fk
		FOREIGN KEY(LoanID)
		REFERENCES Loan(LoanID)
		ON DELETE RESTRICT
		ON UPDATE RESTRICT;
ALTER TABLE LoanPayment
	ADD CONSTRAINT loanpayment_ibfk_2
		FOREIGN KEY(LoanID)
		REFERENCES Loan(LoanID)
		ON DELETE RESTRICT 
		ON UPDATE RESTRICT,
	ADD CONSTRAINT loanpayment_ibfk_1
		FOREIGN KEY(PaymentID)
		REFERENCES Payment(PaymentID)
		ON DELETE RESTRICT 
		ON UPDATE RESTRICT ;
ALTER TABLE LoanTypeProfile
	ADD CONSTRAINT loantypeprofile_ibfk_3
		FOREIGN KEY(BaseRateID)
		REFERENCES Rate(RateID)
		ON DELETE RESTRICT 
		ON UPDATE RESTRICT,
	ADD CONSTRAINT loantypeprofile_ibfk_2
		FOREIGN KEY(BaseRateUpdateFrequencyID)
		REFERENCES FrequencyType(FrequencyTypeID)
		ON DELETE RESTRICT 
		ON UPDATE RESTRICT,
	ADD CONSTRAINT loantypeprofile_ibfk_4
		FOREIGN KEY(RepaymentStartTypeID)
		REFERENCES RepaymentStartType(RepaymentStartTypeID)
		ON DELETE RESTRICT
		ON UPDATE RESTRICT,
	ADD CONSTRAINT loantypeprofile_ibfk_1
		FOREIGN KEY(LoanTypeID)
		REFERENCES LoanType(LoanTypeID)
		ON DELETE RESTRICT 
		ON UPDATE RESTRICT ;

ALTER TABLE Payment
	ADD CONSTRAINT payment_ibfk_1
		FOREIGN KEY(BorrowerPersonID)
		REFERENCES Person(PersonID)
		ON DELETE RESTRICT 
		ON UPDATE RESTRICT ;

ALTER TABLE RateValue
	ADD CONSTRAINT ratevalue_ibfk_1
		FOREIGN KEY(RateID)
		REFERENCES Rate(RateID)
		ON DELETE RESTRICT 
		ON UPDATE RESTRICT ;
ALTER TABLE LoanRateValue
	ADD CONSTRAINT loanratevalue_loan_fk
		FOREIGN KEY(LoanID)
		REFERENCES Loan(LoanID)
		ON DELETE RESTRICT
		ON UPDATE RESTRICT,
	ADD CONSTRAINT loanratevalue_ratevalue_fk
		FOREIGN KEY(RateValueID)
		REFERENCES RateValue(RateValueID)
		ON DELETE RESTRICT
		ON UPDATE RESTRICT;

ALTER TABLE Person
	ADD CONSTRAINT ssn_unique
		UNIQUE (SSN);
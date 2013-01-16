# Amortization
CREATE TABLE AmortizationLoanPayment ( 
	AmortizationLoanPaymentID 	BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	LoanAmortizationScheduleID	BIGINT NULL,
	PaymentAmount             	INT NULL,
	PaymentCount              	INT NULL
);
CREATE TABLE AmortizationSchedule ( 
	AmortizationScheduleID	BIGINT AUTO_INCREMENT NOT NULL,
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

# Billing Statement
CREATE TABLE BillingStatement ( 
	BillingStatementID    	BIGINT AUTO_INCREMENT NOT NULL,
	LoanID                	BIGINT NULL,
	CreatedDate           	DATETIME NULL,
	DueDate               	DATE NULL,
	MinimumRequiredPayment	INT NULL,
	PaidAmount            	INT NULL,
	SatisfiedDate         	DATE NULL,
	PRIMARY KEY(BillingStatementID)
);
CREATE TABLE BillPayment ( 
	BillPaymentID      	BIGINT AUTO_INCREMENT NOT NULL,
	BillingStatementID 	BIGINT NULL,
	LoanPaymentID      	BIGINT NULL,
	AmountAppliedToBill	INT NULL,
	PRIMARY KEY(BillPaymentID)
);
CREATE TABLE LateFee ( 
	LateFeeID           	BIGINT AUTO_INCREMENT NOT NULL,
	BillingStatementID  	BIGINT NULL,
	FeeAmount           	INT NULL,
	EffectiveDate			DATE,
	PostedDate				DATETIME,
	PRIMARY KEY(LateFeeID)
);

# Loan
CREATE TABLE Loan ( 
	LoanID                    	BIGINT AUTO_INCREMENT NOT NULL,
	ServicingStartDate        	DATE NULL,
	LoanTypeID                	BIGINT NULL,
	BorrowerPersonID          	BIGINT NULL,
	EffectiveLoanTypeProfileID	BIGINT NULL,
	StartingPrincipal         	INT NOT NULL,
	StartingInterest          	DECIMAL(20,6) NOT NULL,
	StartingFees              	INT NOT NULL,
	StartingLoanTerm          	INT NULL,
	MinimumPaymentAmount      	INT NULL,
	RepaymentStartDate        	DATETIME NULL,
	FirstDueDate              	DATE NULL,
	LastPaidDate              	DATE NULL,
	CurrentUnpaidDueDate      	DATE NULL,
	InitialDueDate            	DATE NULL,
	NextDueDate	            	DATE NULL,
	PRIMARY KEY(LoanID)
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
	IsActive     	SMALLINT NULL,
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
	IsLockedOut           	SMALLINT NULL,
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
	ON UPDATE RESTRICT ;
ALTER TABLE LoanAmortizationSchedule
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
	ON UPDATE RESTRICT ;
ALTER TABLE SecAssignedPermission
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
	ON UPDATE RESTRICT ;
ALTER TABLE SecAssignedRole
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
ALTER TABLE BillPayment
	ADD CONSTRAINT billpayment_ibfk_2
	FOREIGN KEY(LoanPaymentID)
	REFERENCES LoanPayment(LoanPaymentID)
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ;
ALTER TABLE BillPayment
	ADD CONSTRAINT billpayment_ibfk_1
	FOREIGN KEY(BillingStatementID)
	REFERENCES BillingStatement(BillingStatementID)
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ;
ALTER TABLE LateFee
	ADD CONSTRAINT latefee_ibfk_1
	FOREIGN KEY(BillingStatementID)
	REFERENCES BillingStatement(BillingStatementID)
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ;

ALTER TABLE Loan
	ADD CONSTRAINT loan_ibfk_3
	FOREIGN KEY(EffectiveLoanTypeProfileID)
	REFERENCES LoanTypeProfile(LoanTypeProfileID)
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ;
ALTER TABLE Loan
	ADD CONSTRAINT loan_ibfk_2
	FOREIGN KEY(LoanTypeID)
	REFERENCES LoanType(LoanTypeID)
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ;
ALTER TABLE Loan
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
ALTER TABLE LoanPayment
	ADD CONSTRAINT loanpayment_ibfk_2
	FOREIGN KEY(LoanID)
	REFERENCES Loan(LoanID)
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ;
ALTER TABLE LoanPayment
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
	ON UPDATE RESTRICT ;
ALTER TABLE LoanTypeProfile
	ADD CONSTRAINT loantypeprofile_ibfk_2
	FOREIGN KEY(BaseRateUpdateFrequencyID)
	REFERENCES FrequencyType(FrequencyTypeID)
	ON DELETE RESTRICT 
	ON UPDATE RESTRICT ;
ALTER TABLE LoanTypeProfile
	ADD CONSTRAINT loantypeprofile_ibfk_4
	FOREIGN KEY(RepaymentStartTypeID)
	REFERENCES RepaymentStartType(RepaymentStartTypeID)
	ON DELETE RESTRICT
	ON UPDATE RESTRICT;
ALTER TABLE LoanTypeProfile
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

INSERT INTO LoanType values (10, 'PRIVATE_STUDENT', 'Private Student Loan');
INSERT INTO LoanType values (20, 'MORTGAGE', 'Mortgage Loan');

INSERT INTO RepaymentStartType values (10, 'FIRST_DISBURSEMENT', 'Repayment begins after first disbursement.');
INSERT INTO RepaymentStartType values (20, 'LAST_DISBURSEMENT', 'Repayment begins after last disbursement.');

INSERT INTO FrequencyType (FrequencyTypeID, Name) value (10, 'MONTHLY');
INSERT INTO FrequencyType (FrequencyTypeID, Name) value (20, 'QUARTERLY');
INSERT INTO FrequencyType (FrequencyTypeID, Name) value (30, 'SEMI_ANNUALLY');
INSERT INTO FrequencyType (FrequencyTypeID, Name) value (40, 'ANNUALLY');
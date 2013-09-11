ALTER TABLE DefaultLoanProgramSettings
    DROP FOREIGN KEY defaultloanprogramsettings_repaymentplansettings_fk;

DROP TABLE RepaymentPlanSettings;

CREATE TABLE RepaymentPlan (
	RepaymentPlanID  		BIGINT PRIMARY KEY NOT NULL,
	PlanStartDateID			BIGINT		NULL
);

CREATE TABLE StandardRepaymentPlan (
	RepaymentPlanID  		BIGINT PRIMARY KEY NOT NULL,
	MaxLoanTerm 			INT	NULL,
	MinPaymentAmount		INT NULL
);

CREATE TABLE FixedRepaymentPlan (
	RepaymentPlanID  		BIGINT 		PRIMARY KEY NOT NULL,
	PaymentAmount			INT 		NULL,
	CapFrequencyID			BIGINT 		NULL,
	CapAtEnd 				SMALLINT	NULL
);

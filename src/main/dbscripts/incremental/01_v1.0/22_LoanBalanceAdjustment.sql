CREATE TABLE LoanBalanceAdjustment(
    LoanBalanceAdjustmentID BIGINT PRIMARY KEY AUTO_INCREMENT,
    LoanAdjustmentTypeID BIGINT,
    LoanID BIGINT,
    PrincipalChange INTEGER,
    InterestChange INTEGER,
    FeesChange INTEGER,
    EffectiveDate DATE,
    PostDate DATETIME
);

CREATE TABLE LoanAdjustmentType(
	LoanAdjustmentTypeID BIGINT PRIMARY KEY,
	Name VARCHAR(25),
	Description VARCHAR(45)
);

ALTER TABLE LoanBalanceAdjustment
ADD FOREIGN KEY LoanBalanceAdjustment_LoanAdjustmentType_FK (LoanAdjustmentTypeID) REFERENCES LoanAdjustmentType(LoanAdjustmentTypeID);

ALTER TABLE LoanBalanceAdjustment
ADD FOREIGN KEY LoanBalanceAdjustment_Loan_FK (LoanID) REFERENCES Loan(LoanID);

INSERT INTO LoanAdjustmentType VALUES (10, 'Disbursement', 'Disbursement Made');
INSERT INTO LoanAdjustmentType VALUES (20, 'Manual Fixed Adjustment', 'Fixed Adjustment applied manually');
INSERT INTO LoanAdjustmentType VALUES (30, 'Payment', 'Payment Applied');

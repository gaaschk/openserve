CREATE TABLE RepaymentPlanSettings ( 
	RepaymentPlanSettingsID	BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	Name       				VARCHAR(15) NULL,
	Description				VARCHAR(45) NULL,
	PlanStartDateID			BIGINT		NULL,
	CapInterestAtBegin 		SMALLINT	NULL,
	CapFrequencyID			BIGINT		NULL
	);

#ALTER TABLE DefaultLoanProgramSettings
#	ADD COLUMN RepaymentPlanSettingsID BIGINT NULL,
#	ADD CONSTRAINT defaultloanprogramsettings_repaymentplansettings_fk
#		FOREIGN KEY(RepaymentPlanSettingsID)
#		REFERENCES RepaymentPlanSettings(RepaymentPlanSettingsID)
#		ON DELETE RESTRICT
#		ON UPDATE RESTRICT;

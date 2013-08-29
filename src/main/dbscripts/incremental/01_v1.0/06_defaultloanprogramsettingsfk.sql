ALTER TABLE DefaultLoanProgramSettings
	ADD COLUMN RepaymentPlanSettingsID BIGINT NULL,
	ADD CONSTRAINT defaultloanprogramsettings_repaymentplansettings_fk
		FOREIGN KEY(RepaymentPlanSettingsID)
		REFERENCES RepaymentPlanSettings(RepaymentPlanSettingsID)
		ON DELETE RESTRICT
		ON UPDATE RESTRICT;

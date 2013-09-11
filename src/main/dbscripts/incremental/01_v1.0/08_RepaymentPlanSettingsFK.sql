ALTER TABLE RepaymentPlan
	ADD COLUMN DefaultLoanProgramSettingsID	BIGINT	NULL,
	ADD CONSTRAINT repaymentplan_defaultloanprogramsettings_fk
		FOREIGN KEY(DefaultLoanProgramSettingsID)
		REFERENCES DefaultLoanProgramSettings(DefaultLoanProgramSettingsID)
		ON DELETE RESTRICT
		ON UPDATE RESTRICT;
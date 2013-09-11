ALTER TABLE RepaymentPlan
	ADD COLUMN GraceMonths	INT 	NULL,
	ADD CONSTRAINT repaymentplan_defaultloanprogramsettings_fk
		FOREIGN KEY(DefaultLoanProgramSettingsID)
		REFERENCES DefaultLoanProgramSettings(DefaultLoanProgramSettingsID)
		ON DELETE RESTRICT
		ON UPDATE RESTRICT;

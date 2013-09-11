DROP TABLE RepaymentPlan;

CREATE TABLE RepaymentPlan (
	RepaymentPlanID                 BIGINT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
	PlanStartDateID                 BIGINT	NULL,
    DefaultLoanProgramSettingsID    BIGINT  NULL
);

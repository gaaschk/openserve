package org.gsoft.openserv.domain.loan;

import java.util.Date;

import org.gsoft.openserv.domain.rates.Rate;
import org.gsoft.openserv.util.time.FrequencyType;

public class LoanProgramSettings {
	private DefaultLoanProgramSettings defaultSettings;
	private LenderLoanProgramSettings lenderSpecificSettigs;
	
	public LoanProgramSettings(){}
	
	public LoanProgramSettings(DefaultLoanProgramSettings ltp, LenderLoanProgramSettings llp){
		this.defaultSettings = ltp;
		this.lenderSpecificSettigs = llp;
	}
	
	public Date getEffectiveDate(){
		Date effectiveDate = this.defaultSettings.getEffectiveDate();
		Date lenderEffectiveDate = this.lenderSpecificSettigs.getProgramBeginDate();
		effectiveDate = (effectiveDate.before(lenderEffectiveDate))?lenderEffectiveDate:effectiveDate;
		return effectiveDate;
	}

	public Date getEndDate(){
		Date endDate = this.defaultSettings.getEndDate();
		Date lenderEndDate = this.lenderSpecificSettigs.getProgramEndDate();
		if(endDate == null || (lenderEndDate != null && endDate.after(lenderEndDate)))
			endDate = lenderEndDate;
		return endDate;
	}
	
	public FrequencyType getBaseRateUpdateFrequency() {
		if(this.lenderSpecificSettigs.getBaseRateUpdateFrequency() == null)
			return this.defaultSettings.getBaseRateUpdateFrequency();
		return this.lenderSpecificSettigs.getBaseRateUpdateFrequency();
	}

	public Rate getBaseRate() {
		if(this.lenderSpecificSettigs.getBaseRate() == null)
			return this.defaultSettings.getBaseRate();
		return this.lenderSpecificSettigs.getBaseRate();
	}

	public RepaymentStartType getRepaymentStartType() {
		if(this.lenderSpecificSettigs.getRepaymentStartType() == null)
			return this.defaultSettings.getRepaymentStartType();
		return this.lenderSpecificSettigs.getRepaymentStartType();
	}

	public int getGraceMonths() {
		if(this.lenderSpecificSettigs.getGraceMonths() == null)
			return this.defaultSettings.getGraceMonths();
		return this.lenderSpecificSettigs.getGraceMonths();
	}

	public int getMinDaysToFirstDue() {
		if(this.lenderSpecificSettigs.getMinDaysToFirstDue() == null)
			return this.defaultSettings.getMinDaysToFirstDue();
		return this.lenderSpecificSettigs.getMinDaysToFirstDue();
	}

	public int getMaximumLoanTerm() {
		if(this.lenderSpecificSettigs.getMaximumLoanTerm() == null)
			return this.defaultSettings.getMaximumLoanTerm();
		return this.lenderSpecificSettigs.getMaximumLoanTerm();
	}

	public int getDaysBeforeDueToBill() {
		if(this.lenderSpecificSettigs.getDaysBeforeDueToBill() == null)
			return this.defaultSettings.getDaysBeforeDueToBill();
		return this.lenderSpecificSettigs.getDaysBeforeDueToBill();
	}

	public int getPrepaymentDays() {
		if(this.lenderSpecificSettigs.getPrepaymentDays() == null)
			return this.defaultSettings.getPrepaymentDays();
		return this.lenderSpecificSettigs.getPrepaymentDays();
	}

	public int getDaysLateForFee() {
		if(this.lenderSpecificSettigs.getDaysLateForFee() == null)
			return this.defaultSettings.getDaysLateForFee();
		return this.lenderSpecificSettigs.getDaysLateForFee();
	}
	
	public int getLateFeeAmount(){
		if(this.lenderSpecificSettigs.getLateFeeAmount() == null)
			return this.defaultSettings.getLateFeeAmount();
		return this.lenderSpecificSettigs.getLateFeeAmount();
	}
}

package org.gsoft.openserv.repositories.payment;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanProgramSettings;
import org.gsoft.openserv.domain.payment.LoanPayment;
import org.gsoft.openserv.domain.payment.billing.BillingStatement;
import org.gsoft.openserv.domain.payment.billing.LoanStatementSummary;
import org.gsoft.openserv.repositories.loan.LoanProgramSettingsRepository;
import org.gsoft.openserv.repositories.loan.LoanRepository;
import org.springframework.stereotype.Repository;

@Repository
public class LoanStatementRepository {
	@Resource
	private LoanRepository loanRepository;
	@Resource
	private BillingStatementRepository statementRepository;
	@Resource
	private LoanPaymentRepository loanPaymentRepository;
	@Resource
	private LoanProgramSettingsRepository loanProgramSettingsRepository;
	
	public LoanStatementSummary getLoanStatementSummaryForLoan(Long loanId){
		Loan loan = loanRepository.findOne(loanId);
		return this.getLoanStatementSummaryForLoan(loan);
	}
	
	public LoanStatementSummary getLoanStatementSummaryForLoan(Loan loan){
		List<BillingStatement> billingStatements = statementRepository.findAllBillsForLoan(loan.getLoanID());
		List<LoanPayment> loanPayments = loanPaymentRepository.findAllLoanPayments(loan.getLoanID());
		List<LoanProgramSettings> loanProgramSettings = loanProgramSettingsRepository.findAllLoanProgramSettingsForLoan(loan);
		LoanStatementSummary summary = new LoanStatementSummary(loan, billingStatements, loanPayments, loanProgramSettings);
		return summary;
	}

	public LoanStatementSummary getLoanStatementSummaryForLoanAsOfDate(Long loanId, Date asOfDate){
		Loan loan = loanRepository.findOne(loanId);
		return this.getLoanStatementSummaryForLoanAsOfDate(loan, asOfDate);
	}
	
	public LoanStatementSummary getLoanStatementSummaryForLoanAsOfDate(Loan loan, Date asOfDate){
		List<BillingStatement> billingStatements = statementRepository.findAllBillsForLoanOnOrBefore(loan.getLoanID(), asOfDate);
		List<LoanPayment> loanPayments = loanPaymentRepository.findAllLoanPaymentsEffectiveOnOrBefore(loan.getLoanID(), asOfDate);
		List<LoanProgramSettings> loanProgramSettings = loanProgramSettingsRepository.findAllLoanProgramSettingsForLoan(loan);
		LoanStatementSummary summary = new LoanStatementSummary(loan, billingStatements, loanPayments, loanProgramSettings);
		return summary;
	}
}

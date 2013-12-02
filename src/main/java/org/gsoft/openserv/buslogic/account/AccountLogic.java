package org.gsoft.openserv.buslogic.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.repayment.RepaymentStartDateCalculator;
import org.gsoft.openserv.domain.loan.Account;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.repositories.account.AccountRepository;
import org.springframework.stereotype.Component;

@Component
public class AccountLogic {
	@Resource
	private AccountRepository accountRepository;
	@Resource
	private RepaymentStartDateCalculator repaymentStartDateCalculator;
	
	public Loan addLoanToAccount(Loan loan){
		if(loan.getAccount() == null){
			Date repaymentStartDate = repaymentStartDateCalculator.calculateEarliestRepaymentStartDate(loan);
			List<Account> possibleAccounts = accountRepository.findAccountsByBorrowerAndLoanProgramAndLenderIDAndNotInRepaymentBy(loan.getBorrower().getPersonID(), loan.getLoanProgram().getLoanProgramID(), loan.getLenderID());
			for(Account possibleAccount:possibleAccounts){
				Date accountRepayStart = repaymentStartDateCalculator.calculateRepaymentStartDateForAccount(possibleAccount);
				if(!accountRepayStart.before(repaymentStartDate)){
					possibleAccount.getLoans().add(loan);
					loan.setAccount(possibleAccount);
					break;
				}
			}
			if(loan.getAccount() == null){
				Account newAccount = new Account();
				accountRepository.save(newAccount);
				newAccount.setLenderID(loan.getLenderID());
				newAccount.setLoanProgramID(loan.getLoanProgram().getLoanProgramID());
				newAccount.setBorrowerPersonID(loan.getBorrower().getPersonID());
				ArrayList<Loan> loans = new ArrayList<>();
				loans.add(loan);
				newAccount.setLoans(loans);
				loan.setAccount(newAccount);
				newAccount.setAccountNumber(Long.toString(System.currentTimeMillis(), 36).toUpperCase());
			}
		}
		return loan;
	} 
}

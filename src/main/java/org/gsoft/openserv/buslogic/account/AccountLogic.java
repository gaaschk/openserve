package org.gsoft.openserv.buslogic.account;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.Account;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.repositories.account.AccountRepository;
import org.springframework.stereotype.Component;

@Component
public class AccountLogic {
	@Resource
	private AccountRepository accountRepository;
	
	public Loan addLoanToAccount(Loan loan){
		if(loan.getAccount() == null){
			List<Account> possibleAccounts = accountRepository.findAccountsByBorrowerAndLoanTypeAndLenderID(loan.getBorrower().getPersonID(), loan.getLoanType().getLoanTypeID(), loan.getLenderID());
			for(Account possibleAccount:possibleAccounts){
				if(!possibleAccount.getRepaymentStartDate().before(loan.getEarliestRepaymentStartDate())){
					possibleAccount.getLoans().add(loan);
					loan.setAccount(possibleAccount);
					break;
				}
			}
			if(loan.getAccount() == null){
				Account newAccount = new Account();
				accountRepository.save(newAccount);
				newAccount.setLenderID(loan.getLenderID());
				newAccount.setLoanTypeID(loan.getLoanType().getLoanTypeID());
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

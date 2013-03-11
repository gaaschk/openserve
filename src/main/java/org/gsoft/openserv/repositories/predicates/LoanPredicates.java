package org.gsoft.openserv.repositories.predicates;

import java.util.Date;

import org.gsoft.openserv.domain.loan.LoanProgram;
import org.gsoft.openserv.domain.loan.QLoan;

import com.mysema.query.types.Predicate;

public class LoanPredicates {
	public static Predicate borrowerIdIs(Long borrowerPersonId){
		return QLoan.loan.borrower.personID.eq(borrowerPersonId);
	}
	
	public static Predicate borrowerIdIsAndActiveOnOrBefore(Long borrowerPersonId, Date asOfDate){
		QLoan loan = QLoan.loan;
		return loan.borrower.personID.eq(borrowerPersonId).and(loan.servicingStartDate.loe(asOfDate));
	}
	
	public static Predicate loanProgramIs(LoanProgram loanProgram){
		QLoan loan = QLoan.loan;
		return loan.loanProgram.eq(loanProgram);
	}
	
	public static Predicate accountIDIs(Long accountID){
		QLoan loan = QLoan.loan;
		return loan.account.accountID.eq(accountID);
	}
}

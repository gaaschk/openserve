package org.gsoft.openserv.predicates;

import java.util.Date;

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
}

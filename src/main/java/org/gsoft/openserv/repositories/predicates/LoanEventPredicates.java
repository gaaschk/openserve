package org.gsoft.openserv.repositories.predicates;

import java.util.Date;

import org.gsoft.openserv.domain.loan.QLoanEvent;

import com.mysema.query.types.Predicate;

public class LoanEventPredicates {
	public static Predicate loanIDIsAndEffectiveDateLessThanOrEqualToAndHasTransaction(Long loanID, Date effectiveDate){
		QLoanEvent le = QLoanEvent.loanEvent;
		return le.loanTransaction.isNotNull().and(le.loanID.eq(loanID)).and(le.effectiveDate.loe(effectiveDate));
	}
}

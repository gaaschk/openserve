package org.gsoft.openserv.rulesengine.event;

import org.gsoft.openserv.domain.payment.billing.BillingStatement;

public class BillingStatementCreatedEvent implements SystemEvent {
	private BillingStatement statement;
	
	public BillingStatementCreatedEvent(BillingStatement statement){
		this.statement = statement;
	}
	
	public BillingStatement getStatement(){
		return statement;
	}
}

package org.gsoft.openserv.rulesengine.event;

import org.springframework.stereotype.Component;

/**
 * This class doesn't actually "do" anything.  It's just a 
 * hook for the RulesEngine aop interceptor to pick up events
 * and load them into the RulesEngine context.  For example, 
 * we want to know when a payment is added to the system, but
 * not necessarily every time a payment is loaded.  So, we and 
 * a PaymentAdded event to the RulesEngine so that it can fire
 * appropriate rules.  In this case, it is important that the 
 * payment was "added" in addition to having the payment itself
 * in the context.
 * 
 * @author gaaschk
 */
@Component
public class SystemEventHandler {
	
	public void handleEvent(SystemEvent event){}
}

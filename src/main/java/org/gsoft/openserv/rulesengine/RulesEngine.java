package org.gsoft.openserv.rulesengine;

public interface RulesEngine {

	public abstract boolean isOpen();

	public abstract void close();

	public abstract void open();

	public abstract void evaluateRules();

	public abstract void addContext(final Object ... addedContext);

}
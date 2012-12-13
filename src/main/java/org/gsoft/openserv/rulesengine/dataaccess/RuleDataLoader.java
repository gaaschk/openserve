package org.gsoft.openserv.rulesengine.dataaccess;

import java.util.ArrayList;

public interface RuleDataLoader<T> {

	public Class<T> forType(); 
	
	public ArrayList<Object> loadRuleRelatedData(T contextObject);
}

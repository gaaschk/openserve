package org.gsoft.openserv.rulesengine.dataaccess;

import java.util.List;

public interface RuleDataLoader<T> {

	Class<T> forType(); 
	
	List<Object> loadRuleRelatedData(T contextObject);
}

package org.gsoft.openserv.rulesengine.dataaccess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DataAccessService {
	@Resource
	private ApplicationContext applicationContext;
	private HashMap<Class,RuleDataLoader> dataLoaders = null;
	
	public ArrayList<Object> loadRuleRelatedObjects(Object ... objs){
		ArrayList<Object> additionalObjects = new ArrayList<Object>();
		for(Object obj: objs){
			RuleDataLoader loader = this.getLoaders().get(obj.getClass());
			if(loader != null)
				additionalObjects.addAll(loader.loadRuleRelatedData(obj));
		}
		return additionalObjects;
	}
	
	private HashMap<Class, RuleDataLoader> getLoaders(){
		if(dataLoaders == null){
			dataLoaders = new HashMap<Class, RuleDataLoader>();
			Map<String, RuleDataLoader> beans = applicationContext.getBeansOfType(RuleDataLoader.class);
			Collection<RuleDataLoader> loaders = beans.values();
			for(RuleDataLoader loader: loaders){
				dataLoaders.put(loader.getClass(), loader);
			}
		}
		return dataLoaders;
	}
}


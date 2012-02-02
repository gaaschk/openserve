package org.gsoft.openserv.rulesengine.aop;

import java.lang.reflect.ParameterizedType;
import java.util.Iterator;

import javax.annotation.Resource;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.gsoft.openserv.domain.OpenServDomainObject;
import org.gsoft.openserv.rulesengine.RulesEngine;
import org.gsoft.openserv.rulesengine.annotation.RulesEngineEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RulesEngineAspect {
	@Resource
	private RulesEngine rulesEngine;
	
	@AfterReturning(pointcut="@annotation(org.gsoft.phoenix.rulesengine.annotation.RunRulesEngine)")
	public void runRulesEngine(){
		rulesEngine.evaluateRules();
		rulesEngine.close();
	}
	
	@Before(value="@annotation(org.gsoft.phoenix.rulesengine.annotation.RunRulesEngine)")
	public void openRulesEngine(){
		rulesEngine.open();
	}

	@AfterReturning(pointcut="execution(* org.gsoft.phoenix.repositories.*.*(..))",
			returning="retVal")
	public void addObjectToRulesEngine(OpenServDomainObject retVal){
		if(retVal != null && rulesEngine.isOpen() && retVal.getClass().isAnnotationPresent(RulesEngineEntity.class)){
			rulesEngine.addContext(retVal);
		}
	}

	@AfterReturning(pointcut="execution(* org.gsoft.phoenix.repositories.*.*(..))",
			returning="retVal")
	public void addObjectsToRulesEngine(Iterable<?> retVal){
		if(rulesEngine.isOpen()){
			ParameterizedType[] types = (ParameterizedType[])retVal.getClass().getGenericInterfaces();
			if(types != null && types.length > 0 && types[0].getClass().isAnnotationPresent(RulesEngineEntity.class)){
				Iterator<?> iterator = retVal.iterator();
				while(iterator.hasNext()){
					OpenServDomainObject obj = (OpenServDomainObject)iterator.next();
					rulesEngine.addContext(obj);
				}
			}
		}
	}
}

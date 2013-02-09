package org.gsoft.openserv.rulesengine.aop;

import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.Iterator;

import javax.annotation.Resource;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.gsoft.openserv.domain.PersistentDomainObject;
import org.gsoft.openserv.rulesengine.DroolsRulesEngine;
import org.gsoft.openserv.rulesengine.annotation.RulesEngineEntity;
import org.gsoft.openserv.rulesengine.event.SystemEvent;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RulesEngineAspect {
	@Resource
	private DroolsRulesEngine rulesEngine;
	
	
	
	@AfterReturning(pointcut="@annotation(org.gsoft.openserv.rulesengine.annotation.RunRulesEngine)")
	public void runRulesEngine() throws FileNotFoundException{
		rulesEngine.evaluateRules();
		rulesEngine.close();
	}
	
	@AfterThrowing(pointcut="@annotation(org.gsoft.openserv.rulesengine.annotation.RunRulesEngine)")
	public void closeRulesEngine(){
		rulesEngine.close();
	}
	
	@Before(value="@annotation(org.gsoft.openserv.rulesengine.annotation.RunRulesEngine)")
	public void openRulesEngine(){
		rulesEngine.open();
	}

	@AfterReturning(pointcut="execution(* org.gsoft.openserv.repositories.*.*(..))",
			returning="retVal")
	public void addObjectToRulesEngine(PersistentDomainObject retVal){
		if(retVal != null && rulesEngine.isOpen() && retVal.getClass().isAnnotationPresent(RulesEngineEntity.class)){
			rulesEngine.addContext(retVal);
		}
	}

	@AfterReturning(pointcut="execution(* org.gsoft.openserv.rulesengine.event.SystemEventHandler.*(..)) && args(event)")
	public void addEventToRulesEngine(SystemEvent event){
		if(event != null && rulesEngine.isOpen())
			rulesEngine.addContext(event);
	}
	
	@AfterReturning(pointcut="execution(* org.gsoft.openserv.repositories.*.*(..))",
			returning="retVal")
	public void addObjectsToRulesEngine(Iterable<?> retVal){
		if(rulesEngine.isOpen()){
			if(retVal != null){
				Type[] types = retVal.getClass().getGenericInterfaces();
				if(types != null && types.length > 0 && types[0].getClass().isAnnotationPresent(RulesEngineEntity.class)){
					Iterator<?> iterator = retVal.iterator();
					while(iterator.hasNext()){
						PersistentDomainObject obj = (PersistentDomainObject)iterator.next();
						rulesEngine.addContext(obj);
					}
				}
			}
		}
	}
}

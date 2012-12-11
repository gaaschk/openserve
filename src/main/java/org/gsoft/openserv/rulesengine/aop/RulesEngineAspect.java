package org.gsoft.openserv.rulesengine.aop;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

import javax.annotation.Resource;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;
import org.gsoft.openserv.domain.OpenServDomainObject;
import org.gsoft.openserv.rulesengine.RulesEngine;
import org.gsoft.openserv.rulesengine.annotation.RulesEngineEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@Aspect
@Component
public class RulesEngineAspect {
	@Resource
	private RulesEngine rulesEngine;
	private ArrayList<OpenServDomainObject> knowledge = new ArrayList<OpenServDomainObject>(); 
	
	
	
	@AfterReturning(pointcut="@annotation(org.gsoft.openserv.rulesengine.annotation.RunRulesEngine)")
	public void runRulesEngine() throws FileNotFoundException{
		rulesEngine.evaluateRules();
		rulesEngine.close();

		KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		File ruleDir = ResourceUtils.getFile("classpath:rules");
		for(File ruleFile:ruleDir.listFiles()){
			if(ruleFile.getName().endsWith(".drl"))
				builder.add(ResourceFactory.newFileResource(ruleFile), ResourceType.DRL);
		}
		KnowledgeBase base = KnowledgeBaseFactory.newKnowledgeBase();
		base.addKnowledgePackages(builder.getKnowledgePackages());
		StatelessKnowledgeSession session = base.newStatelessKnowledgeSession();
		session.execute(knowledge);
		knowledge.clear();
	}
	
	@AfterThrowing(pointcut="@annotation(org.gsoft.openserv.rulesengine.annotation.RunRulesEngine)")
	public void closeRulesEngine(){
		rulesEngine.close();
		knowledge.clear();
	}
	
	@Before(value="@annotation(org.gsoft.openserv.rulesengine.annotation.RunRulesEngine)")
	public void openRulesEngine(){
		rulesEngine.open();
	}

	@AfterReturning(pointcut="execution(* org.gsoft.openserv.repositories.*.*(..))",
			returning="retVal")
	public void addObjectToRulesEngine(OpenServDomainObject retVal){
		if(retVal != null && rulesEngine.isOpen() && retVal.getClass().isAnnotationPresent(RulesEngineEntity.class)){
			rulesEngine.addContext(retVal);
			knowledge.add(retVal);
		}
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
						OpenServDomainObject obj = (OpenServDomainObject)iterator.next();
						rulesEngine.addContext(obj);
						knowledge.add(obj);you
					}
				}
			}
		}
	}
}

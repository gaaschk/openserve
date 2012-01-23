package org.gsoft.phoenix.rulesengine;

import javax.annotation.Resource;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.gsoft.phoenix.domain.loan.Loan;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RulesEngineAspect {
	@Resource
	private RulesEngine rulesEngine;
	
	@AfterReturning(
			pointcut="@annotation(org.gsoft.phoenix.rulesengine.RunRulesEngine)",
			returning="retVal"
			)
	public void runRulesEngine(Loan retVal){
		rulesEngine.addContext(retVal);
		rulesEngine.clearContext();
	}
}

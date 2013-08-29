package org.gsoft.openserv.buslogic.repayment.loanphaseevent.spring;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.repayment.loanphaseevent.LoanPhaseEventDateCalculator;
import org.gsoft.openserv.buslogic.repayment.loanphaseevent.LoanPhaseEventDateCalculatorFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class LoanPhaseEventDateCalculatorBeanPostProcessor implements BeanPostProcessor{
	@Resource
	private LoanPhaseEventDateCalculatorFactory factory;
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		//nothing to do here
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		if(bean instanceof LoanPhaseEventDateCalculator){
			factory.addLoanPhaseEventDateCalculator((LoanPhaseEventDateCalculator)bean);
		}
		return bean;
	}
	
}

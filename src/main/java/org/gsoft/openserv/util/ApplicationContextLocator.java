package org.gsoft.openserv.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextLocator implements ApplicationContextAware{
	private static ApplicationContext applicationContext;
	
	public void setApplicationContext(ApplicationContext appContext){
		ApplicationContextLocator.applicationContext = appContext;
	}
	
	public static ApplicationContext getApplicationContext(){
		return ApplicationContextLocator.applicationContext;
	}
}

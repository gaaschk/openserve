package org.gsoft.openserv.rulesengine.event;

import org.gsoft.openserv.domain.loan.DefaultLoanProgramSettings;

public class DefaultLoanProgramSettingsChangedEvent implements SystemEvent {
	private DefaultLoanProgramSettings defaultLoanProgramSettings;
	
	public DefaultLoanProgramSettingsChangedEvent(DefaultLoanProgramSettings defaultLoanProgramSettings){
		this.defaultLoanProgramSettings = defaultLoanProgramSettings; 
	}
	
	public DefaultLoanProgramSettings getDefaultLoanProgramSettings(){
		return defaultLoanProgramSettings;
	}
}

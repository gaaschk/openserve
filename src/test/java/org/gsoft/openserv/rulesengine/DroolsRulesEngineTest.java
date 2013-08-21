package org.gsoft.openserv.rulesengine;

import static org.mockito.Mockito.when;

import org.gsoft.openserv.buslogic.system.SystemSettingsLogic;
import org.gsoft.openserv.domain.system.SystemSettings;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

public class DroolsRulesEngineTest {

	@Test
	public void testOpen() {
		SystemSettings sysSettings = new SystemSettings();
		
		SystemSettingsLogic sysSettingsLogic = Mockito.mock(SystemSettingsLogic.class);
		when(sysSettingsLogic.getCurrentSystemSettings()).thenReturn(sysSettings);
		
		ApplicationContext appContext = Mockito.mock(ApplicationContext.class);
		when(appContext.getBean(SystemSettingsLogic.class)).thenReturn(sysSettingsLogic);
		
		
		DroolsRulesEngine engine = new DroolsRulesEngine();
		ReflectionTestUtils.setField(engine, "springContext", appContext);
		ReflectionTestUtils.setField(engine, "systemSettingsLogic", sysSettingsLogic);

		engine.open();
		engine.addContext("no op");
		engine.setModified();
		engine.evaluateRules();
	}

}

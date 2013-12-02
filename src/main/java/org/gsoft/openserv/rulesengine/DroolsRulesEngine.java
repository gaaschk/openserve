package org.gsoft.openserv.rulesengine;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;
import org.gsoft.openserv.buslogic.system.SystemSettingsLogic;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@Component
public class DroolsRulesEngine implements RulesEngine{
	private boolean isOpen = false;
	private boolean knowledgeModified = false;
	private KnowledgeBase base = null;
	private ArrayList<Object> knowledge = new ArrayList<Object>();
	@Inject
	private ApplicationContext springContext;
	@Inject
	private SystemSettingsLogic systemSettingsLogic;
	
	private KnowledgeBase getKnowledgeBase(){
		if(base == null){
			KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
			List<URL> packages = new ArrayList<>();
			List<URL> drls = new ArrayList<>();
			try {
				List<String> packageNames = IOUtils.readLines(ResourceUtils.getURL("classpath:rules/packages/index.list").openStream());
				for(String packageName:packageNames){
					packages.add(ResourceUtils.getURL("classpath:rules/packages/"+packageName));
				}
				List<String> drlNames = IOUtils.readLines(ResourceUtils.getURL("classpath:rules/drl/index.list").openStream());
				for(String drlName:drlNames){
					packages.add(ResourceUtils.getURL("classpath:rules/drl/"+drlName));
				}
				for(URL packageFile:packages){
					builder.add(ResourceFactory.newInputStreamResource(packageFile.openStream()), ResourceType.DRL);
				}
				for(URL drlFile:drls){
					builder.add(ResourceFactory.newInputStreamResource(drlFile.openStream()), ResourceType.DRL);
				}
			} catch (IOException e) {
				throw new RulesEngineException("Unable to load DRL files.", e);
			}
			if(builder.hasErrors()){
				StringBuilder errorMessage = new StringBuilder("Errors parsing DRLs.  KnowledgeBuilder errors: ");
				String comma = "";
				for(KnowledgeBuilderError error : builder.getErrors()){
					errorMessage.append(comma);
					errorMessage.append(error.getMessage());
					comma = ",";
				}
				throw new RulesEngineException(errorMessage.toString());
			}
			base = KnowledgeBaseFactory.newKnowledgeBase();
			base.addKnowledgePackages(builder.getKnowledgePackages());
		}
		return base;
	}
	
	public boolean isOpen() {
		return this.isOpen;
	}

	public void close() {
		isOpen = false;
		knowledge.clear();
	}

	public void open() {
		isOpen = true;
	}
	
	public void setModified(){
		this.knowledgeModified = true;
	}

	public void evaluateRules() {
		if(!this.isOpen)
			throw new RulesEngineException("RulesEngine must be open before it can be run.");
		while(knowledge.size()>0 && knowledgeModified){
			knowledgeModified = false;
			ArrayList<Object> workingKnowledge = new ArrayList<>();
			workingKnowledge.addAll(knowledge);
			knowledge.clear();
			HashMap<String,Date> sysDate = new HashMap<String, Date>();
			sysDate.put("systemDate", systemSettingsLogic.getCurrentSystemDate());
			workingKnowledge.add(sysDate);
			StatelessKnowledgeSession session = this.getKnowledgeBase().newStatelessKnowledgeSession();
			session.setGlobal("springContext", springContext);
			session.execute(workingKnowledge);
		}
	}

	public void addContext(final Object... addedContext) {
		if(!this.isOpen)
			throw new RulesEngineException("RulesEngine must be open before objects can be added to the context.");
		knowledge.addAll(Arrays.asList(addedContext));
	}
}

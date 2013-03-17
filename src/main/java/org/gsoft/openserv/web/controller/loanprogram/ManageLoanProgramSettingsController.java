package org.gsoft.openserv.web.controller.loanprogram;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.LoanProgram;
import org.gsoft.openserv.domain.loan.DefaultLoanProgramSettings;
import org.gsoft.openserv.repositories.loan.DefaultLoanProgramSettingsRepository;
import org.gsoft.openserv.repositories.loan.LoanProgramRepository;
import org.gsoft.openserv.repositories.rates.RateRepository;
import org.gsoft.openserv.service.loanprogram.LoanProgramSettingsService;
import org.gsoft.openserv.web.models.DefaultLoanProgramSettingsListModel;
import org.gsoft.openserv.web.models.DefaultLoanProgramSettingsModel;
import org.springframework.stereotype.Service;

@Service
public class ManageLoanProgramSettingsController {
	@Resource
	private LoanProgramRepository loanProgramRepository;
	@Resource
	private DefaultLoanProgramSettingsRepository defaultLoanProgramSettingsRepository;
	@Resource
	private RateRepository rateRepostory;
	@Resource
	private LoanProgramSettingsService loanProgramSettingsService;

	public DefaultLoanProgramSettingsListModel loadDefaultLoanProgramSettingsListModel(){
		DefaultLoanProgramSettingsListModel model = new DefaultLoanProgramSettingsListModel();
		List<LoanProgram> loanPrograms = loanProgramRepository.findAll();
		for(LoanProgram loanProgram:loanPrograms){
			model.addDefaultLoanProgramSettingsList(loanProgram, defaultLoanProgramSettingsRepository.findAllDefaultLoanProgramSettingsByLoanProgram(loanProgram));
		}
		if(model.getDefaultLoanProgramSettingsModels().size()>0){
			model.getDefaultLoanProgramSettingsModels().get(0).setSelected(true);
		}
		model.setAllRates(rateRepostory.findAll());
		return model;
	}
	
	public void addDefaultLoanProgramSettingsListModel(DefaultLoanProgramSettingsListModel model){
		LoanProgram newType = new LoanProgram();
		long newId = model.getDefaultLoanProgramSettingsModels().size();
		newType.setLoanProgramID(newId*-1);
		model.addDefaultLoanProgramSettingsList(newType,new ArrayList<DefaultLoanProgramSettings>());
	}

	public void addLoanTypeProfile(DefaultLoanProgramSettingsListModel model){
		for(DefaultLoanProgramSettingsModel profModel:model.getDefaultLoanProgramSettingsModels()){
			if(profModel.isSelected()){
				DefaultLoanProgramSettings prof = new DefaultLoanProgramSettings();
				prof.setLoanProgram(profModel.getLoanType());
				profModel.getLoanTypeProfiles().add(prof);
			}
		}
	}
	
	public void save(DefaultLoanProgramSettingsListModel model){
		for(DefaultLoanProgramSettingsModel profModel:model.getDefaultLoanProgramSettingsModels()){
			LoanProgram loanType = profModel.getLoanType();
			if(loanType.getLoanProgramID()<=0){
				loanType.setLoanProgramID(null);
			}
			loanType = this.loanProgramSettingsService.saveLoanProgram(loanType);
			for(DefaultLoanProgramSettings loanTypeProfile:profModel.getLoanTypeProfiles()){
				loanTypeProfile.setLoanProgram(loanType);
				this.loanProgramSettingsService.saveDefaultLoanProgramSettings(loanTypeProfile);
			}
		}
	}
}

package org.gsoft.openserv.web.loanprogram.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.DefaultLoanProgramSettings;
import org.gsoft.openserv.domain.loan.LoanProgram;
import org.gsoft.openserv.repositories.loan.DefaultLoanProgramSettingsRepository;
import org.gsoft.openserv.repositories.loan.LoanProgramRepository;
import org.gsoft.openserv.repositories.rates.RateRepository;
import org.gsoft.openserv.service.loanprogram.LoanProgramSettingsService;
import org.gsoft.openserv.web.loanprogram.model.DefaultLoanProgramSettingsModel;
import org.gsoft.openserv.web.loanprogram.model.LoanProgramModel;
import org.gsoft.openserv.web.loanprogram.model.ManageLoanProgramsModel;
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

	public ManageLoanProgramsModel loadManageLoanProgramsModel(){
		ManageLoanProgramsModel model = new ManageLoanProgramsModel();
		List<LoanProgram> loanPrograms = loanProgramRepository.findAll();
		List<LoanProgramModel> loanProgramModels = new ArrayList<>();
		model.setAllLoanProgramModels(loanProgramModels);
		for(LoanProgram loanProgram:loanPrograms){
			LoanProgramModel loanProgramModel = new LoanProgramModel();
			loanProgramModels.add(loanProgramModel);
			loanProgramModel.setLoanProgram(loanProgram);
			List<DefaultLoanProgramSettings> allSettingsForLoanProgram = defaultLoanProgramSettingsRepository.findAllDefaultLoanProgramSettingsByLoanProgram(loanProgram);
			List<DefaultLoanProgramSettingsModel> loanProgramSettingsModels = new ArrayList<>();
			for(DefaultLoanProgramSettings settings:allSettingsForLoanProgram){
				DefaultLoanProgramSettingsModel settingsModel = new DefaultLoanProgramSettingsModel();
				settingsModel.setDefaultLoanProgramSettings(settings);
				loanProgramSettingsModels.add(settingsModel);
			}
			loanProgramModel.setAllDefaultLoanProgramSettingsModels(loanProgramSettingsModels);
		}
		if(model.getAllLoanProgramModels().size()>0){
			model.getAllLoanProgramModels().get(0).setSelected(true);
			model.setDisplayedLoanProgram(model.getAllLoanProgramModels().get(0));
			if(model.getDisplayedLoanProgram().getAllDefaultLoanProgramSettingsModels().size()>0){
				model.getAllLoanProgramModels().get(0).setSelected(true);
				model.setDisplayedLoanProgram(model.getAllLoanProgramModels().get(0));
			}
		}
		model.setAllRates(rateRepostory.findAll());
		return model;
	}
	
	public void addLoanProgram(ManageLoanProgramsModel model){
		LoanProgram newType = new LoanProgram();
		LoanProgramModel lpModel = new LoanProgramModel();
		lpModel.setAllDefaultLoanProgramSettingsModels(new ArrayList<DefaultLoanProgramSettingsModel>());
		lpModel.setLoanProgram(newType);
		model.getAllLoanProgramModels().add(lpModel);
	}

	public void addLoanProgramSettings(ManageLoanProgramsModel model){
		DefaultLoanProgramSettings prof = new DefaultLoanProgramSettings();
		prof.setLoanProgram(model.getDisplayedLoanProgram().getLoanProgram());
		DefaultLoanProgramSettingsModel dlpsModel = new DefaultLoanProgramSettingsModel();
		dlpsModel.setDefaultLoanProgramSettings(prof);
		model.getDisplayedLoanProgram().getAllDefaultLoanProgramSettingsModels().add(dlpsModel);
	}
	
	public void save(ManageLoanProgramsModel model){
		for(LoanProgramModel progModel:model.getAllLoanProgramModels()){
			LoanProgram loanType = progModel.getLoanProgram();
			loanType = this.loanProgramSettingsService.saveLoanProgram(loanType);
			for(DefaultLoanProgramSettingsModel loanProgSettingsModel:progModel.getAllDefaultLoanProgramSettingsModels()){
				this.loanProgramSettingsService.saveDefaultLoanProgramSettings(loanProgSettingsModel.getDefaultLoanProgramSettings());
			}
		}
	}
}

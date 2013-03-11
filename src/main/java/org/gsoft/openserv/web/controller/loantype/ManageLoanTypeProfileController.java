package org.gsoft.openserv.web.controller.loantype;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.LoanProgram;
import org.gsoft.openserv.domain.loan.DefaultLoanProgramSettings;
import org.gsoft.openserv.repositories.loan.DefaultLoanProgramSettingsRepository;
import org.gsoft.openserv.repositories.loan.LoanTypeRepository;
import org.gsoft.openserv.repositories.rates.RateRepository;
import org.gsoft.openserv.service.loantype.LoanTypeProfileService;
import org.gsoft.openserv.web.models.LoanTypeProfileModel;
import org.gsoft.openserv.web.models.LoanTypeProfilesModel;
import org.springframework.stereotype.Service;

@Service
public class ManageLoanTypeProfileController {
	@Resource
	private LoanTypeRepository loanTypeRepository;
	@Resource
	private DefaultLoanProgramSettingsRepository loanTypeProfileRepository;
	@Resource
	private RateRepository rateRepostory;
	@Resource
	private LoanTypeProfileService loanTypeProfileService;

	public LoanTypeProfileModel loadLoanTypeProfileModel(){
		LoanTypeProfileModel model = new LoanTypeProfileModel();
		List<LoanProgram> loanTypes = loanTypeRepository.findAll();
		for(LoanProgram loanType:loanTypes){
			model.addLoanTypeProfiles(loanType, loanTypeProfileRepository.findAllDefaultLoanProgramSettingsByLoanProgram(loanType));
		}
		if(model.getLoanTypeProfilesModels().size()>0){
			model.getLoanTypeProfilesModels().get(0).setSelected(true);
		}
		model.setAllRates(rateRepostory.findAll());
		return model;
	}
	
	public void addLoanType(LoanTypeProfileModel model){
		LoanProgram newType = new LoanProgram();
		long newId = model.getLoanTypeProfilesModels().size();
		newType.setLoanProgramID(newId*-1);
		model.addLoanTypeProfiles(newType,new ArrayList<DefaultLoanProgramSettings>());
	}

	public void addLoanTypeProfile(LoanTypeProfileModel model){
		for(LoanTypeProfilesModel profModel:model.getLoanTypeProfilesModels()){
			if(profModel.isSelected()){
				DefaultLoanProgramSettings prof = new DefaultLoanProgramSettings();
				prof.setLoanProgram(profModel.getLoanType());
				profModel.getLoanTypeProfiles().add(prof);
			}
		}
	}
	
	public void save(LoanTypeProfileModel model){
		for(LoanTypeProfilesModel profModel:model.getLoanTypeProfilesModels()){
			LoanProgram loanType = profModel.getLoanType();
			if(loanType.getLoanProgramID()<=0){
				loanType.setLoanProgramID(null);
			}
			loanType = this.loanTypeProfileService.saveLoanType(loanType);
			for(DefaultLoanProgramSettings loanTypeProfile:profModel.getLoanTypeProfiles()){
				loanTypeProfile.setLoanProgram(loanType);
				this.loanTypeProfileService.saveLoanTypeProfile(loanTypeProfile);
			}
		}
	}
}

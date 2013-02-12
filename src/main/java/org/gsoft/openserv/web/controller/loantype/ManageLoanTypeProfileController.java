package org.gsoft.openserv.web.controller.loantype;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.LoanType;
import org.gsoft.openserv.domain.loan.LoanTypeProfile;
import org.gsoft.openserv.repositories.loan.LoanTypeProfileRepository;
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
	private LoanTypeProfileRepository loanTypeProfileRepository;
	@Resource
	private RateRepository rateRepostory;
	@Resource
	private LoanTypeProfileService loanTypeProfileService;

	public LoanTypeProfileModel loadLoanTypeProfileModel(){
		LoanTypeProfileModel model = new LoanTypeProfileModel();
		List<LoanType> loanTypes = loanTypeRepository.findAll();
		for(LoanType loanType:loanTypes){
			model.addLoanTypeProfiles(loanType, loanTypeProfileRepository.findLoanTypeProfilesByLoanType(loanType));
		}
		if(model.getLoanTypeProfilesModels().size()>0){
			model.getLoanTypeProfilesModels().get(0).setSelected(true);
		}
		model.setAllRates(rateRepostory.findAll());
		return model;
	}
	
	public void addLoanType(LoanTypeProfileModel model){
		LoanType newType = new LoanType();
		long newId = model.getLoanTypeProfilesModels().size();
		newType.setLoanTypeID(newId*-1);
		model.addLoanTypeProfiles(newType,new ArrayList<LoanTypeProfile>());
	}

	public void addLoanTypeProfile(LoanTypeProfileModel model){
		for(LoanTypeProfilesModel profModel:model.getLoanTypeProfilesModels()){
			if(profModel.isSelected()){
				LoanTypeProfile prof = new LoanTypeProfile();
				prof.setLoanType(profModel.getLoanType());
				profModel.getLoanTypeProfiles().add(prof);
			}
		}
	}
	
	public void save(LoanTypeProfileModel model){
		for(LoanTypeProfilesModel profModel:model.getLoanTypeProfilesModels()){
			LoanType loanType = profModel.getLoanType();
			if(loanType.getLoanTypeID()<=0){
				loanType.setLoanTypeID(null);
			}
			loanType = this.loanTypeProfileService.saveLoanType(loanType);
			for(LoanTypeProfile loanTypeProfile:profModel.getLoanTypeProfiles()){
				loanTypeProfile.setLoanType(loanType);
				this.loanTypeProfileService.saveLoanTypeProfile(loanTypeProfile);
			}
		}
	}
}

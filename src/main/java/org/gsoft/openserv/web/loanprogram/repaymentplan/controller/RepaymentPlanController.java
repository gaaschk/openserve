package org.gsoft.openserv.web.loanprogram.repaymentplan.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.LoanPhaseEvent;
import org.gsoft.openserv.domain.repayment.RepaymentPlan;
import org.gsoft.openserv.repositories.repayment.RepaymentPlanRepository;
import org.gsoft.openserv.service.repaymentplan.RepaymentPlanSettingsService;
import org.gsoft.openserv.web.loanprogram.repaymentplan.model.RepaymentPlanModel;
import org.gsoft.openserv.web.loanprogram.repaymentplan.model.RepaymentPlanType;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Controller
@RequestMapping("loanprogram/repaymentplans")
@Transactional(readOnly=true)
public class RepaymentPlanController {
	@Resource
	private RepaymentPlanRepository repaymentPlanRepository;
	@Resource
	private ConversionService conversionService;
	@Resource
	private ObjectMapper objectMapper;
	@Resource
	private RepaymentPlanSettingsService repaymentPlanService;
	
	
	@RequestMapping(method={RequestMethod.GET})
	public ModelAndView findAllRepaymentPlans(@RequestParam("loansettingsid") String loansettingsid){
		Long settingsId = new Long(loansettingsid);
		List<RepaymentPlan> repaymentPlans = repaymentPlanRepository.findAllRepaymentPlansForDefaultLoanSettings(settingsId);
		List<RepaymentPlanModel> repaymentPlanModelList = new ArrayList<>();
		for(RepaymentPlan settings:repaymentPlans){
			repaymentPlanModelList.add(conversionService.convert(settings, RepaymentPlanModel.class));
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setView(new MappingJackson2JsonView());
		modelAndView.addObject(repaymentPlanModelList);
		return modelAndView;
	}
	
	@RequestMapping(value="/repaymentplantype", method={RequestMethod.GET})
	public ModelAndView findAllRepaymentPlanTypes(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setView(new MappingJackson2JsonView());
		modelAndView.addObject(RepaymentPlanType.values());
		return modelAndView;
	}

	@Transactional
	@RequestMapping(method={RequestMethod.POST,RequestMethod.PUT})
	public ModelAndView save(@RequestBody String model) throws JsonParseException, JsonMappingException, IOException{
		List<RepaymentPlanModel> repaymentPlanModelList = objectMapper.readValue(model, new TypeReference<List<RepaymentPlanModel>>(){});
		List<RepaymentPlan> savedPlans = new ArrayList<>();
		for(RepaymentPlanModel settingsModel:repaymentPlanModelList){
			RepaymentPlan settings = conversionService.convert(settingsModel, RepaymentPlan.class);
			savedPlans.add(settings);
		}
		savedPlans = repaymentPlanService.saveRepaymentPlanSettings(savedPlans);
		List<RepaymentPlanModel> returnModels = new ArrayList<>();
		for(RepaymentPlan settings:savedPlans){
			returnModels.add(conversionService.convert(settings, RepaymentPlanModel.class));
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setView(new MappingJackson2JsonView());
		modelAndView.addObject(returnModels);
		return modelAndView;
	}
	
	@RequestMapping(value="/loanphaseevent", method={RequestMethod.GET})
	public ModelAndView findAllLoanPhaseEvents(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setView(new MappingJackson2JsonView());
		modelAndView.addObject(LoanPhaseEvent.values());
		return modelAndView;
	}
}

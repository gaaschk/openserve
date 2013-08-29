package org.gsoft.openserv.web.loanprogram.repaymentplan.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.gsoft.openserv.domain.repayment.RepaymentPlanSettings;
import org.gsoft.openserv.repositories.repayment.RepaymentPlanSettingsRepository;
import org.gsoft.openserv.web.loanprogram.repaymentplan.model.RepaymentPlanSettingsModel;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Controller
@RequestMapping("loanprogram/repaymentplans")
public class RepaymentPlansController {
	private static final Logger LOG = LogManager.getLogger(RepaymentPlansController.class);

	@Resource
	private RepaymentPlanSettingsRepository repaymentPlanSettingsRepository;
	@Resource
	private ConversionService conversionService;
	@Resource
	private ObjectMapper objectMapper;
	
	@RequestMapping(method={RequestMethod.GET})
	public ModelAndView findAllRepaymentPlans(){
		List<RepaymentPlanSettings> repaymentPlans = repaymentPlanSettingsRepository.findAll();
		List<RepaymentPlanSettingsModel> repaymentPlanModelList = new ArrayList<>();
		for(RepaymentPlanSettings settings:repaymentPlans){
			repaymentPlanModelList.add(conversionService.convert(settings, RepaymentPlanSettingsModel.class));
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setView(new MappingJackson2JsonView());
		modelAndView.addObject(repaymentPlanModelList);
		return modelAndView;
	}

	@RequestMapping(method={RequestMethod.POST,RequestMethod.PUT})
	public ModelAndView save(@RequestBody String model) throws JsonParseException, JsonMappingException, IOException{
		List<RepaymentPlanSettingsModel> repaymentPlanModelList = objectMapper.readValue(model, new TypeReference<List<RepaymentPlanSettingsModel>>(){});
		for(RepaymentPlanSettingsModel settingsModel:repaymentPlanModelList){
			RepaymentPlanSettings settings = conversionService.convert(settingsModel, RepaymentPlanSettings.class);
			repaymentPlanSettingsRepository.save(settings);
			LOG.debug("Saved Loan Program");
		}
		return this.findAllRepaymentPlans();
	}
}

package org.gsoft.openserv.web.loanprogram.duediligence.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.gsoft.openserv.domain.duediligence.DueDiligenceEventType;
import org.gsoft.openserv.domain.duediligence.DueDiligenceSchedule;
import org.gsoft.openserv.repositories.duediligence.DueDiligenceEventTypeRepository;
import org.gsoft.openserv.repositories.duediligence.DueDiligenceScheduleRepository;
import org.gsoft.openserv.web.loanprogram.duediligence.model.DueDiligenceEventTypeModel;
import org.gsoft.openserv.web.loanprogram.duediligence.model.DueDiligenceScheduleModel;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
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
@RequestMapping("duediligence")
public class DueDiligenceController {
	private static final Logger LOG = LogManager.getLogger(DueDiligenceController.class);

	@Resource
	private ConversionService conversionService;
	@Resource
	private DueDiligenceScheduleRepository dueDiligenceScheduleRepository;
	@Resource
	private DueDiligenceEventTypeRepository dueDiligenceEventTypeRepository;
	@Resource
	private ObjectMapper objectMapper;
	
	@RequestMapping(value="/duediligenceschedules", method={RequestMethod.GET})
	public ModelAndView getAllDueDiligenceSchedulesForLoanProgram(@RequestParam("loanprogramid") String loanProgramID){
		List<DueDiligenceSchedule> dueDiligenceSchedules = dueDiligenceScheduleRepository.findAllByLoanProgramId(Long.valueOf(loanProgramID));
		List<DueDiligenceScheduleModel> dueDiligenceScheduleList = new ArrayList<>();
		for(DueDiligenceSchedule schedule:dueDiligenceSchedules){
			dueDiligenceScheduleList.add(conversionService.convert(schedule, DueDiligenceScheduleModel.class));
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setView(new MappingJackson2JsonView());
		modelAndView.addObject(dueDiligenceScheduleList);
		return modelAndView;
	}
	
	@RequestMapping(value="/duediligenceeventtypes", method={RequestMethod.GET})
	public ModelAndView getAllDueDiligenceEventTypes(){
		List<DueDiligenceEventType> dueDiligenceEventList = dueDiligenceEventTypeRepository.findAll();
		List<DueDiligenceEventTypeModel> dueDiligenceEventTypes = new ArrayList<>();
		for(DueDiligenceEventType type:dueDiligenceEventList){
			dueDiligenceEventTypes.add(conversionService.convert(type, DueDiligenceEventTypeModel.class));
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setView(new MappingJackson2JsonView());
		modelAndView.addObject(dueDiligenceEventTypes);
		return modelAndView;
	}
	
	@RequestMapping(value="/duediligenceschedules", method={RequestMethod.POST,RequestMethod.PUT})
	public ModelAndView saveSchedules(@RequestBody String model) throws JsonParseException, JsonMappingException, IOException{
		List<DueDiligenceScheduleModel> loanProgramSettingsList = objectMapper.readValue(model, new TypeReference<List<DueDiligenceScheduleModel>>(){});
		List<DueDiligenceScheduleModel> updatedSchedules = new ArrayList<>();
		for(DueDiligenceScheduleModel scheduleModel:loanProgramSettingsList){
			DueDiligenceSchedule schedule = dueDiligenceScheduleRepository.save(conversionService.convert(scheduleModel, DueDiligenceSchedule.class));
			updatedSchedules.add(conversionService.convert(schedule, DueDiligenceScheduleModel.class));
			LOG.debug("Saved Due Diligence Schedule");
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setView(new MappingJackson2JsonView());
		modelAndView.addObject(updatedSchedules);
		return modelAndView;
	}
}

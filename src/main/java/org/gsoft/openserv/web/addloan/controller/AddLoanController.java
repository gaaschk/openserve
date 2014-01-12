package org.gsoft.openserv.web.addloan.controller;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.service.loanentry.LoanEntryService;
import org.gsoft.openserv.web.addloan.model.LoanEntryModel;
import org.springframework.core.convert.ConversionService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value="newloan", method={RequestMethod.POST})
@Transactional(readOnly=true)
public class AddLoanController {
	private static final Logger LOG = LogManager.getLogger(AddLoanController.class);
	
	@Resource(name="customConversionService")
	private ConversionService conversionService;
	@Resource
	private LoanEntryService loanEntryService;
	@Resource
	private ObjectMapper objectMapper;
	
	@RequestMapping(method=RequestMethod.POST)
	@Transactional
	public @ResponseBody Loan saveLoan(@RequestBody final String requestBody) throws JsonParseException, JsonMappingException, IOException{
		LoanEntryModel loanModel = objectMapper.readValue(requestBody, LoanEntryModel.class);
		Loan newLoan = conversionService.convert(loanModel, Loan.class);
		LOG.debug(loanModel);
		newLoan = loanEntryService.addNewLoan(newLoan);
		return newLoan;
	}
}

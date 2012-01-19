package org.gsoft.phoenix.service.payment;

import java.util.Calendar;

import javax.annotation.Resource;

import org.gsoft.phoenix.data.DatabaseUtility;
import org.gsoft.phoenix.domain.Person;
import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.service.PersonService;
import org.gsoft.phoenix.service.SystemSettingsService;
import org.gsoft.phoenix.service.loanentry.LoanEntryService;
import org.gsoft.phoenix.util.LoanFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/application-context.xml")
public class TestPaymentServiceIT {
	@Resource
	private DatabaseUtility dbutility;
	@Resource
	private PaymentService paymentService;
	@Resource
	private LoanEntryService loanEntryService;
	@Resource
	private PersonService personService;
	@Resource
	private LoanFactory loanFactory;
	@Resource
	private SystemSettingsService systemSettingsService;
	
	@Before
	public void setUp() throws Exception {
		dbutility.refreshDatabase();
	}

	@Test
	public void test() {
		Loan newLoan = loanFactory.getLoanByID(1);
		loanEntryService.addNewLoan(newLoan);
		Person person = personService.findPersonBySSN(newLoan.getBorrower().getSsn());
		systemSettingsService.adjustSystemDateByCalendarUnit(Calendar.MONTH, 1);
		paymentService.applyPayment(person.getPersonID(), 100000, systemSettingsService.getCurrentSystemDate());
		systemSettingsService.adjustSystemDateByCalendarUnit(Calendar.MONTH, 1);
		paymentService.applyPayment(person.getPersonID(), 100000, systemSettingsService.getCurrentSystemDate());
		systemSettingsService.adjustSystemDateByCalendarUnit(Calendar.MONTH, 1);
		paymentService.applyPayment(person.getPersonID(), 100000, systemSettingsService.getCurrentSystemDate());
		systemSettingsService.adjustSystemDateByCalendarUnit(Calendar.MONTH, 1);
		paymentService.applyPayment(person.getPersonID(), 100000, systemSettingsService.getCurrentSystemDate());
	}

}

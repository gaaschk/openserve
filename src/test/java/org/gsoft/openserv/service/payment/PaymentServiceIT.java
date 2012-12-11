package org.gsoft.openserv.service.payment;

import java.util.Calendar;

import javax.annotation.Resource;

import org.gsoft.openserv.Authenticator;
import org.gsoft.openserv.data.DatabaseUtility;
import org.gsoft.openserv.domain.Person;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.service.PersonService;
import org.gsoft.openserv.service.SystemSettingsService;
import org.gsoft.openserv.service.loanentry.LoanEntryService;
import org.gsoft.openserv.service.payment.PaymentService;
import org.gsoft.openserv.util.LoanFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/application-context.xml")
public class PaymentServiceIT{
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
	@Resource
	private Authenticator authenticator;
	
	@Before
	public void setUp() throws Exception {
		dbutility.refreshDatabase();
	}

	@Test
	public void test() {
		authenticator.authenticate();
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

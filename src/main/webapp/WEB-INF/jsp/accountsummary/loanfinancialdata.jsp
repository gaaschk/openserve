<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<form:form commandName="loandetailmodel.loanFinancialData">
	<fieldset>
		<label>Loan Type:</label>
		<form:input disabled="true" path="loanType"/>
	</fieldset>
	<fieldset>
		<label class="nobr">Current Principal:</label>
		<form:input disabled="true" path="currentPrincipal"/>
	</fieldset>
	<fieldset>
		<label>Current Interest:</label>
		<form:input disabled="true" path="currentInterest"/>
	</fieldset>
	<fieldset>
		<label>Current Fees:</label>
		<form:input disabled="true" path="currentFees"/>
	</fieldset>
</form:form>

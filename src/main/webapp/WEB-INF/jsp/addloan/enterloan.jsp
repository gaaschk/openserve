<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<h1>Enter Loan Information</h1>
<form:form modelAttribute="loanModel">
	<h3>Person Data</h3>
	<fieldset>
		<label>SSN:</label>
		<c:out value="${loanModel.person.ssn}"/>
	</fieldset>
	<fieldset>
		<label>First Name:</label>
		<form:input path="person.firstName"/>
	</fieldset>
	<fieldset>
		<label>Last Name:</label>
		<form:input path="person.lastName"/>
	</fieldset>
	<h3>Loan Data</h3>
	<fieldset>
		<label>Starting Principal:</label>
		<form:input path="startingPrincipal" type="text" data-meiomask-options="{mask: '999-99-9999', autoTab: true, removeIfInvalid: true}" data-meiomask="fixed"/>
	</fieldset>
	<fieldset>
		<label>Starting Interest:</label>
		<form:input path="startingInterest" data-meiomask="regexp.currency" />
	</fieldset>
	<fieldset>
		<label>Starting Fees:</label>
		<form:input path="startingFees" data-meiomask="regexp.currency" />
	</fieldset>
	<input type="submit" value="Save Loan"></input>
</form:form>

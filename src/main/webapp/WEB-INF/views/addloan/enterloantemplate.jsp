<%@ include file="/WEB-INF/views/includes.jsp" %>
<h1>Enter Loan Information</h1>
<form:form modelAttribute="loanModel">
	<h3>Person Data</h3>
	<fieldset>
		<label>SSN:</label>
		<c:out value="${loanModel.person.ssn}"/>
	</fieldset>
	<fieldset>
		<label>First Name:</label>
		<form:input path="person.firstName" type="text"/>
	</fieldset>
	<fieldset>
		<label>Last Name:</label>
		<form:input path="person.lastName" type="text"/>
	</fieldset>
	<h3>Loan Data</h3>
	<tiles:insertAttribute name="detail"/>
</form:form>

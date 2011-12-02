<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<form:form commandName="accountmodel">
<table>
	<tr>
		<td colspan="2">
			<fieldset>
				<label>SSN:</label>
				<c:out value="${accountmodel.borrower.ssn}"/>
			</fieldset>
		</td>
	</tr>
	<tr>
		<td>
			<fieldset>
				<label>First Name:</label>
				<c:out value="${accountmodel.borrower.firstName}"/>
			</fieldset>
		</td>
		<td>
			<fieldset>
				<label>Last Name:</label>
				<c:out value="${accountmodel.borrower.lastName}"/>
			</fieldset>
		</td>
	</tr>
</table>
<table border="1">
	<caption align="top"><h3 align="left">Loans</h3></caption>
	<tr>
		<th><label>Current Principal</label></th>
		<th><label>Current Interest</label></th>
		<th><label>Current Fees</label></th>
	</tr>
	<c:forEach var="loan" items="${accountmodel.loans}" varStatus="index">
		<tr>
			<td><form:input disabled="true" path="loans[${index.count-1}].currentPrincipal"/></td>
			<td><form:input disabled="true" path="loans[${index.count-1}].currentInterest"/></td>
			<td><form:input disabled="true" path="loans[${index.count-1}].currentFees"/></td>
		</tr>
	</c:forEach>
</table>
</form:form>
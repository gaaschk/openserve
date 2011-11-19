<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<h1>Enter Loan Information</h1>
<form:form modelAttribute="loanModel">
	<table>
		<tr>
			<td colspan="2">Person Data</td>
		</tr>
		<tr>
			<td>SSN:</td>
			<td><c:out value="${loanModel.person.ssn}"/></td> 
		</tr>
		<tr>
			<td>First Name:</td>
			<td><form:input path="person.firstName"/>
		</tr>
		<tr>
			<td>Last Name:</td>
			<td><form:input path="person.lastName"/>
		</tr>
		<tr>
			<td colspan="2">Loan Data</td>
		</tr>
		<tr>
			<td>Starting Principal:</td>
			<td><form:input path="startingPrincipal"/>
		</tr>
		<tr>
			<td>Starting Interest:</td>
			<td><form:input path="startingInterest"/>
		</tr>
		<tr>
			<td>Starting Fees:</td>
			<td><form:input path="startingFees"/>
		</tr>
		<tr>
			<td colspan="2"><input type="submit" align="center" value="Save Loan"></input></td>
		</tr>
	</table>
</form:form>

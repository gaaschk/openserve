<%@ include file="/WEB-INF/views/includes.jsp" %>
<h1>Loan Saved</h1>
<table>
	<tr>
		<td>PersonID:</td>
		<td><c:out value="${loanModel.person.personID}"/></td>
	</tr>
	<tr>
		<td>LoanID:</td>
		<td><c:out value="${loanModel.loanID}"/></td>
	</tr>
</table>
<%@ include file="/WEB-INF/layouts/includes.jsp" %>
<form:form commandName="amortizationScheduleModel">
	<table class="loans" id="loantable">
		<col style="width: 30px;"/>
		<caption><label>Loans</label></caption>
		<tr>
			<th></th>
			<th><label>Loan Type</label></th>
			<th><label>Current Principal</label></th>
			<th><label>Current Interest</label></th>
			<th><label>Current Fees</label></th>
		</tr>
		<c:forEach var="loan" items="${amortizationScheduleModel.loans}" varStatus="index">
			<tr class="loanrow" >
				<td><form:checkbox class="loancell" disabled="false" path="loans[${index.count-1}].selected"/></td>
				<td><form:input class="loancell" disabled="true" path="loans[${index.count-1}].loanType"/></td>
				<td><form:input class="loancell" disabled="true" path="loans[${index.count-1}].currentPrincipal"/></td>
				<td><form:input class="loancell" disabled="true" path="loans[${index.count-1}].currentInterest"/></td>
				<td><form:input class="loancell" disabled="true" path="loans[${index.count-1}].currentFees"/></td>
			</tr>
		</c:forEach>
	</table>
	<button name="_eventId_save">Submit</button>
</form:form>
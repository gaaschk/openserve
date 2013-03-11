<%@ include file="/WEB-INF/layouts/includes.jsp" %>
<form:form modelAttribute="loanModel">
	<fieldset>
		<label>Loan Type:</label>
		<form:select path="selectedLoanTypeID">
     		<form:option value="-">Select Loan Type</form:option>
     	    <form:options items="${loanModel.loanTypeList}" itemValue="loanProgramID" itemLabel="name"/>
     	</form:select>
	</fieldset>
	<fieldset>
		<label>Lender:</label>
		<form:select path="selectedLenderID">
			<form:option value="-">Select Lender</form:option>
			<form:options items="${loanModel.lenderList}" itemValue="lenderID" itemLabel="name"/>
		</form:select>
	</fieldset>
	<table>
  		<tr>
    		<th>Disbursement Date</th>
    		<th>Disbursement Amount</th>
  		</tr>
  		<c:forEach var="disb" items="${loanModel.addedDisbursements}" varStatus="index">
			<tr>
				<td><form:input disabled="true" path="addedDisbursements[${index.count-1}].disbursementDate"/></td>
				<td><form:input disabled="true" path="addedDisbursements[${index.count-1}].disbursementAmount"/></td>
  			</tr>
		</c:forEach>
	</table>
	<fieldset>
		<label>Date:</label>
		<form:input class="masked" path="newDisbursement.disbursementDate" data-meiomask="fixed.date-us"/>
		<form:errors path="newDisbursement.disbursementDate"/>
	</fieldset>
	<fieldset>
		<label>Amount:</label>
		<form:input class="masked" path="newDisbursement.disbursementAmount" data-meiomask="reverse.dollar"/>
	</fieldset>
	<input type="submit" name="_eventId_disbursementAdded" value="Add Disbursement"/>
	<input type="submit" name="_eventId_doneAdding" value="Done"/>
</form:form>

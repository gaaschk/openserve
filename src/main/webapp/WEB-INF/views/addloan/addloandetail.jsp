<%@ include file="/WEB-INF/layouts/includes.jsp" %>
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
<form:errors path="addedDisbursements"/>
	<fieldset>
		<label>Effective Date:</label>
		<form:input class="masked" path="effectiveDate" type="text" data-meiomask="fixed.date-us"/>
		<form:errors path="effectiveDate"/>
	</fieldset>
	<fieldset>
		<label class="nobr">Starting Principal:</label>
		<form:input class="masked" path="startingPrincipal" type="text" data-meiomask="reverse.dollar"/>
	</fieldset>
	<fieldset>
		<label>Starting Interest:</label>
		<form:input class="masked" path="startingInterest" data-meiomask="reverse.dollar" />
	</fieldset>
	<fieldset>
		<label>Starting Fees:</label>
		<form:input class="masked" path="startingFees" data-meiomask="reverse.dollar" />
	</fieldset>
	<input type="submit" name="_eventId_save" value="Save Loan"></input>
	
<%@ include file="/WEB-INF/layouts/includes.jsp"%>

<form:form modelAttribute="paymentModel">
<table>
	<tr>
		<td><strong>SSN:</strong></td>
		<td><c:out value="${paymentModel.theBorrower.ssn}"/></td>
	</tr>
	<tr>
		<td><strong>Name:</strong></td>
		<td><c:out value="${paymentModel.theBorrower.firstName}"/><span>  </span><c:out value="${paymentModel.theBorrower.lastName}"/></td>
	</tr>
</table>
<table>
	<tr>
		<td><strong>Effective Date:</strong></td>
		<td><form:input class="masked" path="paymentEffectiveDate" type="text" data-meiomask="fixed.date-us"/></td>
	</tr>
	<tr>
		<td><strong>Payment Amount:</strong></td>
		<td><form:input class="masked" path="paymentAmount" type="text" data-meiomask="reverse.dollar"/></td>
	</tr>
</table>
	<input type="submit" name="_eventId_submitPayment" value="Apply Payment" label="Apply Payment" data-dojo-type="dijit/form/Button"></input>
</form:form>

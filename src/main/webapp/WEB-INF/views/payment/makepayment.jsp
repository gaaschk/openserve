<%@ include file="/WEB-INF/layouts/includes.jsp"%>

<form:form modelAttribute="paymentModel">
<table>
	<tr>
		<td colspan="2">
			<fieldset>
				<label>SSN:</label>
				<c:out value="${paymentModel.theBorrower.ssn}"/>
			</fieldset>
		</td>
	</tr>
	<tr>
		<td>
			<fieldset>
				<label>First Name:</label>
				<c:out value="${paymentModel.theBorrower.firstName}"/>
			</fieldset>
		</td>
		<td>
			<fieldset>
				<label>Last Name:</label>
				<c:out value="${paymentModel.theBorrower.lastName}"/>
			</fieldset>
		</td>
	</tr>
</table>
	<fieldset>
		<label>Payment Amount:</label>		
		<form:input class="masked" path="paymentAmount" type="text" data-meiomask="reverse.dollar"/>
	</fieldset>
	<input type="submit" name="_eventId_submitPayment" value="Apply Payment"></input>
</form:form>

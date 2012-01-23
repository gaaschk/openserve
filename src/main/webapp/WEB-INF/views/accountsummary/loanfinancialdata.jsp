<%@ include file="/WEB-INF/layouts/includes.jsp" %>
<form:form commandName="loandetailmodel.loanFinancialData">
	<table>
		<tr>
			<td>
				<fieldset>
					<label>Loan Type:</label>
					<form:input disabled="true" path="loanType"/>
				</fieldset>
			</td>
		</tr>
		<tr>
			<td>
				<fieldset>
					<label class="nobr">Current Principal:</label>
					<form:input disabled="true" path="currentPrincipal"/>
				</fieldset>
			</td>
			<td>
				<fieldset>
					<label class="nobr">Minimum Payment Amt:</label>
					<form:input disabled="true" path="minimumPaymentAmount"/>
				</fieldset>
			</td>
		</tr>
		<tr>
			<td>
				<fieldset>
					<label>Current Interest:</label>
					<form:input disabled="true" path="currentInterest"/>
				</fieldset>
			</td>
		</tr>
		<tr>
			<td>
				<fieldset>
					<label>Current Fees:</label>
					<form:input disabled="true" path="currentFees"/>
				</fieldset>
			</td>
		</tr>
	</table>
</form:form>

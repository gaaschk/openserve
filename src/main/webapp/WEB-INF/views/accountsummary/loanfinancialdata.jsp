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
					<label class="nobr">Min. Payment Amt:</label>
					<form:input disabled="true" path="minimumPaymentAmount"/>
				</fieldset>
			</td>
			<td>
				<fieldset>
					<label class="nobr">Repay Start Date:</label>
					<form:input disabled="true" path="repaymentStartDate"/>
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
			<td>
				<fieldset>
					<label class="nobr">Next Due Date:</label>
					<form:input disabled="true" path="nextDueDate"/>
				</fieldset>
			</td>
			<td>
				<fieldset>
					<label class="nobr">First Due Date:</label>
					<form:input disabled="true" path="firstDueDate"/>
				</fieldset>
			</td>
			<td>
				<fieldset>
					<label class="nobr">Initial Due Date:</label>
					<form:input disabled="true" path="initialDueDate"/>
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
			<td>
				<fieldset>
					<label class="nobr">Last Paid Date:</label>
					<form:input disabled="true" path="lastPaidDate"/>
				</fieldset>
			</td>
		</tr>
	</table>
</form:form>

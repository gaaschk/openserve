<%@ include file="/WEB-INF/layouts/includes.jsp" %>
<form:form commandName="loandetailmodel.loanFinancialData">
	<table>
		<tr>
			<td>
				<label>Loan Type:</label>
			</td>
			<td>
				<form:input disabled="true" path="loanProgram"/>
			</td>
			<td>
				<label>Eff. Int. Rate:</label>
			</td>
			<td>
				<form:input disabled="true" path="effectiveIntRate"/>
			</td>
			<td>
				<label>Daily Int. Amt.:</label>
			</td>
			<td>
				<form:input disabled="true" path="dailyInterestAmount"/>
			</td>
			<td>
				<label>Base Rate:</label>
			</td>
			<td>
				<form:input disabled="true" path="baseRate"/>
			</td>
			<td>
				<label>Margin:</label>
			</td>
			<td>
				<form:input disabled="true" path="margin"/>
			</td>
		</tr>
		<tr>
			<td>
					<label class="nobr">Current Principal:</label>
			</td>
			<td>
					<form:input disabled="true" path="currentPrincipal"/>
			</td>
			<td>
					<label class="nobr">First Due Date:</label>
			</td>
			<td>
					<form:input disabled="true" path="firstDueDate"/>
			</td>
			<td>
					<label class="nobr">Repay Start Date:</label>
			</td>
			<td>
					<form:input disabled="true" path="repaymentStartDate"/>
			</td>
			<td>
					<label class="nobr">Min. Payment Amt:</label>
			</td>
			<td>
					<form:input disabled="true" path="minimumPaymentAmount"/>
			</td>
			<td>
					<label class="nobr">Remaining Term:</label>
			</td>
			<td>
					<form:input disabled="true" path="remainingTerm"/>
			</td>
		</tr>
		<tr>
			<td>
					<label>Current Interest:</label>
			</td>
			<td>
					<form:input disabled="true" path="currentInterest"/>
			</td>
			<td>
					<label class="nobr">Initial Due Date:</label>
			</td>
			<td>
					<form:input disabled="true" path="initialDueDate"/>
			</td>
			<td>
					<label class="nobr">Used Term:</label>
			</td>
			<td>
					<form:input disabled="true" path="usedTerm"/>
			</td>
			<td>
					<label class="nobr">Last Paid Date:</label>
			</td>
			<td>
					<form:input disabled="true" path="lastPaidDate"/>
			</td>
		</tr>
		<tr>
			<td>
					<label>Current Fees:</label>
			</td>
			<td>
					<form:input disabled="true" path="currentFees"/>
			</td>
			<td>
					<label class="nobr">Next Due Date:</label>
			</td>
			<td>
					<form:input disabled="true" path="nextDueDate"/>
			</td>
			<td>
					<label class="nobr">Current Unpaid Due Date:</label>
			</td>
			<td>
					<form:input disabled="true" path="currentUnpaidDueDate"/>
			</td>
		</tr>
	</table>
</form:form>

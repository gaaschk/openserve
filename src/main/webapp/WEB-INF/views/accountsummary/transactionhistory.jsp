<%@ include file="/WEB-INF/layouts/includes.jsp" %>
<form:form commandName="loandetailmodel">
  <table class="loanevents" id="loaneventtable">
	<col style="width: 90px;"/>
	<caption><label>Loan History</label></caption>
	<tr>
		<th style="width: 90px; ">Effective Date</th>
		<th><label>Posted Date</label></th>
		<th><label>Event Type</label></th>
		<th><label>Accrued Interest</label></th>
		<th style="width: 120px; " ><label style="width: 120px; " >Principal Change</label></th>
		<th><label>Interest Change</label></th>
		<th style="width: 120px; " ><label style="width: 120px; " >Fees Change</label></th>	
		<th style="width: 120px; " ><label style="width: 120px; " >Ending Principal</label></th>
		<th><label >Ending Interest</label></th>
		<th><label style="width: 90px; " >Ending Fees</label></th>	
	</tr>
	<c:forEach var="loanevent" items="${loandetailmodel.loanHistory}" varStatus="index">
		<tr class="loaneventrow" >
			<td><form:input style="width: 90px; " disabled="true" path="loanHistory[${index.count-1}].loanEventEffectiveDate"/></td>
			<td><form:input disabled="true" path="loanHistory[${index.count-1}].loanEventPostDate"/></td>
			<td><form:input disabled="true" path="loanHistory[${index.count-1}].loanEventType"/></td>
			<td><form:input disabled="true" path="loanHistory[${index.count-1}].interestAccrued"/></td>
			<td><form:input style="width: 120px; " disabled="true" path="loanHistory[${index.count-1}].principalChange"/></td>
			<td><form:input disabled="true" path="loanHistory[${index.count-1}].interestChange"/></td>
			<td><form:input style="width: 120px; " disabled="true" path="loanHistory[${index.count-1}].feesChange"/></td>
			<td><form:input style="width: 120px; " disabled="true" path="loanHistory[${index.count-1}].endingPrincipal"/></td>
			<td><form:input disabled="true" path="loanHistory[${index.count-1}].endingInterest"/></td>
			<td><form:input style="width: 90px; " disabled="true" path="loanHistory[${index.count-1}].endingFees"/></td>
		</tr>
	 </c:forEach>
  </table>
</form:form>

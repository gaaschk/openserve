<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<form:form commandName="loandetailmodel">
  <table class="loanevents" id="loaneventtable">
	<caption><label>Loan History</label></caption>
	<tr>
		<th><label>Effective Date</label></th>
		<th><label>Posted Date</label></th>
		<th><label>Event Type</label></th>
		<th><label>Accrued Interest</label></th>
		<th><label>Principal Change</label></th>
		<th><label>Interest Change</label></th>
		<th><label>Fees Change</label></th>	
		<th><label>Ending Principal</label></th>
		<th><label>Ending Interest</label></th>
		<th><label>Ending Fees</label></th>	
	</tr>
	<c:forEach var="loanevent" items="${loandetailmodel.loanHistory}" varStatus="index">
		<tr class="loaneventrow" >
			<td><form:input disabled="true" path="loanHistory[${index.count-1}].loanEventEffectiveDate"/></td>
			<td><form:input disabled="true" path="loanHistory[${index.count-1}].loanEventPostDate"/></td>
			<td><form:input disabled="true" path="loanHistory[${index.count-1}].loanEventType"/></td>
		</tr>
	 </c:forEach>
  </table>
</form:form>

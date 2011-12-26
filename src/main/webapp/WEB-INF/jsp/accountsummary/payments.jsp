<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<form:form commandName="paymenthistorymodel">
  <table class="payments" id="paymenttable">
	<caption><label>Payment History</label></caption>
	<tr>
		<th><label>Effective Date</label></th>
		<th><label>Posted Date</label></th>
		<th><label>Payment Amount</label></th>
	</tr>
	<c:forEach var="payment" items="${paymenthistorymodel.payments}" varStatus="index">
		<tr class="paymentrow" >
			<td><form:input disabled="true" path="payments[${index.count-1}].paymentEffectiveDate"/></td>
			<td><form:input disabled="true" path="payments[${index.count-1}].paymentPostDate"/></td>
			<td><form:input disabled="true" path="payments[${index.count-1}].paymentAmount"/></td>
		</tr>
	 </c:forEach>
  </table>
</form:form>

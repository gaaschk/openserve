<%@ include file="/WEB-INF/layouts/includes.jsp" %>
<form:form commandName="loandetailmodel">
	<fieldset>
		<label>Created Date:</label>
		<form:input disabled="true" path="currentAmortization.creationDate"/>
	</fieldset>
	<fieldset>
		<label>Effective Date:</label>
		<form:input disabled="true" path="currentAmortization.effectiveDate"/>
	</fieldset>
  <table>
    <tr>
    	<th><label>Payment Count</label></th>
    	<th><label>Payment Amount</label></th>
    </tr>
    <c:forEach var="paymentgroup" items="${loandetailmodel.currentAmortization.paymentGroups}" varStatus="pgindex">
      <tr>
        <td><form:input disabled="true" path="currentAmortization.paymentGroups[${pgindex.count-1}].paymentCount"/></td>
        <td><form:input disabled="true" path="currentAmortization.paymentGroups[${pgindex.count-1}].paymentAmount"/></td>
      </tr>
    </c:forEach>
  </table>
</form:form>

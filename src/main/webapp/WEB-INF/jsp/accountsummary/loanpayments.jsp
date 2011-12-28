<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<form:form commandName="selectedpayment">
  <table class="loanpaymenttable">
    <tr>
    	<th><label>Loan ID</label></th>
    	<th><label>Amount to Loan</label></th>
    </tr>
    <c:forEach var="loanpayment" items="${selectedpayment.loanPayments}" varStatus="lpindex">
      <tr>
        <td><form:input disabled="true" path="loanPayments[${lpindex.count-1}].loanID"/></td>
        <td><form:input disabled="true" path="loanPayments[${lpindex.count-1}].appliedAmount"/></td>
      </tr>
    </c:forEach>
  </table>
</form:form>

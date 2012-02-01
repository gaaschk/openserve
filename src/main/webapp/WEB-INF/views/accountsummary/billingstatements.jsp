<%@ include file="/WEB-INF/layouts/includes.jsp" %>
<form:form commandName="loandetailmodel">
  <table>
    <tr>
    	<th><label>Created Date</label></th>
    	<th><label>Due Date</label></th>
    	<th><label>Minimum Required Payment</label></th>
    	<th><label>Amount Paid</label></th>
    	<th><label>Satisfied Date</label></th>
    </tr>
    <c:forEach var="statement" items="${loandetailmodel.billingStatements}" varStatus="bsindex">
      <tr>
        <td><form:input disabled="true" path="billingStatements[${bsindex.count-1}].createdDate"/></td>
        <td><form:input disabled="true" path="billingStatements[${bsindex.count-1}].dueDate"/></td>
        <td><form:input disabled="true" path="billingStatements[${bsindex.count-1}].minimumRequiredPayment"/></td>
        <td><form:input disabled="true" path="billingStatements[${bsindex.count-1}].paidAmount"/></td>
        <td><form:input disabled="true" path="billingStatements[${bsindex.count-1}].satisfiedDate"/></td>
      </tr>
    </c:forEach>
  </table>
</form:form>
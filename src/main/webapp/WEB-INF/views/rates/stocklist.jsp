<%@ include file="/WEB-INF/layouts/includes.jsp" %>
<form:form modelAttribute="stockList">
	<table>
		<tr>
			<th>Symbol</th>
			<th>Name</th>
			<th>Quote Date</th>
			<th>Open</th>
			<th>High</th>
			<th>Low</th>
			<th>Last</th>	
		</tr>
		<c:forEach var="stock" items="${stockList.stocks}" varStatus="index">
			<tr>
				<td><form:input disabled="true" path="stocks[${index.count-1}].symbol"/></td>
				<td><form:input disabled="true" path="stocks[${index.count-1}].name"/></td>
				<td><form:input disabled="true" path="stocks[${index.count-1}].quoteDate"/></td>
				<td><form:input disabled="true" path="stocks[${index.count-1}].open"/></td>
				<td><form:input disabled="true" path="stocks[${index.count-1}].high"/></td>
				<td><form:input disabled="true" path="stocks[${index.count-1}].low"/></td>
				<td><form:input disabled="true" path="stocks[${index.count-1}].last"/></td>
			</tr>
		</c:forEach>
	</table>
</form:form>
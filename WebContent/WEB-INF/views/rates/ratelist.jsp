<%@ include file="/WEB-INF/layouts/includes.jsp" %>
<form:form modelAttribute="rateList">
	<fieldset>
		<label>Date:</label>
		<form:input path="quoteDate"/>
	</fieldset>
	<input type="submit" name="_eventId_changeDate" value="Change Date"></input>
	<table>
		<tr>
			<th>Symbol</th>
			<th>Name</th>
			<th>Quote Date</th>
			<th>Value</th>	
		</tr>
		<c:forEach var="rate" items="${rateList.rates}" varStatus="index">
			<tr>
				<td><form:input disabled="true" path="rates[${index.count-1}].symbol"/></td>
				<td><form:input path="rates[${index.count-1}].name"/></td>
				<td><form:input disabled="true" path="rates[${index.count-1}].quoteDate"/></td>
				<td><form:input path="rates[${index.count-1}].strValue" data-meiomask="regexp.percent"/></td>
			</tr>
		</c:forEach>
	</table>
	<input type="submit" name="_eventId_saveRates" value="Save Changes"></input>
	<br>
	<br>
	<label>Add a new Rate:</label>
	<fieldset>
		<label>Symbol:</label>
		<form:input path="newRateSymbol"/>
	</fieldset>
	<fieldset>
		<label>Name:</label>
		<form:input path="newRateName"/>
	</fieldset>
	<input type="submit" name="_eventId_addRate" value="Add Rate"></input>
</form:form>

<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<form:form modelAttribute="personModel">
	<table>
		<tr>
			<td>SSN:</td>
			<td><form:input path="ssn"/></td>
		</tr>
		<tr>
	 		<td colspan="2"><input type="submit" align="center" value="Search"></input></td>
		</tr>
	</table>
</form:form>

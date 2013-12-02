<%@ include file="/WEB-INF/layouts/includes.jsp" %>
<form:form modelAttribute="personSearchCriteria" data-dojo-type="dijit/form/Form">
		<table>
			<tr><td><strong>SSN: </strong></td><td>
			<form:input type="text" path="ssn" 
					data-meiomask-options="{mask: '999-99-9999', autoTab: true, removeIfInvalid: true}" 
					data-meiomask="fixed"/>
			</td></tr>
		</table>
	<input type="submit" name="_eventId_search" value="Search" label="Search" data-dojo-type="dijit/form/Button"/>
</form:form>

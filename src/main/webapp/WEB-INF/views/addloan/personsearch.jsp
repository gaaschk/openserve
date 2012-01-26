<%@ include file="/WEB-INF/layouts/includes.jsp" %>
<form:form modelAttribute="personSearchCriteria">
	<fieldset>
		<label>SSN:</label>		
		<form:input class="masked" path="ssn" type="text" data-meiomask-options="{mask: '999-99-9999', autoTab: true, removeIfInvalid: true}" data-meiomask="fixed"/>
		<form:errors path="ssn"/>
	</fieldset>
	<input type="submit" name="_eventId_search" value="Search"/>
</form:form>


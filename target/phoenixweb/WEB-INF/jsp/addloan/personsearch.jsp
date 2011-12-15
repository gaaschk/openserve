<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<form:form modelAttribute="personModel">
	<fieldset>
		<label>SSN:</label>		
		<form:input class="masked" path="ssn" type="text" data-meiomask-options="{mask: '999-99-9999', autoTab: true, removeIfInvalid: true}" data-meiomask="fixed"/>
	</fieldset>
	<input type="submit" value="Search"></input>
</form:form>


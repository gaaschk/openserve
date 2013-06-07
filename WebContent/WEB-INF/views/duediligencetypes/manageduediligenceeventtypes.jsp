<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/WEB-INF/layouts/includes.jsp" %>
<form:form modelAttribute="manageDueDiligenceTypesModel">
	<div data-dojo-type="dijit/layout/ContentPane">
		<table>
			<tr>
				<th>Name</th>
				<th>Description</th>
			</tr>
			<c:forEach var="dueDiligenceType" items="${manageDueDiligenceTypesModel.allDueDiligenceEventTypes}" varStatus="ddTypeIndex">
				<tr>
					<td><form:input path="allDueDiligenceEventTypes[${ddTypeIndex.count-1}].name"/></td>
					<td><form:input path="allDueDiligenceEventTypes[${ddTypeIndex.count-1}].description"/></td>
				</tr>
			</c:forEach>
		</table>
		<input data-dojo-type="dijit/form/Button" type="submit" name="_eventId_addDueDiligenceType" value="Add Type" label="Add Type"/>
		<input data-dojo-type="dijit/form/Button" type="submit" name="_eventId_save" value="Save" label="Save"/>
	</div>
</form:form>
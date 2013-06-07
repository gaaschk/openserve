<form:form commandName="manageDueDiligenceSchedulesModel">
	Some cool data
	<table>
		<c:forEach var="schedule" items="${schedules}" varStatus="index">
			<tr>
				<td><form:input path="${schedules[index.count-1]}.effectiveDate"/></td>
			</tr>
		</c:forEach>
	</table>
	<input data-dojo-type="dijit/form/Button" type="submit" name="_eventId_addDueDiligenceSchedule" value="Add Schedule" label="Add Schedule"/>
</form:form>

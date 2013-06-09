<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/WEB-INF/layouts/includes.jsp" %>
<form:form id="dueDiligenceForm" commandName="manageDueDiligenceSchedulesModel">
	<div data-dojo-type="dijit/layout/ContentPane">
		<form:select id="loanProgramSelection" path="selectedLoanProgram" data-dojo-type="dijit/form/FilteringSelect">
     		<form:options items="${manageDueDiligenceSchedulesModel.allLoanPrograms}" itemValue="loanProgramID" itemLabel="name"/>
     		<script type="dojo/method" event="onChange" args="newValue">
				require(['dijit/registry','dojo/domReady!'],function(registry){
					var settingsContainer = registry.byId("schedulePane");
					console.debug("Looking for " + "scheduleModelPane-"+newValue);
					var paneToSelect = registry.byId("scheduleModelPane-"+newValue);
					console.debug("Pane to select is: " + paneToSelect);
					settingsContainer.selectChild(paneToSelect);
				})		
     		</script>
     	</form:select>
     	<div id="schedulePane" data-dojo-type="dijit/layout/StackContainer">
			<c:forEach var="scheduleModel" items="${manageDueDiligenceSchedulesModel.scheduleModels}" varStatus="index">
				<div id="scheduleModelPane-${scheduleModel.loanProgramId}" data-dojo-type="dijit/layout/ContentPane">
					<table>
						<tr><th/><th>Begin Date</th><th>End Date</th></tr>
						<c:forEach var="schedule" items="scheduleModels[${index.count-1}].schedules" varStatus="scheduleIndex">
							<tr>
								<td><input type="radio" class="scheduleRB" id="${schedule}.dueDiligenceScheduleID"/></td>
								<td><form:input disabled="false" path="scheduleModels[${index.count-1}].schedules[${scheduleIndex.count-1}].effectiveDate"/></td>
								<td><form:input disabled="false" path="scheduleModels[${index.count-1}].schedules[${scheduleIndex.count-1}].endDate"/></td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</c:forEach>
     	</div>
	</div>
</form:form>
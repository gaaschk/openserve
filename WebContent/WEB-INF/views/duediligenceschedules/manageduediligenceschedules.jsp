<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/WEB-INF/layouts/includes.jsp" %>
<script type="text/javascript">
	require(['dijit/form/RadioButton', 
			 'dijit/registry', 
			 'dojo/parser', 
			 'dojo/_base/array',
			 'dojo/query',
			 'dojo/on',
			 'dojo/ready', 
			 'dojo/domReady!'], 
			 function(RadioButton, registry, parser, array, query, on, ready) {
			 	ready(function(){
			 		var isOneClicked = false;
			 		array.forEach(query(".scheduleRB"), function(oneRB){
						console.debug("setting up event for RB: " + oneRB.id);
						if(!isOneClicked){
							oneRB.checked = true;
							isOneClicked = true;
						}
						on(oneRB, "click", function(event){
							console.debug("an RB was clicked with name "+this.name+"!!! Looking for container schedulesPane-"+this.name.substring(11,this.name.length));
							var settingsContainer = registry.byId("schedulesPane-"+this.name.substring(11,this.name.length));
							console.debug("Found container " + settingsContainer);
							console.debug("Looking for pane eventPane-"+this.id);
							var paneToSelect = registry.byId("eventPane-"+this.id);
							console.debug("Pane to select is: " + paneToSelect);
							settingsContainer.selectChild(paneToSelect);
						});
					});
				});
			 });
</script>
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
						<c:forEach var="schedule" items="${scheduleModel.schedules}" varStatus="scheduleIndex">
							<tr>
								<td><input type="radio" name="scheduleRB-${scheduleModel.loanProgramId}" value="${schedule}.dueDiligenceScheduleID" class="scheduleRB" id="${schedule.dueDiligenceScheduleID}"/></td>
								<td><form:input disabled="false" path="scheduleModels[${index.count-1}].schedules[${scheduleIndex.count-1}].effectiveDate"/></td>
								<td><form:input disabled="false" path="scheduleModels[${index.count-1}].schedules[${scheduleIndex.count-1}].endDate"/></td>
							</tr>
						</c:forEach>
					</table>
					<div id="schedulesPane-${scheduleModel.loanProgramId}" data-dojo-type="dijit/layout/StackContainer">
						<c:forEach var="schedule" items="${scheduleModel.schedules}" varStatus="scheduleIndex">
							<div id="eventPane-${schedule.dueDiligenceScheduleID}" data-dojo-type="dijit/layout/ContentPane">
								<table>
									<tr><th>Min Days</th><th>Max Days</th><th>Default Days</th><th>Event Type</th></tr>
									<c:forEach var="scheduleEvent" items="${schedule.events}" varStatus="eventIndex">
										<tr>
											<td><form:input disabled="false" path="scheduleModels[${index.count-1}].schedules[${scheduleIndex.count-1}].events[${eventIndex.count-1}].minDelqDays"/></td>
											<td><form:input disabled="false" path="scheduleModels[${index.count-1}].schedules[${scheduleIndex.count-1}].events[${eventIndex.count-1}].maxDelqDays"/></td>
											<td><form:input disabled="false" path="scheduleModels[${index.count-1}].schedules[${scheduleIndex.count-1}].events[${eventIndex.count-1}].defaultDelqDays"/></td>
										</tr>							
									</c:forEach>
								</table>
							</div>						
						</c:forEach>
					</div>
				</div>
			</c:forEach>
     	</div>
	</div>
</form:form>
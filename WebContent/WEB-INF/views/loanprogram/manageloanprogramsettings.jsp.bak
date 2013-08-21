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
			 		array.forEach(query(".loanProgramRB"), function(oneRB){
						console.debug("setting up event for RB: " + oneRB.id);
						if(!isOneClicked){
							oneRB.checked = true;
							isOneClicked = true;
						}
						on(oneRB, "click", function(event){
							console.debug("an RB was clicked!!!");
							var settingsContainer = registry.byId("programSettingsContainer");
							console.debug("Looking for " + "settings_"+this.id);
							var paneToSelect = registry.byId("settings_"+this.id);
							console.debug("Pane to select is: " + paneToSelect);
							settingsContainer.selectChild(paneToSelect);
						});
					});
				});
			 });
</script>
<form:form modelAttribute="manageLoanProgramsModel">
	<div data-dojo-type="dijit/layout/ContentPane">
		<div data-dojo-type="dijit/layout/ContentPane">
			<table class="loanTypes" id="loantypetable">
				<col style="width: 30px;"/>
				<caption style="text-align: left;"><b>Loans Programs</b></caption>
				<tr>
					<th></th>
					<th><label>Name</label></th>
					<th><label>Description</label></th>
				</tr>
				<c:forEach var="loanProgramSettingsModel" items="${manageLoanProgramsModel.allLoanProgramModels}" varStatus="typeIndex">
					<tr>
						<td><input type="radio" class="loanProgramRB" name="loanProgramRB" id="${loanProgramSettingsModel.loanProgram.loanProgramID}"/></td>
						<td><form:input class="loantypecell" disabled="false" path="allLoanProgramModels[${typeIndex.count-1}].loanProgram.name"/></td>
						<td><form:input class="loantypecell" disabled="false" path="allLoanProgramModels[${typeIndex.count-1}].loanProgram.description"/></td>
					</tr>
				</c:forEach>
			</table>
			<input data-dojo-type="dijit/form/Button" type="submit" name="_eventId_addLoanProgram" value="Add Loan Program" label="Add Loan Program"/>
			<br>
		</div>
		<br>
		<caption style="text-align: left;"><b>Loans Program Settings</b></caption>
		<div data-dojo-type="dijit/layout/StackContainer" id="programSettingsContainer">
			<c:forEach var="loanProgramSettingsModel" items="${manageLoanProgramsModel.allLoanProgramModels}" varStatus="typeIndex">
				<div data-dojo-type="dijit/layout/ContentPane" id="settings_${loanProgramSettingsModel.loanProgram.loanProgramID}">
					<table>
						<col style="width: 30px;"/>
						<tr>
							<th>Loan Program</th>
							<th><label>Effective Date</label></th>
							<th><label>End Date</label></th>
							<th><label>Days Late For Fee</label></th>
						</tr>
						<c:forEach var="defaultLoanProgramSettingsModel" items="${loanProgramSettingsModel.allDefaultLoanProgramSettingsModels}" varStatus="index">
							<tr>
								<td><form:input disabled="true" path="allLoanProgramModels[${typeIndex.count-1}].allDefaultLoanProgramSettingsModels[${index.count-1}].defaultLoanProgramSettings.loanProgram"/></td>
								<td><form:input disabled="false" path="allLoanProgramModels[${typeIndex.count-1}].allDefaultLoanProgramSettingsModels[${index.count-1}].defaultLoanProgramSettings.effectiveDate"/></td>
								<td><form:input disabled="false" path="allLoanProgramModels[${typeIndex.count-1}].allDefaultLoanProgramSettingsModels[${index.count-1}].defaultLoanProgramSettings.endDate"/></td>
								<td><form:select path="allLoanProgramModels[${typeIndex.count-1}].allDefaultLoanProgramSettingsModels[${index.count-1}].defaultLoanProgramSettings.baseRate">
     								<form:options items="${loanTypeProfileModel.allRates}" itemValue="rateId" itemLabel="tickerSymbol"/>
     								</form:select></td>
								<td><form:select path="allLoanProgramModels[${typeIndex.count-1}].allDefaultLoanProgramSettingsModels[${index.count-1}].defaultLoanProgramSettings.baseRateUpdateFrequency"><form:options/></form:select></td>
								<td><form:input disabled="false" path="allLoanProgramModels[${typeIndex.count-1}].allDefaultLoanProgramSettingsModels[${index.count-1}].defaultLoanProgramSettings.daysBeforeDueToBill"/></td>
								<td><form:input disabled="false" path="allLoanProgramModels[${typeIndex.count-1}].allDefaultLoanProgramSettingsModels[${index.count-1}].defaultLoanProgramSettings.daysLateForFee"/></td>
								<td><form:input disabled="false" path="allLoanProgramModels[${typeIndex.count-1}].allDefaultLoanProgramSettingsModels[${index.count-1}].defaultLoanProgramSettings.graceMonths"/></td>
								<td><form:checkbox disabled="false" path="allLoanProgramModels[${typeIndex.count-1}].allDefaultLoanProgramSettingsModels[${index.count-1}].defaultLoanProgramSettings.variableRate"/></td>
								<td><form:input disabled="false" path="allLoanProgramModels[${typeIndex.count-1}].allDefaultLoanProgramSettingsModels[${index.count-1}].defaultLoanProgramSettings.lateFeeAmount"/></td>
								<td><form:input disabled="false" path="allLoanProgramModels[${typeIndex.count-1}].allDefaultLoanProgramSettingsModels[${index.count-1}].defaultLoanProgramSettings.maximumLoanTerm"/></td>
								<td><form:input disabled="false" path="allLoanProgramModels[${typeIndex.count-1}].allDefaultLoanProgramSettingsModels[${index.count-1}].defaultLoanProgramSettings.minDaysToFirstDue"/></td>
								<td><form:input disabled="false" path="allLoanProgramModels[${typeIndex.count-1}].allDefaultLoanProgramSettingsModels[${index.count-1}].defaultLoanProgramSettings.prepaymentDays"/></td>
								<td><form:select path="allLoanProgramModels[${typeIndex.count-1}].allDefaultLoanProgramSettingsModels[${index.count-1}].defaultLoanProgramSettings.repaymentStartType"><form:options/></form:select></td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</c:forEach>
		</div>
	</div> <!-- end main content pane -->
	<input data-dojo-type="dijit/form/Button" type="submit" name="_eventId_save" value="Save" label="Save"/>
</form:form>

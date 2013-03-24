<%@ include file="/WEB-INF/layouts/includes.jsp" %>
<script type="text/javascript">
window.addEvent('domready', function() {
 
	var loantypes = $$("#loantypetable .loantyperowrb");
	loantypes.each(function(loantype, i) {
		loantype.addEvent("click", function(event) {
			inittables(this);
		});
	});
	inittables(this);
});

function inittables(thebutton){
	var selectedloantypes = $$("#loantypetable .loantyperowrb");
	for(var idx = 0; idx < selectedloantypes.length; idx++){
		if(selectedloantypes[idx] != thebutton){
			selectedloantypes[idx].checked = false;
			selectedloantypes[idx].value = "false";
			$$("#"+selectedloantypes[idx].id+"-panel").fancyHide();
		}
		else{
			selectedloantypes[idx].value = "true";
			$$("#"+selectedloantypes[idx].id+"-panel").fancyShow();
		}
	}
}
</script>
	<form:form modelAttribute="defaultLoanProgramSettingsListModel">
<div class="panel">
<div class="panel">
<table class="loanTypes" id="loantypetable">
	<col style="width: 30px;"/>
	<caption style="text-align: left;"><b>Loans Programs</b></caption>
	<tr>
		<th></th>
		<th><label>Name</label></th>
		<th><label>Description</label></th>
	</tr>
	<c:forEach var="loanTypeProfilesModel" items="${defaultLoanProgramSettingsListModel.defaultLoanProgramSettingsModels}" varStatus="typeIndex">
		<tr class="loantyperow" >
			<td><form:radiobutton class="loantyperowrb" id="${loanTypeProfilesModel.loanProgram.loanProgramID}" path="defaultLoanProgramSettingsModels[${typeIndex.count-1}].selected" value="true"/></td>
			<td><form:input class="loantypecell" disabled="false" path="defaultLoanProgramSettingsModels[${typeIndex.count-1}].loanProgram.name"/></td>
			<td><form:input class="loantypecell" disabled="false" path="defaultLoanProgramSettingsModels[${typeIndex.count-1}].loanProgram.description"/></td>
		</tr>
	</c:forEach>
</table>
<input type="submit" name="_eventId_addLoanType" value="Add Loan Type" />
<br>
</div>
<br>
<caption style="text-align: left;"><b>Loans Program Settings</b></caption>
<c:forEach var="loanTypeProfilesModel" items="${loanTypeProfileModel.loanTypeProfilesModels}" varStatus="typeIndex">
<div id="${loanTypeProfilesModel.loanType.loanProgramID}-panel" class="loanTypeProfiles-panel">
<table id="${loanTypeProfilesModel.loanType.loanProgramID}-loantypeprofiletable">
	<col style="width: 30px;"/>
	<tr>
		<th>Loan Program</th>
		<th><label>Effective Date</label></th>
		<th><label>End Date</label></th>
		<th><label>Days Late For Fee</label></th>
	</tr>
	<c:forEach var="loanTypeProfile" items="${loanTypeProfilesModel.loanTypeProfiles}" varStatus="index">
		<tr class="loantypeprofilerow" >
			<td><form:input class="loantypeprofilecell" disabled="true" path="loanTypeProfilesModels[${typeIndex.count-1}].loanTypeProfiles[${index.count-1}].loanProgram"/></td>
			<td><form:input class="loantypeprofilecell" disabled="false" path="loanTypeProfilesModels[${typeIndex.count-1}].loanTypeProfiles[${index.count-1}].effectiveDate"/></td>
			<td><form:input class="loantypeprofilecell" disabled="false" path="loanTypeProfilesModels[${typeIndex.count-1}].loanTypeProfiles[${index.count-1}].endDate"/></td>
			<td><form:select path="loanTypeProfilesModels[${typeIndex.count-1}].loanTypeProfiles[${index.count-1}].baseRate">
     				<form:options items="${loanTypeProfileModel.allRates}" itemValue="rateId" itemLabel="tickerSymbol"/>
     			</form:select></td>
			<td><form:select path="loanTypeProfilesModels[${typeIndex.count-1}].loanTypeProfiles[${index.count-1}].baseRateUpdateFrequency"><form:options/></form:select></td>
			<td><form:input class="loantypeprofilecell" disabled="false" path="loanTypeProfilesModels[${typeIndex.count-1}].loanTypeProfiles[${index.count-1}].daysBeforeDueToBill"/></td>
			<td><form:input class="loantypeprofilecell" disabled="false" path="loanTypeProfilesModels[${typeIndex.count-1}].loanTypeProfiles[${index.count-1}].daysLateForFee"/></td>
			<td><form:input class="loantypeprofilecell" disabled="false" path="loanTypeProfilesModels[${typeIndex.count-1}].loanTypeProfiles[${index.count-1}].graceMonths"/></td>
			<td><form:checkbox class="loantypeprofilecell" disabled="false" path="loanTypeProfilesModels[${typeIndex.count-1}].loanTypeProfiles[${index.count-1}].variableRate"/></td>
			<td><form:input class="loantypeprofilecell" disabled="false" path="loanTypeProfilesModels[${typeIndex.count-1}].loanTypeProfiles[${index.count-1}].lateFeeAmount"/></td>
			<td><form:input class="loantypeprofilecell" disabled="false" path="loanTypeProfilesModels[${typeIndex.count-1}].loanTypeProfiles[${index.count-1}].maximumLoanTerm"/></td>
			<td><form:input class="loantypeprofilecell" disabled="false" path="loanTypeProfilesModels[${typeIndex.count-1}].loanTypeProfiles[${index.count-1}].minDaysToFirstDue"/></td>
			<td><form:input class="loantypeprofilecell" disabled="false" path="loanTypeProfilesModels[${typeIndex.count-1}].loanTypeProfiles[${index.count-1}].prepaymentDays"/></td>
			<td><form:select path="loanTypeProfilesModels[${typeIndex.count-1}].loanTypeProfiles[${index.count-1}].repaymentStartType"><form:options/></form:select></td>
		</tr>
	</c:forEach>
</table>
<input type="submit" name="_eventId_addLoanTypeProfile" value="Add Loan Type Profile" />
</div>
</c:forEach>
</div>
<input type="submit" name="_eventId_save" value="save" />
	</form:form>

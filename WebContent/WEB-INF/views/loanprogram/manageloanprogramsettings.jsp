<%@ include file="/WEB-INF/layouts/includes.jsp" %>
<style>
	.dgrid {
		width: 100%;
		height: 100%;
	}
</style>
<script>
	require(["dojo/store/JsonRest", "dgrid/OnDemandGrid", "dgrid/Selection", "dojo/_base/declare", "dgrid/editor", "dijit/form/DateTextBox", "dojo/date/locale", "dojo/domReady!"],
		function(JsonRest, OnDemandGrid, Selection, declare, editor, DateTextBox, locale){
			
			var grid;
			var loanProgramSettingsStore = new JsonRest({target:"/openserv/web/loanprogram/loanprogramsettings.do"});
			loanProgramSettingsStore.query("?loanprogramid=${selectedLoanProgramId}").then(function(response){
				grid = declare([OnDemandGrid,Selection])({
					columns: [
						{label: "ID", field: "defaultLoanProgramSettingsID"},
						editor({label: "Effective Date", field:"effectiveDate",
							formatter: function(value){if(value){
								return locale.format(new Date(value), {datePattern: "yyyy-MM-dd", selector: "date"});
							}
							return '';
							},
							editOn:"dblclick",editorArgs:{required:true,constraints:{datePattern:"yyyy-MM-dd"}}},DateTextBox),
						editor({label: "End Date", field: "endDate", 
							formatter: function(value){if(value){
								return locale.format(new Date(value), {datePattern: "yyyy-MM-dd", selector: "date"});
							}
							return '';
							},
							editOn:"dblclick",editorArgs:{required:true,constraints:{datePattern:"yyyy-MM-dd"}}},DateTextBox)
					],
					selectionMode: "single"
				}, "grid");
				grid.renderArray(response.defaultLoanProgramSettingsList);
			});
		});
</script>
<div data-dojo-type="dijit/layout/BorderContainer">
	<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'top'">
		<h2>Default Loan Program Settings</h2>
	</div>
	<div data-dojo-type="dijit/layout/BorderContainer" data-dojo-props="region: 'center'">
		<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'center'">
			<div id="grid"></div>
		</div>
		<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'bottom', splitter: 'true'">
			<form id="settingsForm">
				<table>
					<tr>
						<td>Effective Date:</td>
						<td><input type="date" name="effectiveDate"/></td>
						<td>End Date:</td>
						<td><input type="date" name="endDate"/></td>
					</tr>
					<tr>
						<td>Maximum Loan Term:</td>
						<td><input type="number" name="maximumLoanTerm"/></td>
						<td>Grace Months:</td>
						<td><input type="number" name="graceMonths"/></td>
					</tr>
					<tr>
						<td>Min. Days To 1st Due:</td>
						<td><input type="number" name="minDaysToFirstDue"/></td>
						<td>Prepayment Days:</td>
						<td><input type="number" name="prepaymentDays"/></td>
					</tr>
					<tr>
						<td>Days Before Due To Bill:</td>
						<td><input type="number" name="daysBeforeDueToBill"/></td>
						<td>Days Late For Fee:</td>
						<td><input type="number" name="daysLateForFee"/></td>
						<td>Late Fee Amount:</td>
						<td><input type="number" name="lateFeeAmount"/></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'right'">
		<button id="addButton" data-dojo-type="dijit/form/Button" type="button">Add</button>
	</div>
	<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'bottom'">
		<button id="saveLoanProgramsButton" data-dojo-type="dijit/form/Button" type="button">Save</button>
		<button id="editButton" data-dojo-type="dijit/form/Button" type="button">Edit</button>
		<button id="closeButton" data-dojo-type="dijit/form/Button" type="button">Close</button>
	</div>
</div>	

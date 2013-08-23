<%@ include file="/WEB-INF/layouts/includes.jsp" %>
<style>
	.dgrid {
		width: 100%;
		height: 100%;
	}
</style>
<div data-dojo-type="dijit/layout/BorderContainer">
	<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'top'">
		<h2>Default Loan Program Settings</h2>
	</div>
	<div data-dojo-type="dijit/layout/BorderContainer" data-dojo-props="region: 'center'">
		<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'center'">
			<div id="grid"></div>
		</div>
		<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'bottom', splitter: 'true'">
			<form data-dojo-type="dojox.form.Manager" id="settingsForm">
				<input name="defaultLoanProgramSettingsID" type="hidden"/>
				<input name="loanProgramID" type="hidden"/>
				<table>
					<tr>
						<td>Effective Date:</td>
						<td><input id="effectiveDate" data-dojo-type="dijit/form/DateTextBox" data-dojo-props="constraints:{datePattern:'yyyy-MM-dd'}" type="date" name="effectiveDate"/></td>
						<td>End Date:</td>
						<td><input id="endDate" data-dojo-type="dijit/form/DateTextBox" data-dojo-props="constraints:{datePattern:'yyyy-MM-dd'}" type="date" name="endDate"/></td>
						<td>Base Rate Update Freq.:</td>
						<td><div id="baseRateUpdateFrequency"></div></td>
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
						<td><input data-dojo-type="dijit/form/CurrencyTextBox" data-dojo-props="constraints:{fractional:true},currency:'USD'" name="lateFeeAmount"/></td>
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
		<button id="closeButton" data-dojo-type="dijit/form/Button" type="button">Close</button>
	</div>
</div>	
<script>
	require(["dojo/store/JsonRest", "dgrid/OnDemandGrid", "dgrid/Selection", "dojo/_base/declare", "dgrid/editor", "dijit/form/DateTextBox", "dojo/date/locale", "dojo/on", "dojo/dom", "dojo/query", "dijit/registry", "dojo/parser", "dojo/_base/array", "dijit/form/Button", "dojo/store/Memory", "dijit/form/Select", "dojo/data/ObjectStore",  
			 "dijit/form/CurrencyTextBox", "dojox/form/Manager", "dojo/domReady!"],
		function(JsonRest, OnDemandGrid, Selection, declare, editor, DateTextBox, locale, on, dom, query, registry, parser, array, Button, Memory, Select, ObjectStore){
			var grid;
			var updating = false;
			var loanProgramSettingsStore = new JsonRest({target:"/openserv/web/loanprogram/loanprogramsettings.do"});
			var frequencyTypeStore = new JsonRest({target:"/openserv/web/loanprogram/loanprogramsettings/frequencytype"});
			
			new Button({
				label: "Save",
				onClick: function(){
					updateLoanSettings();
					console.log(grid.store.data);
					loanProgramSettingsStore.put(grid.store.data);
				}
			}, "saveLoanProgramsButton");
			
			new Button({
				label: "Add",
				onClick: function(){
					grid.store.add({
						defaultLoanProgramSettingsID:new Date().getMilliseconds()*(-1),
						loanProgramID:Number("${selectedLoanProgramId}")
					});
				}
			}, "addButton");
			
			var os;
			var store;
			frequencyTypeStore.query("").then(function(response){
				store = new Memory({
					data: response.frequencyTypeList
				});
				os = new ObjectStore({objectStore: store});
				var rateFreqSelect = new Select({
					store: os,
					name: "baseRateUpdateFrequency"
				}, "baseRateUpdateFrequency");
				rateFreqSelect.startup();
  			});

			loanProgramSettingsStore.query("?loanprogramid=${selectedLoanProgramId}").then(function(response){
				var settingsStore = new Memory({data: response.defaultLoanProgramSettingsModelList, idProperty: "defaultLoanProgramSettingsID"});
				grid = declare([OnDemandGrid,Selection])({
					store: settingsStore,
					columns: [
						{label: "Effective Date", field:"effectiveDate",
							formatter: function(value){
										if(value){
											return locale.format(new Date(value), {datePattern: "yyyy-MM-dd", selector: "date"});
										}
									return '';
								},
						},
						{label: "End Date", field: "endDate", 
							formatter: function(value){
										if(value){
											return locale.format(new Date(value), {datePattern: "yyyy-MM-dd", selector: "date"});
										}
									return '';
								},
						}
					],
					selectionMode: "single"
				}, "grid");
				grid.startup();
				grid.on("dgrid-select", function(event){
					var theForm = registry.byId("settingsForm");
					theForm.setFormValues(event.rows[0].data);
					for(var field in event.rows[0].data){
						if(field.indexOf('Date') > -1 && event.rows[0].data[field]){
							var dateBox = registry.byId(field);
							dateBox.set("value", new Date(event.rows[0].data[field]));
						}
 						else if(field == 'baseRateUpdateFrequency'){
							var baseRateBox = registry.byId(field);
							baseRateBox.set("value", event.rows[0].data[field]);
						}
					}
				});
				grid.on("dgrid-deselect", function(event){
					if(!updating){
						updating = true;
						updateLoanSettings();
						updating = false;
					}
				});
			});
			
			function updateLoanSettings(){
				var theForm = registry.byId("settingsForm");
				var theData = theForm.gatherFormValues();
				theData["baseRateUpdateFrequency"] = registry.byId("baseRateUpdateFrequency").get("value");
				grid.store.put(theData);
				grid.refresh();
			}
		});
</script>

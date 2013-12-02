var grid;
var loanPhaseObjStore;
var loanPhaseMemStore;
var repaymentPlanTypeObjStore;
var repaymentPlanTypeMemStore;

require([ "dijit/form/Button", "dojo/store/JsonRest", "dojo/store/Memory",
				"dgrid/OnDemandGrid", "dojo/_base/declare",
				"dgrid/extensions/ColumnResizer", "dgrid/editor",
				"dojo/data/ObjectStore", "dijit/form/Select",
				"dgrid/Selection", "dijit/registry", "dojo/_base/lang", "dijit/layout/StackContainer",
				"dijit/form/NumberTextBox", "dijit/form/CurrencyTextBox", "dijit/form/Form"],
		function(Button, JsonRest, Memory, OnDemandGrid, declare,
				ColumnResizer, editor, ObjectStore, Select, Selection, registry, lang) {
			var repaymentPlanStore = new JsonRest({target : "/openserv/web/loanprogram/repaymentplans"});
			var loanPhaseStore = new JsonRest({target : "/openserv/web/loanprogram/repaymentplans/loanphaseevent"});
			var repaymentPlanTypeStore = new JsonRest({target : "/openserv/web/loanprogram/repaymentplans/repaymentplantype"});
			var frequencyTypeStore = new JsonRest({target:"/openserv/web/loanprogram/loanprogramsettings/frequencytype"});

			loanPhaseStore.get("").then(function(response) {
				loanPhaseMemStore = new Memory({
					data : response.loanPhaseEventList,
					idProperty : "id"
				});
				loanPhaseObjStore = new ObjectStore({
					objectStore : loanPhaseMemStore,
					labelProperty : "label"
				});
				var planStartDateSelect = new Select({
					store : loanPhaseObjStore,
					name : "planStartDateID"
				}, "planStartDateSelect");
				planStartDateSelect.startup();
			});

			repaymentPlanTypeStore.get("").then(function(response) {
				repaymentPlanTypeMemStore = new Memory({
					data : response.repaymentPlanTypeList,
					idProperty : "id"
				});
				repaymentPlanTypeObjStore = new ObjectStore({
					objectStore : repaymentPlanTypeMemStore,
					labelProperty : "label"
				});
				var repaymentPlanTypeSelect = new Select({
					store : repaymentPlanTypeObjStore,
					name : "planTypeID",
					onChange : function(newValue){
						var planDetailContainer = registry.byId("planDetailContainer");
						if(newValue == 10)
							planDetailContainer.selectChild("standardPlanDetail",true);
						else if(newValue == 20)
							planDetailContainer.selectChild("fixedPlanDetail",true);
					}
				}, "repaymentPlanTypeSelect");
				repaymentPlanTypeSelect.startup();
			});
			
			frequencyTypeStore.query("").then(function(response){
				var store = new Memory({
					data: response.frequencyTypeList
				});
				var os = new ObjectStore({objectStore: store});
				var capFrequencySelect = new Select({
					store: os,
					name: "capFrequencyID"
				}, "capFrequencySelect");
				capFrequencySelect.startup();
  			});

			var settingsIdField = document.getElementById("defaultLoanProgramSettingsID");
			var settingsId = settingsIdField.value;
			
			repaymentPlanStore.query("?loansettingsid="+settingsId).then(function(response) {
				var theStore = new Memory({
					data : response.repaymentPlanModelList,
					idProperty : "repaymentPlanID"
				});
				grid = declare([ OnDemandGrid, ColumnResizer,Selection ])
					({
						store : theStore,
						columns : [{
							id : "loanPhase",
							label : "Loan Phase",
							field : "planStartDateID",
							formatter : function(value) {
								if (value)
									return loanPhaseMemStore.get(value).label;
								return loanPhaseMemStore.get(10).label;
							}
						},
						{
							id : "type",
							label : "Plan Type",
							field : "planTypeID",
							formatter : function(value) {
								if (value)
									return repaymentPlanTypeMemStore.get(value).label;
								return repaymentPlanTypeMemStore.get(10).label;
							}
						} ],
						selectionMode : "single"
				}, "grid");
				grid.startup();
				grid.on("dgrid-select", function(event){
					var data = event.rows[0].data;
					updateScreen(data);
				});
				grid.on("dgrid-deselect", function(event){
					updateData();
				});
			});
			
			function updateScreen(data){
				var theForm = registry.byId("settingsForm");
				theForm.set('value', data);
				var theStandardForm = registry.byId("standardSettingsForm");
				theStandardForm.set('value', data);
				var theFixedForm = registry.byId("fixedSettingsForm");
				theFixedForm.set('value', data);
			}
			
			function updateData(){
				var theForm = registry.byId("settingsForm");
				var theData = theForm.get('value');
				if (theData.planTypeID == 10) {
					var theStandardForm = registry.byId("standardSettingsForm");
					var theStandardData = theStandardForm.get('value');
					lang.mixin(theData, theStandardData);
				}
				if(theData.planTypeID == 20){
					var theFixedForm = registry.byId("fixedSettingsForm");
					var theFixedData = theFixedForm.get('value');
					lang.mixin(theData, theFixedData);
				}
				grid.store.put(theData);
				grid.refresh();
			}

			new Button({
				onClick : function() {
					updateData();
					when(repaymentPlanStore.put(grid.store.data), function(){
						alert("Update Successful");
					});
				}
			}, "saveButton");
			new Button({
				onClick : function() {
					grid.store.add({
						repaymentPlanID : new Date().getMilliseconds() * (-1),
						planStartDateID : 10,
						planTypeID : 10,
						graceMonths : 0,
						maxLoanTerm : 0,
						minPaymentAmount : 0
					});
					grid.refresh();
				}
			}, "addButton");
		});
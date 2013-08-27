<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/WEB-INF/layouts/includes.jsp" %>
<style>
	#scheduleGrid{
		width: 100%;
		height: 100%;
		min-height: 8em;
	}
	#eventGrid{
		width: 100%;
		height: 95%;
	}
</style>
<div data-dojo-type="dijit/layout/BorderContainer">
	<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'top'">
		<h2>Due Diligence Schedules</h2>
	</div>
	<div data-dojo-type="dijit/layout/BorderContainer" data-dojo-props="region: 'center'">
		<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'top', splitter: 'true'">
			<div id="scheduleGrid"></div>
		</div>
		<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'center'">
			Effective Date:
			<input id="effectiveDate" data-dojo-type="dijit/form/DateTextBox" data-dojo-props="constraints:{datePattern:'yyyy-MM-dd'}" type="date" name="effectiveDate"/>
			<div id="eventGrid"></div>
		</div>
		<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'right'">
			<button id="addEventButton" data-dojo-type="dijit/form/Button" type="button">Add Event</button>
			<button id="eventTypesButton" data-dojo-type="dijit/form/Button" type="button">Event Types</button>
		</div>
	</div>
	<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'right'">
		<button id="addScheduleButton" data-dojo-type="dijit/form/Button" type="button">Add Schedule</button>
	</div>
	<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'bottom'">
		<button id="saveButton" data-dojo-type="dijit/form/Button" type="button">Save</button>
	</div>
</div>
<script type="text/javascript">
	require(["dojo/store/JsonRest", "dojo/store/Memory", "dojo/data/ObjectStore", "dojox/rpc/Rest", "dojo/_base/declare", "dgrid/OnDemandGrid", "dgrid/Selection", "dojo/date/locale", "dijit/registry", "dijit/form/NumberSpinner", "dgrid/editor", "dijit/form/Select", "dijit/form/Button", "dojo/when",   
			"dijit/form/DateTextBox", "dijit/form/Button", "dojo/domReady!"],
		function(JsonRest, Memory, ObjectStore, Rest, declare, OnDemandGrid, Selection, locale, registry, NumberSpinner, editor, Select, Button, when){
			var scheduleGrid;
			var eventGrid;
			var scheduleStore = new JsonRest({target:"/openserv/web/duediligence/duediligenceschedules"});
			var eventTypeRestStore = new JsonRest({target:"/openserv/web/duediligence/duediligenceeventtypes"});
			var eventTypeStore;
			var eventTypeMemStore;
			var selectedSchedule;
			var eventMemStore = new Memory({data: 
							{
							dueDiligenceEventID: 1, 
							dueDiligenceEventTypeId: 20, 
							minDelqDays: 10,
							maxDelqDays: 20,
							defaultDelqDays: 30
							}, idProperty: "dueDiligenceEventID"});

			new Button({
				label: "Add Event",
				onClick: function(){
					if(selectedSchedule){
 						var newEvent = {};
 						newEvent["dueDiligenceEventID"] = new Date().getMilliseconds()*(-1);;
						newEvent["dueDiligenceEventTypeId"] = 10;
						newEvent["minDelqDays"] = 0;
						newEvent["maxDelqDays"] = 0;
						newEvent["defaultDelqDays"] = 0;
						if(!selectedSchedule.events)
							selectedSchedule["events"] = [];
						selectedSchedule.events.push(newEvent);
						eventGrid.refresh();
					}
				}
			}, "addEventButton");
			
			new Button({
				label: "Event Types",
				onClick: function(){
					window.location = "${flowExecutionUrl}&_eventId=duediligenceeventtypes";
				}
			},"eventTypesButton");
			
			new Button({
				label: "Add Schedule",
				onClick: function(){
 						var newSchedule = {
							dueDiligenceScheduleID: new Date().getMilliseconds()*(-1),
							loanProgramID: Number("${selectedLoanProgramId}"),
							effectiveDate: new Date(),
							events: []
 						};
 						var newEvent = {};
 						newEvent["dueDiligenceEventID"] = new Date().getMilliseconds()*(-1);;
						newEvent["dueDiligenceEventTypeId"] = 10;
						newEvent["minDelqDays"] = 0;
						newEvent["maxDelqDays"] = 0;
						newEvent["defaultDelqDays"] = 0;
						newSchedule.events.push(newEvent);
 						scheduleGrid.store.add(newSchedule);
						scheduleGrid.refresh();
					}
			}, "addScheduleButton");

			new Button({
				label: "Save",
				onClick: function(){
					if(selectedSchedule){
						var dateBox = registry.byId("effectiveDate");
						var date = dateBox.get("value");
						selectedSchedule.effectiveDate = date;
					}
					when(scheduleStore.put(scheduleGrid.store.data), function(){
 						alert("Update Successful");
					});
				}
			}, "saveButton");
	
			eventTypeRestStore.get("").then(function(response){
					var eventTypeList = response.dueDiligenceEventTypeModelList;
					eventTypeMemStore = new Memory({data:eventTypeList,idProperty: "dueDiligenceEventTypeID"});
					eventTypeStore = new ObjectStore({
						objectStore: eventTypeMemStore,
						labelProperty: "name"
					});
			eventGrid = declare([OnDemandGrid, Selection])({
				store: eventMemStore,
				columns: [
					editor({id: "type", label: "Event Type", field: "dueDiligenceEventTypeId", 
						formatter: function(value){
							return eventTypeMemStore.get(value).name;
						}, 
						editorArgs: {store: eventTypeStore}, autoSave: true}, Select, "dblclick"),
					
					editor({id: "min", label: "Min", field: "minDelqDays", autoSave: true}, NumberSpinner, "dblclick"),
					editor({id: "max", label: "Max", field: "maxDelqDays", autoSave: true}, NumberSpinner, "dblclick"),
					editor({id: "default", label: "Default", field: "defaultDelqDays", autoSave: true}, NumberSpinner, "dblclick")
				]
			}, "eventGrid");
			eventGrid.startup();
				});
			
			scheduleStore.query("?loanprogramid=${selectedLoanProgramId}").then(function(response){
				var scheduleGridStore = new Memory({data: response.dueDiligenceScheduleModelList, idProperty: "dueDiligenceScheduleID"});
				scheduleGrid = declare([OnDemandGrid, Selection])({
					store: scheduleGridStore,
					columns: [
						{label: "Effective Date", field:"effectiveDate",
							formatter: function(value){
										if(value){
											return locale.format(new Date(value), {datePattern: "yyyy-MM-dd", selector: "date"});
										}
									return '';
								},
						}
					],
					selectionMode: "single"
				},"scheduleGrid");
				scheduleGrid.startup();
				scheduleGrid.on("dgrid-select", function(event){
					selectedSchedule = event.rows[0].data;
					var dateBox = registry.byId("effectiveDate");
					dateBox.set("value", new Date(selectedSchedule["effectiveDate"]));
					eventMemStore.setData(selectedSchedule.events);
					eventGrid.refresh();
				});
			});
		}
	);
</script>
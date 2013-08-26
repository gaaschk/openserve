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
	</div>
	<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'right'">
		<button id="addScheduleButton" data-dojo-type="dijit/form/Button" type="button">Add Schedule</button>
	</div>
	<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'bottom'">
		<button id="saveButton" data-dojo-type="dijit/form/Button" type="button">Save</button>
	</div>
</div>
<script type="text/javascript">
	require(["dojo/store/JsonRest", "dojo/store/Memory", "dojo/data/ObjectStore", "dojox/rpc/Rest", "dojo/_base/declare", "dgrid/OnDemandGrid", "dgrid/Selection", "dojo/date/locale", "dijit/registry", "dijit/form/NumberSpinner", "dgrid/editor", "dijit/form/Select", "dijit/form/Button",   
			"dijit/form/DateTextBox", "dijit/form/Button", "dojo/domReady!"],
		function(JsonRest, Memory, ObjectStore, Rest, declare, OnDemandGrid, Selection, locale, registry, NumberSpinner, editor, Select, Button){
			var scheduleGrid;
			var scheduleStore = new JsonRest({target:"/openserv/web/duediligence/duediligenceschedules"});
			var scheduleService = new Rest("/openserv/web/duediligence/duediligenceschedules");
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
				label: "Save",
				onClick: function(){
					if(selectedSchedule){
						var dateBox = registry.byId("effectiveDate");
						selectedSchedule.effectiveDate = dateBox.get("value");
						var eventStore = eventMemStore;
						console.log("Event Store: " + eventStore);
						selectedSchedule.events = eventStore.data;
						console.log("Event Store data: " + eventStore.data);
						console.log("Updated Row: " + JSON.stringify(selectedSchedule));
						scheduleGrid.store.put(selectedSchedule);
					}
					scheduleStore.put(scheduleGrid.store.data);
					alert("Update Successful");
				}
			}, "saveButton");
	
			eventTypeRestStore.get("").then(function(response){
					var eventTypeList = response.dueDiligenceEventTypeModelList;
					eventTypeMemStore = new Memory({data:eventTypeList,idProperty: "dueDiligenceEventTypeID"});
					eventTypeStore = new ObjectStore({
						objectStore: eventTypeMemStore,
						labelProperty: "name"
				});
				
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
					console.log("select event");
					var dateBox = registry.byId("effectiveDate");
					console.log(selectedSchedule);
					dateBox.set("value", new Date(selectedSchedule["effectiveDate"]));
					eventMemStore.setData(selectedSchedule.events);
					eventGrid.refresh();
					console.log("event grid data on select: " + JSON.stringify(eventGrid.store.data));
				});
				var eventGrid = declare([OnDemandGrid, Selection])({
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
		}
	);
</script>
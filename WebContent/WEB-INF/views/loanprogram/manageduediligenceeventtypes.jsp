<%@ include file="/WEB-INF/layouts/includes.jsp" %>
<style>
	.dgrid {
		width: 100%;
		height: 100%;
	}
	
	.dgrid-column-lpid{
		width: 30px;
	}

	.dgrid-column-name{
		width: 20%;
	}
	
	.dgrid-column-desc{
		width: auto;
	}
		
</style>
<div data-dojo-type="dijit/layout/BorderContainer">
	<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'top'">
		<h2>Due Diligence Event Types</h2>
		double click to edit
	</div>
	<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'center'">
		<div id="grid"></div>
	</div>
	<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'right'">
		<table>
			<tr>
				<td><button id="addButton" data-dojo-type="dijit/form/Button" type="button">Add</button></td>
			</tr>
		</table>
	</div>
	<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'bottom'">
		<button id="saveButton" data-dojo-type="dijit/form/Button" type="button">Save</button>
		<button id="closeButton" data-dojo-type="dijit/form/Button" type="button">Close</button>
	</div>
</div>	
<script>
	require(["dgrid/OnDemandGrid", "dgrid/Selection", "dojo/_base/declare", "dojo/store/JsonRest", "dojo/store/Memory", "dojo/store/Cache", "dojo/store/Observable", 
	         "dgrid/editor", "dojo/dom", "dojo/on", "dojo/parser", "dojo/_base/connect", "dijit/registry", "dijit/form/Button", "dgrid/extensions/ColumnResizer", "dojo/data/ObjectStore", "dojo/domReady!"],
		function(OnDemandGrid, Selection, declare, JsonRest, Memory, Cache, Observable, editor, dom, on, parser, connect, registry, Button, ColumnResizer, ObjectStore){
			var eventTypeStore = new JsonRest({target:"/openserv/web/duediligence/duediligenceeventtypes"});
			var grid;
			var theStore;
			eventTypeStore.get("").then(function(response){
				console.log(response.dueDiligenceEventTypeModelList);
				theStore = new Memory({
						idProperty: "dueDiligenceEventTypeID",
						data: response.dueDiligenceEventTypeModelList
					});
				grid = declare([OnDemandGrid, Selection, ColumnResizer])({
					store: theStore,
					columns: [
						editor({id: "name", label: "Name", field: "name", autoSave: true}, "text", "dblclick"),
						editor({id: "desc", label: "Description", field: "description", autoSave: true}, "text", "dblclick")
					],
					selectionMode: "single"
				},"grid");
				grid.startup();
				grid.on("dgrid-select", function(event){
					selectedRow = event.rows[0];
				});
			});
			
			new Button({
				label: "Save",
				onClick: function(){
					eventTypeStore.put(grid.store.data);
					alert("Update Successful");
				}
			}, "saveButton");
			
			new Button({
				label: "Close",
				onClick: function(){
					window.location = "${flowExecutionUrl}&_eventId=closeDueDiligenceTypes";
				}
			}, "closeButton");
			
			new Button({
				label: "Add",
				onClick: function(){
					grid.store.add({
						dueDiligenceEventTypeID:new Date().getMilliseconds()*(-1),
						name:'',
						description:''
						});
					grid.refresh();
				}
			},"addButton");
	});
</script>

	
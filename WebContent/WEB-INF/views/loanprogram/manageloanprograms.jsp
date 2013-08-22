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
<script>
	require(["dgrid/OnDemandGrid", "dgrid/Selection", "dojo/_base/declare", "dojo/store/JsonRest", "dojo/store/Memory", "dojo/store/Cache", "dojo/store/Observable", 
	         "dgrid/editor", "dojo/dom", "dojo/on", "dojo/parser", "dojo/_base/connect", "dijit/registry", "dijit/form/Button", "dgrid/extensions/ColumnResizer", "dojo/domReady!"],
		function(OnDemandGrid, Selection, declare, JsonRest, Memory, Cache, Observable, editor, dom, on, parser, connect, registry, Button, ColumnResizer){
			var loanProgramStore = new JsonRest({target:"/openserv/web/loanprogram/allloanprograms.do"});
			var loanProgramSet;
			var grid;
			var selectedRow;
			loanProgramStore.get("").then(function(response){
				grid = declare([OnDemandGrid, Selection, ColumnResizer])({
					columns: [
						{id: "lpid", label: "ID", field: "loanProgramID"},
						editor({id: "name", label: "Name", field: "name"}, "text", "dblclick"),
						editor({id: "desc", label: "Description", field: "description", }, "text", "dblclick")
					],
					selectionMode: "single"
					
				},"grid");
				grid.renderArray(response.loanprograms.loanProgramModelList);
				loanProgramSet = response;
				grid.on("dgrid-select", function(event){
					selectedRow = event.rows[0];
				});
			});
			
			new Button({
				label: "Save",
				onClick: function(){
					loanProgramStore.put(loanProgramSet.loanprograms);
				}
			}, "saveLoanProgramsButton");
			
			new Button({
				label: "Edit Settings",
				onClick: function(){
					window.location = "${flowExecutionUrl}&_eventId=editSettings&loanprogramid="+selectedRow.data.loanProgramID;
				}
			}, "editButton");

			new Button({
				label: "Close",
				onClick: function(){
					window.location = "/openserv/web/home/home.do";
				}
			}, "closeButton");
	});
</script>
<div data-dojo-type="dijit/layout/BorderContainer">
	<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'top'">
		<h2>Loan Programs</h2>
		double click to edit
	</div>
	<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'center'">
		<div id="grid"></div>
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

	
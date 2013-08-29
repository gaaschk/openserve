<%@ include file="/WEB-INF/layouts/includes.jsp" %>
<style>
	.dgrid {
		width: 100%;
		height: 100%;
	}
	
	.dgrid-column-name{
		width: 20%;
	}
	
	.dgrid-column-desc{
		width: auto;
	}
		
</style>
<div data-dojo-type="dijit/layout/BorderContainer">
	<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'right'">
		<button id="addButton" data-dojo-type="dijit/form/Button" type="button">Add</button>	
	</div>
	<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'center'">
		<div id="grid"></div>
	</div>
	<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'bottom'">
		<button id="saveButton" data-dojo-type="dijit/form/Button" type="button">Save</button>	
		<button id="closeButton" data-dojo-type="dijit/form/Button" type="button">Close</button>	
	</div>
</div>
<script type="text/javascript">
	var grid;
	require(["dijit/form/Button", "dojo/store/JsonRest", "dojo/store/Memory", "dgrid/OnDemandGrid", "dojo/_base/declare", "dgrid/extensions/ColumnResizer", "dgrid/editor"], 
			function(Button, JsonRest, Memory, OnDemandGrid, declare, ColumnResizer, editor){
		var repaymentPlanStore = new JsonRest({target:"/openserv/web/loanprogram/repaymentplans"});
		
		repaymentPlanStore.get("").then(function(response){
			var theStore = new Memory({
				idProperty: "repaymentPlanSettingsID",
				data: response.repaymentPlanSettingsModelList
			});
			grid = declare([OnDemandGrid, ColumnResizer])({
				store: theStore,
				columns:[
					editor({id: "name", label: "Name", field: "name"}, "text", "dblclick"),
					editor({id: "desc", label: "Description", field: "description", }, "text", "dblclick")
				]
			}, "grid");
		});

		new Button({
			onClick: function(){
				repaymentPlanStore.put(grid.store.data);
				alert("Update Successful");
			}
		},"saveButton");
		new Button({
			onClick: function(){
				window.location = "${flowExecutionUrl}&_eventId=closeRepaymentPlans";
			}
		},"closeButton");
		new Button({
			onClick: function(){
				grid.store.add({
					repaymentPlanSettingsID:new Date().getMilliseconds()*(-1),
					name:'<name>',
					description:'<description>'
					});
				grid.refresh();
			}
		},"addButton");
	});
</script>
<%@ include file="/WEB-INF/layouts/includes.jsp" %>
<style>
	.dgrid-column-desc{
		width: auto;
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
<div id="grid"></div>

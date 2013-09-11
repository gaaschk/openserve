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
	<div data-dojo-type="dijit/layout/BorderContainer" data-dojo-props="region:'center'">
		<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'top'">
			<div id="grid" style="min-height: 6em"></div>
		</div>
		<div data-dojo-type="dijit/layout/BorderContainer" data-dojo-props="region:'center'">
			<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'top'">
				<form data-dojo-type="dijit/form/Form" id="settingsForm">
					<input id="repaymentPlanID" type="hidden" data-dojo-type="dijit/form/TextBox" name="repaymentPlanID"/>
					<input id="defaultLoanProgramSettingsID" type="hidden" data-dojo-type="dijit/form/TextBox" name="defaultLoanProgramSettingsID" value="${selectedDefaultLoanSettingsId}"/>
					<table><tr>
						<td>Plan Type:</td>
						<td><div id="repaymentPlanTypeSelect"></div></td>
					</tr><tr>
						<td>Start Date:</td>
						<td><div id="planStartDateSelect"></div></td>
					</tr>
					<tr>
						<td>Grace Months:</td>
						<td><input id="graceMonths" data-dojo-type="dijit/form/NumberTextBox" type="number" name="graceMonths"/></td>
					</tr></table>
				</form>
			</div>
			<div id="planDetailContainer" data-dojo-type="dijit/layout/StackContainer" data-dojo-id="planDetailContainer" data-dojo-props="region:'center'">
				<div id="standardPlanDetail" data-dojo-type="dijit/layout/ContentPane">
					<form data-dojo-type="dijit/form/Form" id="standardSettingsForm">
						<table>
							<tr>
								<td>Maximum Loan Term:</td>
								<td><input id="maxLoanTerm" data-dojo-type="dijit/form/NumberTextBox" type="number" name="maxLoanTerm"/></td>
							</tr>
							<tr>
								<td>Minimum Payment Amount:</td>
								<td><input id="minPaymentAmount" data-dojo-type="dijit/form/CurrencyTextBox" data-dojo-props="constraints:{fractional:true},currency:'USD'" name="minPaymentAmount"/></td>
							</tr>
						</table>
					</form>
				</div>
				<div id="fixedPlanDetail" data-dojo-type="dijit/layout/ContentPane">
					<form data-dojo-type="dijit/form/Form" id="fixedSettingsForm">
						<table>
							<tr>
								<td>Payment Amount:</td>
								<td><input id="paymentAmount" data-dojo-type="dijit/form/CurrencyTextBox" data-dojo-props="constraints:{fractional:true},currency:'USD'" name="paymentAmount"/></td>
							</tr>
							<tr>
								<td>Cap. Frequency:</td>
								<td><div id="capFrequencySelect"></div></td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'right'">
		<button id="addButton" data-dojo-type="dijit/form/Button" type="button">Add</button>	
	</div>
	<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'bottom'">
		<button id="saveButton" data-dojo-type="dijit/form/Button" type="button">Save</button>	
		<button id="closeButton" data-dojo-type="dijit/form/Button" type="button">Close</button>	
	</div>
</div>
<script src="${jsUrl}/openservweb/loanprogram/js/managerepaymentplans.js" type="text/javascript"></script>
<script type="text/javascript">
	require(["dijit/form/Button"],function(Button){
		new Button({
			onClick : function() {
				window.location = "${flowExecutionUrl}&_eventId=closeRepaymentPlans";
			}
		}, "closeButton");
	});
</script>
<%@ include file="/WEB-INF/layouts/includes.jsp" %>
<style type="text/css">
table.loans{
	border-width: thin;
	border-style: solid;
	caption-side: top;
}
table.loans caption{
	align: right;
	font-weight: bolder;
	font-size: medium;
}
table.loans tr td input{
	text-align: right;
}
table.loans caption label{
	text-align: left;
}
</style>
<!-- 
 -->
<script type="text/javascript">
	require([
	        'dojo/parser',
			'dojo/request',
			'dojo/_base/array',
			'dojo/dom',
			'dojo/on',
			'dojo/query',
			'dijit/MenuBar', 
			'dijit/MenuBarItem', 
			'dijit/PopupMenuBarItem',
	 		'dijit/DropDownMenu', 
	 		'dijit/MenuItem', 
	 		'dijit/layout/BorderContainer', 
	 		'dijit/layout/TabContainer',
	     	'dijit/layout/ContentPane', 
	     	'dijit/form/TextBox', 
	     	'dijit/form/Button',
	     	'dijit/form/RadioButton',
	     	'dijit/_base/manager',
			'dojo/domReady!'], 
	function(parser, request, array, dom, on, query){
		var isClicked = false;
		array.forEach(query(".loanrowrb"), function(loan){
			if(!isClicked){
				loan.checked = true;
				loadAccount(loan.id, request);
				isClicked = true;
			}
			on(loan, "click", function(event){
				loadAccount(this.id, request);
			});
		});
	});
	
	function loadAccount(loanid, request){
		var documenturl = new URI(document.location.href);
		var urlBase = documenturl.get('scheme')+'://'+documenturl.get('host')+':'+documenturl.get('port')+documenturl.get('directory');
		var url = urlBase+'accountsummary/loanfinancialdata.do?loandetailid='+loanid;
		request.get(url).then(
			function(response){
				document.getElementById("loanFinancialPane").innerHTML = response;
			}		
		);
		var url = urlBase+'accountsummary/transactionhistory.do?loandetailid='+loanid;
		request.get(url).then(
			function(response){
				document.getElementById("transactionHistoryPane").innerHTML = response;
			}		
		);
		var url = urlBase+'accountsummary/amortizationschedule.do?loandetailid='+loanid;
		request.get(url).then(
			function(response){
				document.getElementById("amortizationPane").innerHTML = response;
			}		
		);
		var url = urlBase+'accountsummary/billingstatements.do?loandetailid='+loanid;
		request.get(url).then(
			function(response){
				document.getElementById("billingStatementPane").innerHTML = response;
			}		
		);
	}
</script>
<div data-dojo-type="dijit/layout/BorderContainer" style="width: 100%; height: 100%">
		<form:form commandName="accountSummaryModel">
			<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'top'">
				<table>
					<tr>
						<td><strong>SSN:</strong></td>
						<td><c:out value="${accountSummaryModel.borrower.ssn}"/></td>
					</tr>
					<tr>
						<td><strong>Name:</strong></td>
						<td><c:out value="${accountSummaryModel.borrower.firstName}"/><span>  </span><c:out value="${accountSummaryModel.borrower.lastName}"/></td>
					</tr>
				</table>
			</div>
			<div data-dojo-type="dijit/layout/TabContainer" data-dojo-props="region:'center'">
				<div data-dojo-type="dijit/layout/BorderContainer" title="Loan Information" id="loanDetailPane">
					<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'top', splitter: 'true'" style="height: 2cm">
						<table class="loans" id="loantable">
							<col style="width: 30px;"/>
							<caption>Loans</caption>
							<tr>
								<th></th>
								<th><label>Account Number</label></th>
								<th><label>Loan Type</label></th>
								<th><label>Current Principal</label></th>
								<th><label>Current Interest</label></th>
								<th><label>Current Fees</label></th>
							</tr>
							<c:forEach var="loan" items="${accountSummaryModel.loans}" varStatus="index">
								<tr class="loanrow" >
									<td><input type="radio" class="loanrowrb" id="${loan.loanID}" value="${loan.loanID}" name="loanrowrb"></td>
									<td><form:input class="loancell" disabled="true" path="loans[${index.count-1}].accountNumber"/></td>
									<td><form:input class="loancell" disabled="true" path="loans[${index.count-1}].loanProgram"/></td>
									<td><form:input class="loancell" disabled="true" path="loans[${index.count-1}].currentPrincipal"/></td>
									<td><form:input class="loancell" disabled="true" path="loans[${index.count-1}].currentInterest"/></td>
									<td><form:input class="loancell" disabled="true" path="loans[${index.count-1}].currentFees"/></td>
								</tr>
							</c:forEach>
						</table>
					</div>
					<div id="loanDetail" data-dojo-type="dijit/layout/TabContainer" data-dojo-props="region:'center'">
						<div id="loanFinancialPane" data-dojo-type="dijit/layout/ContentPane" title="Loan Detail">
						</div>
						<div id="transactionHistoryPane" data-dojo-type="dijit/layout/ContentPane" title="Transaction History">
						</div>
						<div id="amortizationPane" data-dojo-type="dijit/layout/ContentPane" title="Amortization Schedule">
						</div>
						<div id="billingStatementPane" data-dojo-type="dijit/layout/ContentPane" title="Billing Statements">
						</div>
					</div>
				</div>
				<div data-dojo-type="dijit/layout/ContentPane" title="Payment Information" id="paymentHistoryPane">
			  		<jsp:include page="payments.jsp"/>
				</div>
			</div>
		</form:form>
</div>

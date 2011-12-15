<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<style type="text/css">
table.loans{
	border-width: thin;
	border-style: inset;
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

<script type="text/javascript">
window.addEvent('domready', function() {
 
	var loans = $$("#loantable .loanrowrb");
	loans.each(function(loan, i) {
		loan.addEvent("click", function(event) {
			var selectedloans = $$("#loantable .loanrowrb")
			for(var idx = 0; idx < selectedloans.length; idx++){
				if(selectedloans[idx] != this)
					selectedloans[idx].checked = false;
			}
			var documenturl = new URI(document.location.href);
			var url = documenturl.get('scheme')+'://'+documenturl.get('host')+':'+documenturl.get('port')+documenturl.get('directory')+'loandetail.do?loandetailid='+this.id;
			new Request.HTML({
				url: url,
				method: 'get',
				update: 'loanDetail',
				evalScripts: true /* this is the default */
				}).send();
		});
	});
});
</script>
<div class="panel">
<form:form commandName="accountmodel">
<table>
	<tr>
		<td colspan="2">
			<fieldset>
				<label>SSN:</label>
				<c:out value="${accountmodel.borrower.ssn}"/>
			</fieldset>
		</td>
	</tr>
	<tr>
		<td>
			<fieldset>
				<label>First Name:</label>
				<c:out value="${accountmodel.borrower.firstName}"/>
			</fieldset>
		</td>
		<td>
			<fieldset>
				<label>Last Name:</label>
				<c:out value="${accountmodel.borrower.lastName}"/>
			</fieldset>
		</td>
	</tr>
</table>
<table class="loans" id="loantable">
	<caption><label>Loans</label></caption>
	<tr>
		<th><label></label></th>
		<th><label>Loan Type</label></th>
		<th><label>Current Principal</label></th>
		<th><label>Current Interest</label></th>
		<th><label>Current Fees</label></th>
	</tr>
	<c:forEach var="loan" items="${accountmodel.loans}" varStatus="index">
		<tr class="loanrow" >
			<td><input type="radio" class="loanrowrb" id="${loan.loanID}"/></td>
			<td><form:input class="loancell" disabled="true" path="loans[${index.count-1}].loanType"/></td>
			<td><form:input class="loancell" disabled="true" path="loans[${index.count-1}].currentPrincipal"/></td>
			<td><form:input class="loancell" disabled="true" path="loans[${index.count-1}].currentInterest"/></td>
			<td><form:input class="loancell" disabled="true" path="loans[${index.count-1}].currentFees"/></td>
		</tr>
	</c:forEach>
</table>
</form:form>
<div id="loanDetail">
	<p>Select a loan for detail</p>	
</div>
</div>

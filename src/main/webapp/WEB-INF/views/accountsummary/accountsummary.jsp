<%@ include file="/WEB-INF/views/includes.jsp" %>
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
			var selectedloans = $$("#loantable .loanrowrb");
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
	
	initAccountTabs();
});

function initAccountTabs() { $$('#accounttabs a').each(function(el) { 
  el.addEvent('click',function(e) { 
    var ev = new Event(e).stop(); 
      tabState(el);
      /*load appropriate pane here*/ 
      if(this == $('loaninfotab')){
      	$('paymentHistoryPane').fancyHide();
      	$('loanDetailPane').fancyShow();
      }
      else if(this == $('paymenttab')){
      	$('loanDetailPane').fancyHide();
      	$('paymentHistoryPane').fancyShow();
      }
    });
  });
  $('paymentHistoryPane').fancyHide();
  $('loanDetailPane').fancyShow();
} 

function tabState(ael) { 
  $$('.tab-menu a').each(function(el) { 
    if(el.hasClass('active')) { 
      el.removeClass('active'); 
      } 
    }); 
  ael.addClass('active'); 
} 

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
<div class="panel">
	<ul class="tab-menu" id="accounttabs">
  	  <li><a href="#" class="active" id="loaninfotab"><span>Loan Information</span></a></li>
      <li><a href="#" id="paymenttab"><span>Payment History</span></a></li>
    </ul>
</div>
<div class="panel" id="loanDetailPane">
<table class="loans" id="loantable">
	<col style="width: 30px;"/>
	<caption><label>Loans</label></caption>
	<tr>
		<th></th>
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
  <div id="loanDetail">
  </div>
</div>
</form:form>
<div class="panel" id="paymentHistoryPane">
  <jsp:include page="payments.jsp"/>
</div>
</div>

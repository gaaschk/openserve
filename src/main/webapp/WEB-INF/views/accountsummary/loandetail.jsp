<%@ include file="/WEB-INF/layouts/includes.jsp" %>
<style type="text/css">
#tabContainer{ 
  position:relative; 
  width:700px;
  border-style: inset;
  border-width: thin;
}
</style>
<script type="text/javascript"> 
window.addEvent('domready', function() { initTabs(); }); 

function initTabs() { $$('.tab-menu a').each(function(el) { 
  el.addEvent('click',function(e) { 
    var ev = new Event(e).stop(); 
      tabState(el);
      /*load appropriate pane here*/ 
      if(this == $('loanfintab')){
      	$('transactionHistoryPane').fancyHide();
      	$('amortizationSchedulePane').fancyHide();
      	$('billingStatementsPane').fancyHide();
      	$('loanFinancialPane').fancyShow();
      }
      else if(this == $('loanhisttab')){
      	$('loanFinancialPane').fancyHide();
      	$('amortizationSchedulePane').fancyHide();
      	$('billingStatementsPane').fancyHide();
      	$('transactionHistoryPane').fancyShow();
      }
      else if(this == $('amortizationtab')){
      	$('loanFinancialPane').fancyHide();
      	$('transactionHistoryPane').fancyHide();
      	$('billingStatementsPane').fancyHide();
      	$('amortizationSchedulePane').fancyShow();
      }
      else if(this == $('statementtab')){
      	$('loanFinancialPane').fancyHide();
      	$('transactionHistoryPane').fancyHide();
      	$('amortizationSchedulePane').fancyHide();
      	$('billingStatementsPane').fancyShow();
      }
    });
  });
  $('transactionHistoryPane').fancyHide();
  $('amortizationSchedulePane').fancyHide();
  $('billingStatementsPane').fancyHide();
  $('loanFinancialPane').fancyShow();
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
<h3>Loan Detail</h3>
<div class="panel">
	<ul class="tab-menu" id="detailTabs">
  	  <li><a href="#" class="active" id="loanfintab"><span>Loan Detail</span></a></li>
      <li><a href="#" id="loanhisttab"><span>Transaction History</span></a></li>
      <li><a href="#" id="amortizationtab"><span>Amortization Schedule</span></a></li>
      <li><a href="#" id="statementtab"><span>Billing Statements</span></a></li>
    </ul>
</div>
<div class="panel" id="loanFinancialPane">
  <jsp:include page="loanfinancialdata.jsp"/>
</div>
<div class="panel" id="transactionHistoryPane">
  <jsp:include page="transactionhistory.jsp"/>
</div>
<div class="panel" id="amortizationSchedulePane">
  <jsp:include page="amortizationschedule.jsp"/>
</div>
<div class="panel" id="billingStatementsPane">
  <jsp:include page="billingstatements.jsp"/>
</div>

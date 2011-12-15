<%@ include file="/WEB-INF/jsp/includes.jsp" %>
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
      	$('loanFinancialPane').fancyShow();
      }
      else if(this == $('loanhisttab')){
      	$('loanFinancialPane').fancyHide();
      	$('transactionHistoryPane').fancyShow();
      }
    });
  });
  $('transactionHistoryPane').fancyHide();
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
    </ul>
</div>
  <div class="panel" id="loanFinancialPane">
  	<jsp:include page="loanfinancialdata.jsp"/>
  </div>
  <div class="panel" id="transactionHistoryPane">
  	<jsp:include page="transactionhistory.jsp"/>
  </div>

<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<style type="text/css">
#tabContainer{ 
  position:relative; 
  width:700px;
  border-style: inset;
  border-width: thin;
  
} 

#tabMenu{ 
  position:relative; 
  height:30px; 
} 

#tabContent{
  position:relative;
  height:266px;
  font:12px Verdana, Arial, Helvetica, sans-serif; 
  color:#444444; border:4px solid #9fb2d6; overflow:auto;
} 

#tabContent .content{ 
  display:none; 
} 

#tabContent .active{ 
  padding:5px 10px; 
  display:block; 
} 

.menu{margin:0 auto; padding:0; height:30px; width:100%; display:block;}
.menu li{padding:0; margin:5; list-style:none; display:inline;}
.menu li a{float:left; padding-left:15px; display:block; color:rgb(255,255,255); text-decoration:none; font:12px Verdana, Arial, Helvetica, sans-serif; cursor:pointer; background:url("${imagesUrl}/main_bg.gif") 0px -30px no-repeat;}
.menu li a span{line-height:30px; float:left; display:block; padding-right:5px; background:url("${imagesUrl}/topMenuImages.png") 80% -30px no-repeat;}
.menu li a:hover{background-position:0px -60px; color:rgb(255,255,255);}
.menu li a:hover span{background-position:100% -60px;}
.menu li a.active, .menu li a.active:hover{line-height:30px; font:12px Verdana, Arial, Helvetica, sans-serif; background:url("${imagesUrl}/main_bg.gif") 0px -90px no-repeat; color:rgb(255,255,255);}
.menu li a.active span, .menu li a.active:hover span{background:url("${imagesUrl}/main_bg.gif") 100% -90px no-repeat;}
</style>

<script type="text/javascript"> 
window.addEvent('domready', function() { initTabs(); }); 

function initTabs() { $$('#tabMenu a').each(function(el) { 
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
} 

function tabState(ael) { 
  $$('#tabMenu a').each(function(el) { 
    if(el.hasClass('active')) { 
      el.removeClass('active'); 
      } 
    }); 
  ael.addClass('active'); 
} 
</script>
<h3>Loan Detail</h3>
<div id="tabContainer">
  <div id="tabMenu"> 
	<ul class="menu">
  	  <li><a href="#" class="active" id="loanfintab"><span>Loan Detail</span></a></li>
      <li><a href="#" id="loanhisttab"><span>Transaction History</span></a></li>
    </ul>
  </div>
  <div id="loanFinancialPane">
  	<jsp:include page="loanfinancialdata.jsp"/>
  </div>
  <div id="transactionHistoryPane">
  	<jsp:include page="transactionhistory.jsp"/>
  </div>
</div>

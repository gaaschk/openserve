<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<script type="text/javascript">
window.addEvent('domready', function() {
	var paymentrows = $$('.paymentrow .paymentrowrb');
	paymentrows.each(function(payment, i){
		payment.addEvent('click',function(event){
			var selectablerbs = $$('.paymentrow .paymentrowrb');
			for(var idx = 0; idx < selectablerbs.length; idx++){
				if(selectablerbs[idx] != this)
					selectablerbs[idx].checked = false;
			}
			var documenturl = new URI(document.location.href);
			var url = documenturl.get('scheme')+'://'+documenturl.get('host')+':'+documenturl.get('port')+documenturl.get('directory')+'paymentdetail.do?paymentdetailid='+this.id.split('-')[1];
			new Request.HTML({
				url: url,
				method: 'get',
				update: 'paymentdetail',
				evalScripts: true /* this is the default */
				}).send();
		});
	});
});
</script>
<form:form commandName="paymenthistorymodel">
  <table>
	<caption><label>Payment History</label></caption>
	<tr>
		<th></th>
		<th><label>Effective Date</label></th>
		<th><label>Posted Date</label></th>
		<th><label>Payment Amount</label></th>
	</tr>
	<c:forEach var="payment" items="${paymenthistorymodel.payments}" varStatus="pmtindex">
		<tr class="paymentrow" >
			<td><input type="radio" class="paymentrowrb" id="paymentrowrb-${payment.paymentID}"/></td>
			<td><form:input disabled="true" path="payments[${pmtindex.count-1}].paymentEffectiveDate"/></td>
			<td><form:input disabled="true" path="payments[${pmtindex.count-1}].paymentPostDate"/></td>
			<td><form:input disabled="true" path="payments[${pmtindex.count-1}].paymentAmount"/></td>
		</tr>
	 </c:forEach>
  </table>
  <div id="paymentdetail">
  </div>
</form:form>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/WEB-INF/layouts/includes.jsp" %>
<form:form id="dueDiligenceForm" commandName="manageDueDiligenceSchedulesModel">
	<div data-dojo-type="dijit/layout/ContentPane">
		<form:select id="loanProgramSelection" path="selectedLoanProgram" data-dojo-type="dijit/form/FilteringSelect">
     		<form:option value="-">Select Loan Program</form:option>
     		<form:options items="${manageDueDiligenceSchedulesModel.allLoanPrograms}" itemValue="loanProgramID" itemLabel="name"/>
     		<script type="dojo/method" event="onChange" args="newValue">
				require(['dojo/request'],function(request){
					var documenturl = new URI(document.location.href);
					var url = documenturl.get('scheme')+'://'+documenturl.get('host')+':'+documenturl.get('port')+documenturl.get('directory')+'duediligence/schedules.do?loanprogramid='+newValue;
					request.post(url,{
							data: $("#dueDiligenceForm").serialize()
						}).then(
						function(response){
							dojo.byId("schedulePane").innerHTML = response;
						}
					);
				})		
     		</script>
     	</form:select>
     	<div id="schedulePane" data-dojo-type="dijit/layout/StackContainer">
			<table>
				<c:forEach var="schedule" items="${schedules}" varStatus="index">
					<tr>
						<td><form:input path="${schedules[index.count-1]}.effectiveDate"/></td>
					</tr>
		</c:forEach>
	</table>
     	</div>
	</div>
</form:form>
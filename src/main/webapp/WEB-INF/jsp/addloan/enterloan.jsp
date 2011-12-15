<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<h1>Enter Loan Information</h1>
<form:form modelAttribute="loanModel">
	<h3>Person Data</h3>
	<fieldset>
		<label>SSN:</label>
		<c:out value="${loanModel.person.ssn}"/>
	</fieldset>
	<fieldset>
		<label>First Name:</label>
		<form:input path="person.firstName" type="text"/>
	</fieldset>
	<fieldset>
		<label>Last Name:</label>
		<form:input path="person.lastName" type="text"/>
	</fieldset>
	<h3>Loan Data</h3>
	<fieldset>
		<label>Loan Type:</label>
		<form:select path="loanType">
     		<form:option value="-" label="--Please Select"/>
     		<form:options/>
		</form:select>
	</fieldset>
	<fieldset>
		<label>Starting Principal:</label>
		<form:input class="masked" path="startingPrincipal" type="text" data-meiomask="reverse.dollar"/>
	</fieldset>
	<fieldset>
		<label>Starting Interest:</label>
		<form:input class="masked" path="startingInterest" data-meiomask="reverse.dollar" />
	</fieldset>
	<fieldset>
		<label>Starting Fees:</label>
		<form:input class="masked" path="startingFees" data-meiomask="reverse.dollar" />
	</fieldset>
	<input type="submit" value="Save Loan"></input>
</form:form>

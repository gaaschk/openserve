<%@ include file="/WEB-INF/jsp/includes.jsp" %>
	<fieldset>
		<label>Loan Type:</label>
		<form:select path="loanType">
     		<form:option value="-" label="--Please Select"/>
     		<form:options/>
		</form:select>
	</fieldset>
	<fieldset>
		<label class="nobr">Starting Principal:</label>
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

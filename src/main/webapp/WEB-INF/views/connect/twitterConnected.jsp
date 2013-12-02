<%@ include file="/WEB-INF/layouts/includes.jsp"%>

<h3>Connected to Twitter</h3>

<form id="disconnect" method="post">
	<div class="formInfo">
		<p>The Spring Social Showcase sample application is already connected to your Twitter account.
			Click the button if you wish to disconnect.			
	</div>

	<button type="submit">Disconnect</button>	
	<input type="hidden" name="_method" value="delete" />
</form>

<p><a href="<c:url value="/web/twitter" />">View your Twitter profile</a></p>


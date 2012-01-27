<%@ include file="/WEB-INF/layouts/includes.jsp"%>

<h3>Connected to Facebook</h3>

<form id="disconnect" method="post">
	<div class="formInfo">
		<p>
			Spring Social Showcase is connected to your Facebook account.
			Click the button if you wish to disconnect.
		</p>		
	</div>
	<button type="submit">Disconnect</button>	
	<input type="hidden" name="_method" value="delete" />
</form>

<a href="<c:url value="/web/facebook"/>">View your Facebook profile</a>

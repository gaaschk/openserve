<%@ include file="/WEB-INF/layouts/includes.jsp"%>

<h3>Connect to Facebook</h3>

<form action="<c:url value="/web/connect/facebook" />" method="POST">
	<input type="hidden" name="scope" value="publish_stream,user_photos,offline_access" />
	<div class="formInfo">
		<p>You aren't connected to Facebook yet. Click the button to connect Spring Social Showcase with your Facebook account.</p>
	</div>
	<p><button type="submit"><img src="<c:url value="/content/social/facebook/connect_light_medium_short.gif" />"/></button></p>
	<label for="postToWall"><input id="postToWall" type="checkbox" name="postToWall" /> Tell your friends about Spring Social Showcase on your Facebook wall</label>
</form>

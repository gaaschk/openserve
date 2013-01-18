<%@ include file="/WEB-INF/layouts/includes.jsp"%>
<ul class="menu-right">
	<li class="menu-item"><a href="${baseUrl }/home/home.do">Home</a></li>
	<sec:authorize access="isAnonymous()">
		<li class="menu-item"><a href="${baseUrl }/signin">Login</a></li>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
        <td><a href="<c:url value="/j_spring_security_logout"/>">Logout</a></td>
    </sec:authorize>
</ul>

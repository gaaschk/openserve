<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/WEB-INF/layouts/includes.jsp" %>
<html>
  <head>
	<title>OpenServ :: Home</title>
	<!-- load mootools for masked text fields -->
	<script src="${jsUrl}/magicbox/js/mootools-core-1.4.1-full-compat.js" type="text/javascript" charset="utf-8"></script>
	<script src="${jsUrl}/magicbox/js/mootools-more-1.4.0.1.js" type="text/javascript" charset="utf-8"></script>

	<script src="${jsUrl}/magicbox/js/Meio.Mask.js" type="text/javascript"></script>
	<script src="${jsUrl}/magicbox/js/Meio.Mask.Fixed.js" type="text/javascript"></script>
	<script src="${jsUrl}/magicbox/js/Meio.Mask.Reverse.js" type="text/javascript"></script>
	<script src="${jsUrl}/magicbox/js/Meio.Mask.Repeat.js" type="text/javascript"></script>
	<script src="${jsUrl}/magicbox/js/Meio.Mask.Reverse.js" type="text/javascript"></script>
	<script src="${jsUrl}/magicbox/js/Meio.Mask.Regexp.js" type="text/javascript"></script>
	<script src="${jsUrl}/magicbox/js/Meio.Mask.Extras.js" type="text/javascript"></script>

	<style type="text/css" title="text/css">
		@import "${jsUrl}/dojo-1.9.1/dgrid/css/skins/soria.css";
		@import "${jsUrl}/dojo-1.9.1/dijit/themes/soria/soria.css";
		@import "${jsUrl}/dojo-1.9.1/dijit/themes/claro/claro.css";
	</style>
	<style type="text/css">
		html, body {
			width: 100%;
			height: 100%;
			margin: 0;
			overflow:hidden;
			font: 12px Myriad,Helvetica,Tahoma,Arial,clean,sans-serif;
		}
		.tagline{
			color: #B2B2B2;
			font-family: Verdana,Arial,Helvetica,sans-serif;
			font-size: 10px;
			font-weight: bold;
			padding: 7px 0 0;
			text-align: center;
			text-transform: uppercase;
		}
		.topNav{
			text-align: right;
			font-family: Verdana,Arial,Helvetica,sans-serif;
		}
		.menu-right li{
			display: inline;
			list-style-type: none;
			margin: 0 10px 0 0;
		}
		.topNav a{
			color: #FFFFFF;
		}
		#main .ContentPane{
			background: none repeat scroll 0 0 #EEEEEE;
		}
	</style>
    <!-- load Dojo 
	-->
    <script type="text/javascript" data-dojo-config="isDebug:true,async:true,parseOnLoad:true" src="${jsUrl}/dojo-1.9.1/dojo/dojo.js"></script>
	<script type="text/javascript">
			require([
				'dojo/parser',
				'dijit/layout/BorderContainer',
				'dijit/layout/ContentPane',
				'dijit/MenuBar',
				'dijit/MenuBarItem',
				'dijit/PopupMenuBarItem',
				'dijit/DropDownMenu']);
	</script>
  </head>
  <body class="soria">
	<div id="main" data-dojo-type="dijit/layout/BorderContainer" data-dojo-props="design:'headline'" style="height: 100%; width: 100%; padding: 0">
		<div id="desktopHeader" data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'top'" style="background-color: #718BA6; padding:0" class="claro">
			<div>
				<h2 class="tagline">A <span class="taglineEm" style="color: #EEEEEE; font-weight: bold">Loan Servicing</span> Application</h2>
				<div class="topNav">
					<ul class="menu-right">
						<li class="menu-item"><a href="${baseUrl}/home/home.do">Home</a></li>
						<sec:authorize access="isAnonymous()">
							<li class="menu-item"><a href="${baseUrl}/signin">Login</a></li>
						</sec:authorize>
						<sec:authorize access="isAuthenticated()">
							<td><a href="<c:url value="/j_spring_security_logout"/>">Logout</a></td>
						</sec:authorize>
					</ul>
				</div class="topNav">
			</div>
			<div id="mainMenu" data-dojo-type="dijit/MenuBar" data-dojo-props="region: 'top'" style="background: none repeat scroll 0 0 #EEEEEE">
				<div id="addLoan" data-dojo-type="dijit/MenuBarItem" onClick="window.location.href='/openserv/web/addloan'">Add Loan</div>
				<div id="makePayment" data-dojo-type="dijit/MenuBarItem" onClick="window.location.href='/openserv/web/payment'">Payment</div>
				<div id="accountSummary" data-dojo-type="dijit/MenuBarItem" onClick="window.location.href='/openserv/web/accountsummary'">Account Summary</div>
				<div id="loanProgram" data-dojo-type="dijit/MenuBarItem" onClick="window.location.href='/openserv/web/loanprogram'">Manage Loan Programs</div>
			</div>
		</div>
		<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'center'">
		  <tiles:insertAttribute name="content" />
		</div>
		<div id="desktopFooter" data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'bottom'" style="height: 50px; width: 100%;">
			<span>1.877.MYNUMBER</span>
			<br/>
			<span>
				<a href="">Privacy Policy</a>
			</span>
			<br/>
			<span>Copyright &copy; </span>
		</div>
	</div>
	<script type="text/javascript">
  		$$('input[data-meiomask]').each(
			function(input) {
	  			input.meiomask(input.get('data-meiomask'), JSON
						.decode(input.get('data-meiomask-options')));
				});
  		$$('input[data-meiomask]').each(function(input) {
			console.log("meiomask: " + input.get('data-meiomask'));
		});
	</script>
  </body>
</html>
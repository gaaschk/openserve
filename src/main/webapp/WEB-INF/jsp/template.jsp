<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<html>
	<head>
		<title>PhoenixWeb :: Home</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="${cssUrl}/${cssName}" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="${cssUrl}/coin-slider.css" />
		<script type="text/javascript" src="${jsUrl}/cufon-yui.js"></script>
		<script type="text/javascript" src="${jsUrl}/cufon-times.js"></script>
		<script type="text/javascript" src="${jsUrl}/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="${jsUrl}/script.js"></script>
		<script type="text/javascript" src="${jsUrl}/coin-slider.min.js"></script>
	</head>
	<body>
		<div class="main">
			<div class="header_resize">
				<div class="header">	
					<img src="${imagesUrl}/cology-logo.png" alt="Cology, Inc." />
				   	<div class="clr"></div>
					<div class="menu_nav">
						<tiles:insertAttribute name="menu" />	
					</div>
				    <div class="clr"></div>
				</div>
			</div>
		<div class="content">
			<div class="content_resize">
				<div class="article">
					<tiles:insertAttribute name="content" />
				</div>
			</div>
		</div>
		<div class="clr"></div>
		</div>
		<div class="footer">
			<div class="footer_resize">
				<span>1.877.456.4PPS (4777) &#8226; Borrowers </span>
				<br/>
				<span>1.877.458.4PPS (4777) &#8226; Financial Partners </span>
				<br/>
				<span>
					<a href="http://www.ppslc.com/disclaimer.php">Website Disclaimer and Privacy Policy</a>
				</span>
				<br/>
				<span>Copyright &copy; 2010  Panhandle-Plains Management and Servicing Corporation</span>
			</div>
		</div>
	</body>
</html>
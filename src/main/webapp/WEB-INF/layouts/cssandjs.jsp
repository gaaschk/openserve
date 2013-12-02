<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/WEB-INF/layouts/includes.jsp" %>
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

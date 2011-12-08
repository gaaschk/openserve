<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<html>
	<head>
		<title>PhoenixWeb :: Home</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="${cssUrl}/${cssName}" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="${cssUrl}/coin-slider.css" />
		<!-- 
		<script type="text/javascript" src="${jsUrl}/cufon-yui.js"></script>
		<script type="text/javascript" src="${jsUrl}/cufon-times.js"></script>
		<script type="text/javascript" src="${jsUrl}/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="${jsUrl}/mootools-core-1.4.1-full-compat.js"></script>
		<script type='text/javascript' src='${jsUrl}/dFilter.js'></script> 
		<script type='text/javascript' src='${jsUrl}/iMask-full.js'></script>
		<script type="text/javascript" src="${jsUrl}/script.js"></script>
		<script type="text/javascript" src="${jsUrl}/coin-slider.min.js"></script>
		-->
		
		<script src="${jsUrl}/mootools-core-1.4.1-full-compat.js" type="text/javascript" charset="utf-8"></script>
		<script src="${jsUrl}/mootools-more-1.4.0.1.js" type="text/javascript" charset="utf-8"></script>
		
		<script src="${jsUrl}/Meio.Mask.js" type="text/javascript"></script>
		<script src="${jsUrl}/Meio.Mask.Fixed.js" type="text/javascript"></script>
		<script src="${jsUrl}/Meio.Mask.Reverse.js" type="text/javascript"></script>
		<script src="${jsUrl}/Meio.Mask.Repeat.js" type="text/javascript"></script>
		<script src="${jsUrl}/Meio.Mask.Reverse.js" type="text/javascript"></script>
		<script src="${jsUrl}/Meio.Mask.Regexp.js" type="text/javascript"></script>
		<script src="${jsUrl}/Meio.Mask.Extras.js" type="text/javascript"></script>
		<script type="text/javascript">
			window.addEvent('domready', function() {
  				//time to implement fancy show / hide
  				Element.implement({
    				//implement show
				    fancyShow: function() {
      					this.show();
      					this.fade('in');
    				},	
    				//implement hide
    				fancyHide: function() {
      					this.fade('out');
      					this.hide();
    				}
  				});
			});		
		</script>
		<style type="text/css">
			fieldset{ display: table; border-style: none;}
			label{ float: left; width: 100px; }
		</style>
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
		<div class="sidebar">
			<div class="sidebar_resize">
				A Menu Item Goes here.
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
				<span>1.877.COLOGY3</span>
				<br/>
				<span>
					<a href="http://www2.cology.com/privacy-policy">Privacy Policy</a>
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
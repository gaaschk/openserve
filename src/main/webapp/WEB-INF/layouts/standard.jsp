<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/WEB-INF/layouts/includes.jsp" %>
<html>
	<head>
		<title>PhoenixWeb :: Home</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

		<link rel="stylesheet" type="text/css" href="/phoenixweb/content/mocha/themes/default/css/Content.css" />
		<link rel="stylesheet" type="text/css" href="/phoenixweb/content/mocha/themes/default/css/Core.css" />
		<link rel="stylesheet" type="text/css" href="/phoenixweb/content/mocha/themes/default/css/Layout.css" />
		<link rel="stylesheet" type="text/css" href="/phoenixweb/content/mocha/themes/default/css/Dock.css" />
		<link rel="stylesheet" type="text/css" href="/phoenixweb/content/mocha/themes/default/css/Window.css" />
		<link rel="stylesheet" type="text/css" href="/phoenixweb/content/mocha/themes/default/css/Tabs.css" />
		
		<script src="${jsUrl}/mootools-core-1.4.1-full-compat.js" type="text/javascript" charset="utf-8"></script>
		<script src="${jsUrl}/mootools-more-1.4.0.1.js" type="text/javascript" charset="utf-8"></script>
		
		<script src="${jsUrl}/mocha.js" type="text/javascript"></script>
		<script src="${jsUrl}/mocha-init.js" type="text/javascript"></script>
		
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
  				$('page').set({
  					scrollbars: true
  				});
			});		
		</script>
		<style type="text/css">
			fieldset{ display: table; border-style: none;}
			label{ float: left; width: 100px; }
		</style>
	</head>
	<body>
	  <div id="desktop">
		<div id="dektopHeader">
		  <div id="desktopTitlebarWrapper">
		    <div id="desktopTitlebar">
			  <h1 class="applicationTitle">Phoenix</h1>
			  <h2 class="tagline">A <span class="taglineEm">Loan Servicing</span> Application</h2>
			  <div id="topNav">
			    <tiles:insertAttribute name="menu" />
			  </div>
			</div>
		  </div>
		  <div id="desktopNavbar">	
			<ul>
				<li class="menu-item"><a href="/phoenixweb/web/addloan">Add a new Loan</a></li>
				<li class="menu-item"><a href="/phoenixweb/web/payment">Payment</a></li>
				<li class="menu-item"><a href="/phoenixweb/web/accountsummary/home.do">Account Summary</a></li>
			</ul>
		  </div>
		</div><!-- desktop header end -->
		<div id="pageWrapper">
		  <div class="panel" id="page">
		    <tiles:insertAttribute name="content" />
		  </div>
		</div>
		
		<div id="desktopFooterWrapper">
			<div id="desktopFooter">
				<span>1.877.MYNUMBER</span>
				<br/>
				<span>
					<a href="">Privacy Policy</a>
				</span>
				<br/>
				<span>Copyright &copy; </span>
			</div>
		</div>
	</div><!-- desktop end -->
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
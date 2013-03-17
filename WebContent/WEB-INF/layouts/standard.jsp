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
			@import "/openserv/content/style.css";
			@import "/openserv/content/dojo/resources/dojo.css";
			@import "/openserv/content/dijit/themes/claro/claro.css";
		</style>

		<script type="text/javascript" src="${jsUrl}/dojo/dojo.js" data-dojo-config="isDebug:true,async:true,parseOnLoad:true"></script>
		<script type="text/javascript">
			require([
				'dojo/parser',
				'dojo/request',
				'dojo/dom',
				'dojo/on',
				'dojo/domReady!', 
				'dijit/MenuBar', 
				'dijit/MenuBarItem', 
				'dijit/PopupMenuBarItem',
    			'dijit/DropDownMenu', 
    			'dijit/MenuItem', 
    			'dijit/layout/BorderContainer', 
    			'dijit/layout/TabContainer',
        		'dijit/layout/ContentPane',
        		'dijit/form/Form', 
        		'dijit/form/TextBox', 
        		'dijit/form/Button',
		     	'dijit/form/RadioButton'
        	]);
        </script>
  	</head>
	<body class="claro">
	  <div id="appLayout" class="demoLayout" data-dojo-type="dijit/layout/BorderContainer"
           data-dojo-props="design: 'headline'" style="height: 100%; width: 100%">
		<div id="dektopHeader" class="edgePanel" data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'top'">
		  <div id="desktopTitlebar">
			<h1 class="applicationTitle">OpenServ</h1>
			<h2 class="tagline">A <span class="taglineEm">Loan Servicing</span> Application</h2>
			<div id="topNav">
			  <tiles:insertAttribute name="menu" />
	  	    </div>
		  </div>
		  <div id="desktopNavbar" data-dojo-type="dijit/MenuBar">	
			<ul>
				<li data-dojo-type="dijit/MenuBarItem"><a href="/openserv/web/addloan">Add a new Loan</a></li>
				<li data-dojo-type="dijit/MenuBarItem"><a href="/openserv/web/payment">Payment</a></li>
				<li data-dojo-type="dijit/MenuBarItem"><a href="/openserv/web/accountsummary">Account Summary</a></li>
				<li data-dojo-type="dijit/MenuBarItem"><a href="/openserv/web/loantype">Manage Loan Programs</a></li>
				<li data-dojo-type="dijit/MenuBarItem"><a href="/openserv/web/rates/stocks">Stocks</a></li>
			</ul>
		  </div>
		</div><!-- desktop header end -->
		<div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'center'">
		  <tiles:insertAttribute name="content" />
		  <a href="/openserv/OpenService-AddLoan.mov">Add Loan Screencast</a>
  		</div>
		<div id="desktopFooter" data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'bottom'">
			<span>1.877.MYNUMBER</span>
			<br/>
			<span>
				<a href="">Privacy Policy</a>
			</span>
			<br/>
			<span>Copyright &copy; </span>
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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<html>
	<head>
		<title>PhoenixWeb :: Home</title>
		<c:if test="${empty cookie['ppmsc.embedded']}">
			<link rel="stylesheet" href="${cssUrl}/${cssName}" type="text/css" media="screen" title="phoenixweb" charset="utf-8" />
		</c:if>
		<c:if test="${!empty cookie['ppmsc.embedded']}">
			<link rel="stylesheet" href="${cssUrl}/${cssName}" type="text/css" media="screen" title="phoenixweb" charset="utf-8" />
		</c:if>
		<link rel="stylesheet" href="${cssUrl}/jquery-ui-1.7.2.custom.css" type="text/css" media="screen" title="phoenixweb" charset="utf-8" />
		<script charset="utf-8" type="text/javascript" src="${jsUrl}/jquery-1.3.2.js"></script>
		<script charset="utf-8" type="text/javascript" src="${jsUrl}/jquery-ui-1.7.2.custom.min.js"></script>
		<script charset="utf-8" type="text/javascript" src="${jsUrl}/jquery.jeditable.js"></script>
		<script>
			$(document).ready(function(){
			  $(".onload-hidden").hide();		
			  $(".has-datepicker").datepicker();
			});

			function remove(prefix){
				$("#"+prefix+"Container").remove();
			}

			function edit(prefix){
				$("#"+prefix+"View").hide();
				$("#"+prefix+"Edit").show();
			}

			function setPreferred(prefix, type, index){
				var count = $("#"+type+"Count").attr("value");

				for(var i=0;i<count;i++){
					var preferredButton = $("#"+prefix+i+"PreferredButton");
					var preferredField = $("."+prefix+i+"Preferred");

					if(i!=index){
						preferredButton.show();
						preferredField.attr("value","false");
					}
					else{
						preferredButton.hide();
						preferredField.attr("value","true");
					}
				}
			}
			
			function addNew(type) {
				var newItem = $("#"+type+"_Index_Container").clone();
				var countField = $("#"+type+"Count");
				var count = countField.attr("value");

				var replaceExpression1 = eval("/_Index_/g");
				var newHtml1 = newItem.html().replace(replaceExpression1,count);
				var replaceExpression2 = eval("/_"+type+"Collection_/g");
				var newHtml2 = newHtml1.replace(replaceExpression2,"");
				newItem.html(newHtml2);
				
				newItem.attr("id",type+count+"Container");
				newItem.appendTo("#"+type+"Container");
				$("#"+type+count+"Container").show();
				countField.attr("value",count*1+1);
			}

		</script>
	</head>
	<body>
		<c:if test="${empty cookie['ppmsc.embedded']}">
		<div class="header-content">	
			<img src="${imagesUrl}/cology-logo.png" alt="Cology, Inc." />
		</div>
		</c:if>
		<div class="menu-bar">
			<tiles:insertAttribute name="menu" />
		</div>
		<div class="primary-content">
			<tiles:insertAttribute name="content" />
		</div>
		<div class="footer-content">
			<c:if test="${!empty cookie['ppmsc.embedded']}">
				<span>Powered by:</span>
				<br/>
				<img src="${imagesUrl}/cology-logo.png" alt="Cology, Inc." />
				<br/>
			</c:if>
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
	</body>
</html>
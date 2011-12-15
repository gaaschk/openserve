<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
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
		
		<script src="${jsUrl}/mootools-core-1.3-full-nocompat.js" type="text/javascript" charset="utf-8"></script>
		<script src="${jsUrl}/mootools-more-1.3.0.1.js" type="text/javascript" charset="utf-8"></script>
		
		<script src="${jsUrl}/Meio.Mask.js" type="text/javascript"></script>
		<script src="${jsUrl}/Meio.Mask.Fixed.js" type="text/javascript"></script>
		<script src="${jsUrl}/Meio.Mask.Reverse.js" type="text/javascript"></script>
		<script src="${jsUrl}/Meio.Mask.Repeat.js" type="text/javascript"></script>
		<script src="${jsUrl}/Meio.Mask.Reverse.js" type="text/javascript"></script>
		<script src="${jsUrl}/Meio.Mask.Regexp.js" type="text/javascript"></script>
		<script src="${jsUrl}/Meio.Mask.Extras.js" type="text/javascript"></script>
	</head>

	<body>
		
		<h1>Fixed</h1>
		<div class="content">
			<form action="index_submit" method="get">
				<fieldset>
					<label>fixed Mask:</label>
					<input id="change-mask-input" style="width: 200px" type="text" data-meiomask-options="{mask: '999-99-9999', autoTab: true, removeIfInvalid: true}" data-meiomask="fixed" />
				</fieldset>
				<a id="change-mask" title="change mask" href="#">change mask</a>
				<fieldset>
					<label>fixed.phone Mask:</label>
					<input style="width: 200px" type="text" data-meiomask="fixed.phone" />
				</fieldset>
				<fieldset>
					<label>fixed.phone-us Mask:</label>
					<input style="width: 200px" type="text" data-meiomask="fixed.phone-us" />
				</fieldset>
				<fieldset>
					<label>fixed.cpf Mask:</label>
					<input style="width: 200px" type="text" data-meiomask="fixed.cpf" />
				</fieldset>
				<fieldset>
					<label>fixed.cnpj Mask:</label>
					<input style="width: 200px" type="text" data-meiomask="fixed.cnpj" />
				</fieldset>
				<fieldset>
					<label>fixed.date Mask:</label>
					<input style="width: 200px" type="text" data-meiomask="fixed.date" />
				</fieldset>
				<fieldset>
					<label>fixed.date Mask:</label>
					<input style="width: 200px" type="text" data-meiomask="fixed.date" value="321312321" />
				</fieldset>
				<fieldset>
					<label>fixed.date-us Mask:</label>
					<input style="width: 200px" type="text" data-meiomask="fixed.date-us" />
				</fieldset>
				<fieldset>
					<label>fixed.date-us Mask:</label>
					<input style="width: 200px" type="text" data-meiomask="fixed.date-us" value="12122012" />
				</fieldset>
				<fieldset>
					<label>fixed.cep Mask:</label>
					<input style="width: 200px" type="text" data-meiomask="fixed.cep" />
				</fieldset>
				<fieldset>
					<label>fixed.time Mask:</label>
					<input style="width: 200px" type="text" data-meiomask="fixed.time" />
				</fieldset>
				<fieldset>
					<label>fixed.cc Mask:</label>
					<input style="width: 200px" type="text" data-meiomask="fixed.cc" />
				</fieldset>
			</form>
		</div>

		<h1>Reverse</h1>
		<div class="content">
			<form action="index_submit" method="get">
				<fieldset>
					<label>reverse.integer Mask:</label>
					<input style="width: 200px" type="text" data-meiomask-options="{autoEmpty: true, autoTab: true}" data-meiomask="reverse.integer" />
				</fieldset>
				<fieldset>
					<label>reverse.decimal Mask:</label>
					<input style="width: 200px" type="text" data-meiomask-options="{autoEmpty: true}" data-meiomask="reverse.decimal" />
				</fieldset>
				<fieldset>
					<label>reverse.decimal-us Mask:</label>
					<input style="width: 200px" type="text" data-meiomask="reverse.decimal-us" />
				</fieldset>
				<fieldset>
					<label>reverse.reais Mask:</label>
					<input style="width: 200px" type="text" data-meiomask-options="{autoTab: true}" data-meiomask="reverse.reais" />
				</fieldset>
				<fieldset>
					<label>reverse.dollar Mask:</label>
					<input style="width: 200px" type="text" data-meiomask="reverse.dollar" />
				</fieldset>
			</form>
		</div>
		
		<h1>Regexp</h1>
		<div class="content">
			<form action="index_submit" method="get">
				<fieldset>
					<label>regexp.ip Mask:</label>
					<input style="width: 200px" type="text" data-meiomask="regexp.ip" />
				</fieldset>
				<fieldset>
					<label>regexp.email Mask:</label>
					<input style="width: 200px" type="text" data-meiomask="regexp.email" />
				</fieldset>
			</form>
		</div>
		
		<h1>Repeat</h1>
		<div class="content">
			<form action="index_submit" method="get">
				<fieldset>
					<label>repeat Mask:</label>
					<input style="width: 200px" type="text" data-meiomask-options="{mask: '9', maxLength: 5}" data-meiomask="repeat" />
				</fieldset>
			</form>
		</div>

		<script type="text/javascript">
			$('change-mask').addEvent('click', function(){
				$('change-mask-input').meiomask('reverse.integer');
			});
			$$('input').each(function(input){
				input.meiomask(input.get('data-meiomask'), JSON.decode(input.get('data-meiomask-options')));
			});
			
			/*
			$$('input').each(function(input){
				console.log(input.get('meiomask'));
			});*/
		</script>
	</body>
</html>

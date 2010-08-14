<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html lang="en" xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Oikos</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="${content.getURI("/resources/css/footerStick.css")}" 	type="text/css" rel="stylesheet">
		<link href="${content.getURI("/resources/css/global.css")}" 		type="text/css" rel="stylesheet">
		<script type="text/javascript" src="${content.getURI("/resources/js/ajax.js")}"></script>
	</head>
	<body>
		<div id="page"> 
	    	<div id="top">${templateTool.render("navigations.Top")}</div> 
		  	<div id="screen">${screen_placeholder}</div> 
		  	<div id="bottom">${templateTool.render("navigations.Bottom")}</div> 
		</div>
	</body>
</html>

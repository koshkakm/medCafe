<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
    
    <html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
	<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<title>Demo Page: Using Progressive Enhancement to Convert a Select Box Into an Accessible jQuery UI Slider</title>
	<script type="text/javascript" src="js/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="js/ui.all-1.7.1.js"></script>
	<script type="text/javascript" src="js/selectToUISlider.jQuery.js"></script>

	<link type="text/css" href="css/custom-theme/jquery-ui-1.7.2.custom.css" rel="stylesheet" />	
	<link rel="Stylesheet" href="css/ui.slider.extras.css" type="text/css" />
	<style type="text/css">
		body {font-size: 62.5%; font-family:"Segoe UI","Helvetica Neue",Helvetica,Arial,sans-serif; }
		fieldset { border:0; margin: 6em; height: 12em;}	
		label {font-weight: normal; float: left; margin-right: .5em; font-size: 1.1em;}
		select {margin-right: 1em; float: left;}
		.ui-slider {clear: both; top: 5em;}
	</style>
	<script type="text/javascript">
		$(function(){
			$('select#valueAA, select#valueBB').selectToUISlider({
				labels: 12
			});
		
		});
		
	</script>
	<!-- jQuery UI theme switcher -->
	<script type="text/javascript" src="http://ui.jquery.com/applications/themeroller/themeswitchertool/"></script>
	<script type="text/javascript"> $(function(){ $('<div style="position: absolute; right: 20px; margin-top: -40px" />').appendTo('body').themeswitcher({onSelect: function(){ setTimeout(fixToolTipColor, 800); }}); });</script>
</head>

<body>
	<tags:IncludeRestlet relurl="c/dates/"/>
</body>
</html>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %><%@
    taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<%
	String patientId = request.getParameter("patient_id");
	if (patientId == null)
		patientId = "1";
		
	String dataUrl = request.getParameter("data");
	if (dataUrl == null)
		dataUrl = "c/repositories/medcafe/patients/" + patientId + "/charts/temperature";

	%>
 <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Flot Examples</title>
    <link href="layout.css" rel="stylesheet" type="text/css"></link>
    <!--[if IE]><script language="javascript" type="text/javascript" src="../excanvas.min.js"></script><![endif]-->
   	<script language="javascript" type="text/javascript" src="js/jquery-1.3.2.js"></script>
    <script language="javascript" type="text/javascript" src="js/jquery.flot.js"></script>
 	<script language="javascript" type="text/javascript" src="js/jquery.flot.selection.js"></script>
 	<script type="text/javascript" language="javascript" src="js/jquery.delay.js"></script>
	
 </head>
    <body>
    <h1>Temperature Chart 1</h1>

    <div id="placeholder" style="width:600px;height:300px;"></div>

	<div id="miniature" style="float:left;margin-left:20px;margin-top:50px">
    <div id="overview" style="width:166px;height:100px"></div>

    <p>Example of loading data dynamically with AJAX. Temperature Graph. Click the buttons below.</p>

    <p></p>

    <p>
      
    </p>

    <p>
      <input class="dataUpdate" type="button" value="Poll for data">
    </p>

<script id="source" language="javascript" type="text/javascript">
$(function () {
    var options = {
        lines: { show: true },
        points: { show: true },
        xaxis: { mode: "time"},
        selection: { mode: "xy" }
    };
    
    
    var data = [];
    var placeholder = $("#placeholder");
    var overview = $("#overview");
    
    //$.plot(placeholder, data, options);

	
    // fetch one series, adding to what we got
    var alreadyFetched = {};
      
   	// find the URL in the link right next to us 
    var dataurl = "<%=dataUrl%>";

        // then fetch the data with jQuery
        function onDataReceived(series) {
        	
            // extract the first coordinate pair so you can see that
            // data is now an ordinary Javascript object
            var firstcoordinate = '(' + series.data[0][0] + ', ' + series.data[0][1] + ')';

            // let's add it to our current data
            if (!alreadyFetched[series.label]) {
                alreadyFetched[series.label] = true;
                data.push(series);
            }
        
            // and plot all we got
            $("#placeholder").delay(100,function()
			{
				
            	$.plot(placeholder, data, options);
           
            
	            $.plot(overview, data, {
			        legend: { show: true, container: $("#overviewLegend") },
			        points: { show: true },
			        xaxis: { mode: "time" },
			        grid: { color: "#999" },
			        selection: { mode: "xy" }
	    		});
	
	    		$("#placeholder").bind("plotselected", function (event, ranges) 
	    		{
			        // clamp the zooming to prevent eternal zoom
			        if (ranges.xaxis.to - ranges.xaxis.from < 0.00001)
			            ranges.xaxis.to = ranges.xaxis.from + 0.00001;
			        if (ranges.yaxis.to - ranges.yaxis.from < 0.00001)
			            ranges.yaxis.to = ranges.yaxis.from + 0.00001;
			        
			        // do the zooming
			        plot = $.plot($("#placeholder"),data,
			                      $.extend(true, {}, options, {
			                          xaxis: { min: ranges.xaxis.from, max: ranges.xaxis.to },
			                          yaxis: { min: ranges.yaxis.from, max: ranges.yaxis.to }
			                      }));
			        
			        // don't fire event on the overview to prevent eternal loop
			        overview.setSelection(ranges, true);
			    });
			    
			    $("#overview").bind("plotselected", function (event, ranges) {
	        		plot.setSelection(ranges);
	   			 });
   			 
   			  });
         }
        
        var myJSONObject =<tags:IncludeRestlet relurl="<%=dataUrl%>" mediatype="json"/>;
        //var myJSONObject ={"label":"Temperature","data":[["1261380000000","100.1"],["1261385000000","101.7"],["12614250000001261480000000","101.7101.5"],["1261485000000","100.2"],["1261490000000","100.2"],["1261584970731","98.7"]]};
        //var myJSONObject ={ "label": 'Temperature (Patient 1)', "data": [[1261380000000, 100.1], [1261385000000, 101.7],[1261425000000, 101.7],[1261480000000, 101.5], [1261485000000, 100.2], [1261490000000, 100.2], [1261584970731, 98.7]]};
        onDataReceived(myJSONObject);
        
});


</script>

 </body>
</html>
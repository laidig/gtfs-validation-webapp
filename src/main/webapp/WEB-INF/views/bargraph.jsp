<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="info.mta.bustech.urlhelper.asset" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GTFS Validaton</title>
 		<%=asset.JS("jquery.js",request)%>
 		<%=asset.CSS("style.css",request)%>
 		
 		<%=asset.JS("jquery-ui.js",request)%>
	    <%=asset.CSS("jquery-ui-1.10.3.custom.min.css",request)%>
	       
	       
 		<!-- BEGIN: load jqplot -->
	      <%=asset.JS("jquery.jqplot.min.js",request)%>
	      <%=asset.JS("plugins/jqplot.barRenderer.min.js",request)%>
	      <%=asset.JS("plugins/jqplot.canvasTextRenderer.min.js",request)%>
	      <%=asset.JS("plugins/jqplot.canvasAxisLabelRenderer.min.js",request)%>
	      <%=asset.JS("plugins/jqplot.canvasAxisTickRenderer.min.js",request)%>
	      <%=asset.JS("plugins/jqplot.enhancedLegendRenderer.min.js",request)%>
	      <%=asset.JS("plugins/jqplot.categoryAxisRenderer.min.js",request)%>
	      <%=asset.JS("plugins/jqplot.highlighter.min.js",request)%>
	      <%=asset.JS("plugins/jqplot.cursor.min.js",request)%>
	      <%=asset.JS("bar_graph.js",request)%>
	      <%=asset.CSS("jquery.jqplot.min.css",request)%>
	      <%=asset.CSS("bargraph.css",request)%>
	    <!-- END: load jqplot -->
</head>
<script>
$(document).ready(function(){
	  labels = ["YU", "BLA","TEST"];
	  Dates = ["Jan 19", "Jan 20", "Jan 21"];
	  var Depot1 = [2.61,5.00,6.00];
	  var Depot2 = [2.61,5.00,6.00];
	  var Depot3 = [2.61,5.00,6.00];

	  var file = "File Name"
	  
 		var north = $.jqplot('north', [Depot1, Depot2, Depot3],BarChart(file));
   		$(window).resize(function() {
      	 	north.replot( {resetAxes: true } );
    	 	});
	});
	

</script>
<body>
	<div id="north" class="bargraph"></div>
</body>
</html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="info.mta.bustech.urlhelper.asset"%>

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
<style>
.graphGTFS {
	margin: 0 auto;
	min-width: 800px;
	padding: 80px 0;
	width: 90%;
	padding-bottom: 30px;
}
</style>
<body>

	<c:forEach items="${GraphJSON}"  var="GTFS"	varStatus="loop">

		<script>

		$(document).ready(function(){
			  
			  var GTFSData = $.parseJSON('${GTFS}');
			  var Labels = GTFSData.Depots;
			  var Dates = GTFSData.Dates;
			  var Depots = GTFSData.TripCount;

			  var file = '${Files[loop.index]}';
			  
		 		var north = $.jqplot('trips${loop.index}', Depots,BarChart(file,Labels,Dates));
		   		$(window).resize(function() {
		      	 	north.replot( {resetAxes: true } );
		    	 	});
		});
		
	</script>

		<div class="graphGTFS">

			<div id="trips${loop.index}" class="bargraph"></div>

		</div>
	</c:forEach>

</body>
</html>
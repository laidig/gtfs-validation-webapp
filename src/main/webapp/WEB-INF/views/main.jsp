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
</head>
<script>
$(document).ready(function() {
	$( "#gtfs_options" ).submit(function( event ) {
		var list = 0;
		$('#gtfs_options option').each(function() {

		    if($(this).is(':selected'))
		    	list++;
		});
		
		if(list == 0)
		{
			alert("Please select a zip file");
			return false;
		}else
		{
			 $.ajax({
				  	type: "POST",
			        url: $(this).attr("action"),
			        data: $(this).serializeArray(),
			        async:false,
				    success: function(data)
				    {
				      //callback methods go right here
				    }
				  });
			 
			// event.preventDefault();
		}
	});
	
});
</script>
<body>
	<div id="gtfs_container">
		<form id="gtfs_options" action="graph" method="post">
			<select name="GTFSs" multiple style="width: 300px">
				<c:forEach items="${GTFSList}" var="current">
					<option value="${current}"><c:out value="${current}"></c:out></option>
				</c:forEach>
			</select> <br>
			<br>
			<input type="submit" value="Draw Graphs">
		</form>

		<p>To select multiple options, please hold down the Ctrl (windows)
			/ Command (Mac) and click your options.</p>
	</div>
</body>
</html>
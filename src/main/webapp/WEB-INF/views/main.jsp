<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="info.mta.bustech.urlhelper.asset"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GTFS Validaton</title>
<%=asset.JS("jquery.js", request)%>
<%=asset.CSS("style.css", request)%>
</head>
<style>
.next {
	width: 50%;
	float: left;
}

#fotter {
	position: absolute;
	bottom: 0;
	width: 100%;
	margin-left: auto;
	margin-right: auto;
	padding:20px;
}
#loading{
	display:none; 
	width: 100px;
	height: 100px; 
	padding:10px
}
</style>
<script>
	$(document).ready(function() {
		$("#gtfs_build").submit(function(event) {
			var list = 0;
			$('#gtfs_build option').each(function() {

				if ($(this).is(':selected'))
					list++;
			});

			if (list == 0) {
				alert("Please select a zip file");
				return false;
			} else {
				$("#loading").show();
				$.ajax({
					type : "POST",
					url : "build",
					data : $(this).serializeArray(),
					async : false,
					success : function(data) {
						//callback methods go right here
						$("#loading").hide();
					}
				});

				// event.preventDefault();
			}
		});

	});
</script>
<body>
	<div id="gtfs_wrapper">
		<div class="next">
			<form id="gtfs_build">
				<select name="GTFSs" multiple style="width: 300px">
					<c:forEach items="${GTFSzip}" var="current">
						<option value="${current}"><c:out value="${current}"></c:out></option>
					</c:forEach>
				</select> <br> <br> <input type="submit" value="Build CSV">
			</form>

		</div>

		<div class="next">
			<form id="gtfs_csv" action="graph" method="post">
				<select name="GTFSs" multiple style="width: 300px">
					<c:forEach items="${GTFScsv}" var="current">
						<option value="${current}"><c:out value="${current}"></c:out></option>
					</c:forEach>
				</select> <br> <br> <input type="submit" value="Draw Graphs">
			</form>
		</div>
		<br>
		<br>
		<div id="fotter"><span id="loading"><img src="resources/assets/image/loading.gif" /></br><h2>Building...</h2></span>
		To select multiple options, please hold down CTRL (windows) / CMND (Mac) and click your options.</div>
	</div>

</body>
</html>
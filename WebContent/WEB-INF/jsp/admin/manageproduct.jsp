<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>Manage Products</title>
<meta name="generator" content="Bootply" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">

<!-- DataTables CSS -->
<link
	href="${pageContext.request.contextPath}/resources/bootstrap/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css"
	rel="stylesheet">

<!-- DataTables Responsive CSS -->
<link
	href="${pageContext.request.contextPath}/resources/bootstrap/bower_components/datatables-responsive/css/dataTables.responsive.css"
	rel="stylesheet">

<!-- Bootstrap Core CSS -->
<link
	href="${pageContext.request.contextPath}/resources/bootstrap/bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- MetisMenu CSS -->
<link
	href="${pageContext.request.contextPath}/resources/bootstrap/bower_components/metisMenu/dist/metisMenu.min.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link
	href="${pageContext.request.contextPath}/resources/bootstrap/dist/css/sb-admin-2.css"
	rel="stylesheet">

<!-- Custom Fonts -->
<link
	href="${pageContext.request.contextPath}/resources/bootstrap/bower_components/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
</head>
<body>


	<div id="wrapper">
		<jsp:include page="../headernav.jsp" />


		<!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Manage Products</h1>
						<div class="col-lg-6">
							<label>Catalog</label> <select id="catid" name="catid"
								onChange="getDetails()" class="form-control pull-left"
								style="margin-bottom: 6px;">
								<c:forEach var="catalogPres" items="${catalogs}">
									<c:choose>
										<c:when test="${selectedId == catalogPres.id}">
											<option value="${catalogPres.id}" selected>${catalogPres.name}</option>
										</c:when>
										<c:otherwise>
											<option value="${catalogPres.id}">${catalogPres.name}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>

						</div>



					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->
				<div class="row">
					<div class="col-lg-12">
						<div class="panel panel-primary">
							<div class="panel-heading">Product List</div>
							<!-- /.panel-heading -->
							<div class="panel-body">
								<div class="dataTable_wrapper">
									<table class="table table-striped table-hover"
										id="dataTables-example">
										<thead>
											<tr>


												<th>Name</th>
												<th>Quantity</th>
												<th>Manufacture Date</th>
												<th>Unit Price</th>
												<th>Action</th>

											</tr>
										</thead>
										<tbody>
											<c:forEach var="productPres" items="${products}">
												<tr>
													<td>${productPres.getName()}</td>
													<td>${productPres.getQuantityAvail()}</td>
													<td>${productPres.getMfg()}</td>
													<td>${productPres.getUnitPrice()}</td>
													<td><a href="${pageContext.servletContext.contextPath}/admin/deleteproduct/${productPres.id}"><span class="btn btn-danger">
																Delete Product</span></a></td>
												</tr>
											</c:forEach>


										</tbody>
									</table>
								</div>
								<!-- /.table-responsive -->
								<div class="panel-footer">
									<a
										href="${pageContext.servletContext.contextPath}/admin/addnewproduct?id=${selectedId}&value=${name}"
										class="btn btn-primary"> Add New Product</a>
								</div>

							</div>
							<!-- /.panel-body -->


						</div>
						<!-- /.panel -->
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- /.row -->

			</div>
			<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->

	</div>

	<jsp:include page="../footernav.jsp" />
	<!-- jQuery -->
	<script
		src="${pageContext.request.contextPath}/resources/bootstrap/bower_components/jquery/dist/jquery.min.js"></script>

	<!-- DataTables JavaScript -->
	<script
		src="${pageContext.request.contextPath}/resources/bootstrap/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/bootstrap/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script
		src="${pageContext.request.contextPath}/resources/bootstrap/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script
		src="${pageContext.request.contextPath}/resources/bootstrap/bower_components/metisMenu/dist/metisMenu.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script
		src="${pageContext.request.contextPath}/resources/bootstrap/dist/js/sb-admin-2.js"></script>


	<!-- /.modal -->

	<script>
		$(document).ready(function() {
			$('#dataTables-example').DataTable({
				responsive : true
			});
			//var ddl=document.getElementById("dropdown");
			//ddl.addEventListener("onChange", selectedCatalog);
			//	alert($('#dropdown:selected').text());
		});

		function selectedCatalog() {
			alert("asdas");
			var selectedOption = document.getElementById("dropdown");
			var selectedValue = selectedOption.options[selectedOption.selectedIndex].value;
			alert(selectedValue);

		}
	</script>

</body>
</html>
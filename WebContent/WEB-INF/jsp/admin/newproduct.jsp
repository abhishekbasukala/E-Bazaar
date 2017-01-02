<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>Add New Product</title>
<meta name="generator" content="Bootply" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
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
						<h1 class="page-header">Add New Product</h1>
					</div>
					<!-- /.col-lg-12 -->
				</div>

				<div class="col-lg-9">
					<div class="panel panel-primary">
						<div class="panel-heading">New Product</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-12">
									<form
										action="${pageContext.servletContext.contextPath}/admin/saveproduct"
										method='post'>
										<input type="hidden" value="${catId}" name="catalogId"
											class="form-control">

										<div class="form-group">
											<label>Catalog</label> <input type="text" value="${catName}"
												name="catalogId" class="form-control" disabled>

										</div>

										<div class="form-group">
											<label>Product Name</label> <input type="text"
												name="productName" class="form-control">
										</div>
										<div class="form-group">
											<label>Manufacturer Date</label> <input type="text"
												name="manufactureDate" class="form-control">
										</div>
										<div class="form-group">
											<label>Number in stock</label> <input type="text"
												name="quantity" class="form-control">
										</div>
										<div class="form-group">
											<label>Unit Price </label> <input type="text"
												name="unitPrice" class="form-control">
										</div>
										<div class="form-group">
											<label>Description</label> <input type="text"
												name="description" class="form-control">

										</div>

										<div class="panel-footer">
											<input class="btn btn-primary" type="submit" value="Save"
												name="Save">
										</div>
									</form>
								</div>
								<!-- /.col-lg-6 (nested) -->
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->

	</div>

	<jsp:include page="../footernav.jsp" />
	<!-- jQuery -->
	<script
		src="${pageContext.request.contextPath}/resources/bootstrap/bower_components/jquery/dist/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script
		src="${pageContext.request.contextPath}/resources/bootstrap/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script
		src="${pageContext.request.contextPath}/resources/bootstrap/bower_components/metisMenu/dist/metisMenu.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script
		src="${pageContext.request.contextPath}/resources/bootstrap/dist/js/sb-admin-2.js"></script>

</body>
</html>
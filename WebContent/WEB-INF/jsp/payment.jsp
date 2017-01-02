<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>Shipping And Billing Address</title>
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
		<jsp:include page="headernav.jsp" />


		<!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Payment</h1>
					</div>
				</div>
				<!-- /.col-lg-12 -->
				<div class="col-lg-9">
					<div class="panel panel-primary">
						<div class="panel-heading">Credit Card Information</div>
						<c:if test="${not empty message}">
							<div class="alert alert-danger alert-dismissable">
								<button type="button" class="close" data-dismiss="alert"
									aria-hidden="true">×</button>

								${message}
								</div>
						</c:if>
				
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-12">
									<form
										action="${pageContext.servletContext.contextPath}/customer/terms"
										method="post">

										<div class="form-group">
											<label>Name</label> <input class="form-control" type="text"
												value="${ccard.name }" name="name" />
										</div>

										<div class="form-group">
											<label>Card Number</label> <input class="form-control"
												type="text" value="${ccard.ccNumber }" name="ccNumber" />
										</div>
										<div class="form-group">
											<label>Card Type</label> <select class="form-control"
												name="ccType" value="${ccard.ccType}">
												<c:choose>
													<c:when test="${ccard.ccType == 'MasterCard'}">
														<option value="Master Card" selected>MasterCard</option>
														<option value="Visa">Visa</option>
														<option value="Discover">Discover</option>
													</c:when>
													<c:when test="${ccard.ccType == 'Visa'}">
														<option value="Master Card">MasterCard</option>
														<option value="Visa" selected>Visa</option>
														<option value="Discover">Discover</option>
													</c:when>
													<c:otherwise>
														<option value="Master Card">MasterCard</option>
														<option value="Visa">Visa</option>
														<option value="Discover" selected>Discover</option>
													</c:otherwise>
												</c:choose>


											</select>
										</div>
										<div class="form-group">
											<label>Expiration Date</label> <input class="form-control "
												type="text" value="${ccard.ccExpDate}" name="ccExpDate" />
										</div>
										<div class="form-group">
											<input type="submit" class="btn btn-primary" value="Checkout">
											<input type="submit" class="btn btn-primary"
												value="Previous Page" onclick="goBack()"> <a
												href="${pageContext.servletContext.contextPath}/addToCart"
												class="pull-right"><span class="buttonNormal">
													Back to Shipping Cart</span></a>
										</div>
									</form>
								</div>
								<!-- /.col-lg-6 (nested) -->
							</div>
						</div>

					</div>
				</div>
			</div>
			<!-- /.row -->
		</div>
		<!-- /.container-fluid -->
	</div>
	<!-- /#page-wrapper -->


	<jsp:include page="footernav.jsp" />
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
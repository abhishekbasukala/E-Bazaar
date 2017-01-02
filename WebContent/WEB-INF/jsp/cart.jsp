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
						<h1 class="page-header">Cart Items</h1>

						<div class="table-responsive">
							<table class="table table-striped table-bordered table-hover">

								<thead>
									<tr>
										<th>Item</th>
										<th>Quantity</th>
										<th>Unit Price</th>
										<th>Total Price</th>
										<th>Delete</th>

									</tr>
								</thead>
								<%-- <c:forEach begin="0" end="${cartItems.length} }" var="i">
									<c:out value="${i}" />
								</c:forEach> --%>

								<c:forEach varStatus="loop" var="cartItemPres"
									items="${cartItems}">

									<tr>
										<td>${cartItemPres.itemName }</td>
										<td>${cartItemPres.quantity }</td>
										<td>${cartItemPres.price }</td>
										<td>${cartItemPres.totalPrice }</td>

										<td><a
											href="${pageContext.servletContext.contextPath}/deleteItem/${cartItemPres.itemName}">Delete</a></td>

									</tr>
								</c:forEach>
							</table>
						</div>
						<p class="buttonRow">
							<input type="submit" onclick="window.location.href='${pageContext.servletContext.contextPath}/customer/shippingbilling'" class="btn btn-primary" value="Proceed to Checkout"> 
							<input type="submit" onclick="window.location.href='${pageContext.servletContext.contextPath}'" class="btn btn-primary" value="Continue Shopping">
							<input type="submit" onclick="window.location.href='${pageContext.servletContext.contextPath}/customer/savecart'" class="btn btn-primary" value="Save Cart">
						</p>

					</div>
				</div>

				<!-- /.row -->
			</div>
			<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->

	</div>

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
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
				<form
					action="${pageContext.servletContext.contextPath}/customer/payment"
					method='post'>
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">Shipping and Billing Addresses</h1>

							<p class="error">${message}</p>
						</div>
						<!-- /.col-lg-12 -->
					</div>
					<div class="col-lg-6">
						<div class="panel panel-primary">
							<div class="panel-heading">Shipping Address</div>
							<div class="panel-body">
								<div class="row">
									<div class="col-lg-12">

										<div class="form-group">
											<label>Street</label> <input id="shipStreet"
												class="form-control" type="text"
												value="${shippingAddress.street }" name="shipStreet" />

										</div>
										<div class="form-group">
											<label>City</label> <input id="shipCity" class="form-control"
												type="text" value="${shippingAddress.city }" name="shipCity" />
										</div>
										<div class="form-group">
											<label>State</label> <input id="shipState"
												class="form-control" type="text"
												value="${shippingAddress.state }" name="shipState" />
										</div>
										<div class="form-group">
											<label>Zip</label> <input id="shipZip" class="form-control"
												type="text" value="${shippingAddress.zip }" name="shipZip" />
										</div>

									</div>
									<!-- /.col-lg-6 (nested) -->
								</div>
							</div>

						</div>
					</div>
					<div class="col-lg-6">
						<div class="panel panel-primary">
							<div class="panel-heading">Billing Address</div>
							<div class="panel-body">
								<div class="row">
									<div class="col-lg-12">

										<div class="form-group">
											<label>Street</label> <input id="billStreet"
												class="form-control" type="text"
												value="${billingAddress.street }" name="billStreet" />
										</div>
										<div class="form-group">
											<label>City</label> <input id="billCity" class="form-control"
												type="text" value="${billingAddress.city }" name="billCity" />
										</div>
										<div class="form-group">
											<label>State</label> <input id="billState"
												class="form-control" type="text"
												value="${billingAddress.state }" name="billState" />
										</div>
										<div class="form-group">
											<label>Zip</label> <input id="billZip" class="form-control"
												type="text" value="${billingAddress.zip }" name="billZip" />
										</div>

									</div>
									<!-- /.col-lg-6 (nested) -->
								</div>
							</div>

						</div>
					</div>
					<div class="col-lg-12">
						<div class="col-lg-4">
							<div class="checkbox">
								<label> <input id="sameShipBill" type="checkbox"
									name="sameShipBill" value="sameShipBill">Billing Same
									As Shipping
								</label>
							</div>
						</div>
						<div class="col-lg-4">
							<div class="checkbox">
								<label> <input type="checkbox" name="saveShippingAdd"
									value="saveShippingAdd">Save Shipping Address
								</label>
							</div>
						</div>
						<div class="col-lg-4">
							<div class="checkbox">
								<label> <input type="checkbox" name="saveBillingAdd"
									value="saveBillingAdd">Save Billing Address
								</label>
							</div>
						</div>
					</div>
					<div class="col-lg-12"
						style="margin-top: 25px; margin-bottom: 20px;">
						<div class="col-lg-3">
							<button type="button" onclick="window.location.href='${pageContext.servletContext.contextPath}/customer/selectshipaddress'"	class="btn btn-primary" name="submit"
								>Select Shipping</button>
						
						</div>
						<div class="col-lg-3">

							<button type="button"
								onclick="window.location.href='${pageContext.servletContext.contextPath}/customer/selectbilladdress'"
								class="btn btn-primary" name="submit"
								>Select Billing</button>
						</div>
						<div class="col-lg-3">
							<input type="submit" class="btn btn-success" value="Checkout" />
						</div>
						<div class="col-lg-3">
							<button type="button" onclick="window.location.href='${pageContext.servletContext.contextPath}'"
								class="btn btn-primary ">Back To Home</button>
						</div>

					</div>
				</form>
			</div>
		</div>

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
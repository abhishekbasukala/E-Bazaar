<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<link
	href="${pageContext.servletContext.contextPath}/resources/css/bootstrap.css"
	rel='stylesheet' type='text/css' />
<!-- jQuery (necessary JavaScript plugins) -->
<script type='text/javascript'
	src="${pageContext.servletContext.contextPath}/resources/js/jquery-1.11.1.min.js"></script>
<!-- Custom Theme files -->
<link
	href="${pageContext.servletContext.contextPath}/resources/css/style.css"
	rel='stylesheet' type='text/css' />
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="application/x-javascript">
	
	
        addEventListener("load", function () {
            setTimeout(hideURLbar, 0);
        }, false);

        function hideURLbar() {
            window.scrollTo(0, 1);
        }
    

</script>
<link
	href='http://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900'
	rel='stylesheet' type='text/css'>
<link
	href='http://fonts.googleapis.com/css?family=Playfair+Display:400,700,900'
	rel='stylesheet' type='text/css'>
<!-- start menu -->
<link
	href="${pageContext.servletContext.contextPath}/resources/css/megamenu.css"
	rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/resources/js/megamenu.js"></script>
<script>
	$(document).ready(function() {
		$(".megamenu").megamenu();
	});
</script>
<script
	src="${pageContext.servletContext.contextPath}/resources/js/menu_jquery.js"></script>
<script
	src="${pageContext.servletContext.contextPath}/resources/js/simpleCart.min.js">
	
</script>
</head>

<body>
	<div class="header_bg">
		<div class="container">
			<div class="header">
				<div class="head-t">
					<div class="logo">
						<a href="index.html"><img
							src="${pageContext.servletContext.contextPath}/resources/images/logo.png"
							class="img-responsive" alt="" /> </a>
					</div>
					<!-- start header_right -->
					<div class="header_right">
						<div class="rgt-bottom">
							<div class="right_btn">
								<sec:authorize ifNotGranted="ADMIN, CUSTOMER">
									<a href="${pageContext.servletContext.contextPath}/login"><span>LOGIN</span></a>
								</sec:authorize>
								 <sec:authorize ifAnyGranted="ADMIN, CUSTOMER">
								 <a href="${pageContext.servletContext.contextPath}/processLogout"><span>LOGOUT</span></a>
								 </sec:authorize>

							</div>
						</div>
						<div class="clearfix"></div>
					</div>
					<div class="clearfix"></div>
				</div>
				<!-- start header menu -->
				<ul class="megamenu skyblue">
					<li class="active grid"><a class="color1"
						href="${pageContext.servletContext.contextPath}">Browse
							Catalogs</a></li>
					<li class="grid"><a class="color2"
						href="${pageContext.servletContext.contextPath}/customer/viewPastOrder">Order
							History</a></li>
					<li class="grid"><a class="color2" href="${pageContext.servletContext.contextPath}/customer/retrievesavedcart">Retrieve Saved
							Cart</a></li>
					<li class="grid"><a class="color2"
						href="${pageContext.servletContext.contextPath}/admin/cataloglist">Maintain
							Catalogs</a></li>
					<li class="grid"><a class="color2" href="${pageContext.servletContext.contextPath}/admin/productlist">Maintain
							Products</a></li>
				</ul>
			</div>
		</div>
	</div>
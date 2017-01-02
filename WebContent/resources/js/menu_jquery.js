$(document).ready(function() {
	$('#cssmenu > ul > li > a').click(function() {
		$('#cssmenu li').removeClass('active');
		$(this).closest('li').addClass('active');
		var checkElement = $(this).next();
		if ((checkElement.is('ul')) && (checkElement.is(':visible'))) {
			$(this).closest('li').removeClass('active');
			checkElement.slideUp('normal');
		}
		if ((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
			$('#cssmenu ul ul:visible').slideUp('normal');
			checkElement.slideDown('normal');
		}
		if ($(this).closest('li').find('ul').children().length == 0) {
			return true;
		} else {
			return false;
		}
	});
});

// Login Form
$(function() {
	var button = $('#loginButton');
	var box = $('#loginBox');
	var form = $('#loginForm');
	button.removeAttr('href');
	button.mouseup(function(login) {
		box.toggle();
		button.toggleClass('active');
	});
	form.mouseup(function() {
		return false;
	});
	$(this).mouseup(function(login) {
		if (!($(login.target).parent('#loginButton').length > 0)) {
			button.removeClass('active');
			box.hide();
		}
	});
});

function goBack() {
	window.history.back();
}

function getDetails() {
	var userSelection = document.getElementById("catid").value;
	var text = document.getElementById("catid").options[document
			.getElementById('catid').selectedIndex].text;
	window.location = 'productlist?catId=' + userSelection + '&name=' + text;
}

$(document).ready(function() {
	$("#sameShipBill").click(function() {
		if ($(this).prop("checked") == true) {
			$("#billStreet").val($("#shipStreet").val());
			$("#billCity").val($("#shipCity").val());
			$("#billState").val($("#shipState").val());
			$("#billZip").val($("#shipZip").val());

		} else {
			//console.log("checkbox unchecked");

		}
	});
});
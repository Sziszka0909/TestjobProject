//When the page is infuse, get bank account details from server
//If data is empty, show error message
//Else show bank account details
//Error handling
$(document).ready(function	() {
	$.ajax({
		method: "GET",
		url: "bankaccount",
		datatype: "json",
		success: function(data){
			if(data.length == 0){
				document.getElementsByClassName("details")[0].innerHTML = "";
				$(".message").append("<h3>You haven't got any bank account.</h3>");
			}else {
				$.each(data, function(ArrayID, BankAccount){
					$(".details").append("<tr><td>" + BankAccount.accountNumber + 
							"</td><td> " + BankAccount.currency + 
							"</td> <td>" + BankAccount.balance + "</td></tr>");
				})
			}
		},
		error: function(jqXHR, exception) {
			var statusCode = jqXHR.status;
			document.getElementsByClassName("message")[0].innerHTML = "";
			if(statusCode == 500){
				$(".message").append("<p>Sorry, our database servers are temporarily down.</p>");
			} else {
				$(".message").append("<p>Something went wrong. Please try again a few minutes later.</p>")
			}
		}
	})
})
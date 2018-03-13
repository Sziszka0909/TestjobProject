//When the page is infuse, get bank account details from server
//If data is empty, show error message
//Else show bank account details
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
		error: function(data) {
			document.getElementsByClassName("errormessage")[0].innerHTML = "";
			$(".errormessage").append("<p>" + data + "</p>");
		}
	})
})
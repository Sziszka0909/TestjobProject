$(document).ready(function	() {
	//Call this 2 functions
	getSessionId();
	transaction();
})

//Get session id from server
//Call getAccount function with data
function getSessionId(){
	$.ajax({
		method:"POST",
		url:"transactionpage",
		datatype: "json",
		success: function(data){
			getAccount(data);
		}
	})
}

//Get all account from server
//Source and target drop down menu append the account number that belongs to session id
//Error handling
function getAllAccount(id){
	$.ajax({
		method:"GET",
		url:"transactionpage",
		datatype: "json",
		success: function(data){
			$.each(data, function(ArrayID, BankAccount){
				if(BankAccount.userId == Number(id)){
					$(".source").append("<option>" + BankAccount.accountNumber + "</option>");
				}
				$(".target").append("<option>" + BankAccount.accountNumber + "</option>");
			})
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
}
//Get account based on iD from server
//If data is empty show error message
//Else call getAllAccount() function with id
//Error handling
function getAccount(id){
	$.ajax({
		method: "GET",
		url: "bankaccount",
		datatype: "json",
		success: function(data){
			if(data.length == 0){
				document.getElementsByClassName("table")[0].innerHTML = "";
				$(".message").append("<h3>You haven't got any bank account.</h3>");
			} else {
				getAllAccount(id);
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
}

//When transaction button clicked
//Get details from fields
//If source field equals target field show error message
//If amount field is empty show error message
//Else send the datas to server and show the data what I get
//Error handling
function transaction(){
	$(".transaction").click(function() {
		document.getElementsByClassName("message")[0].innerHTML = "";
		var source = $(".source").val()
		var target = $(".target").val();
		var amount = $(".amount").val();
		if(source == target){
			$(".message").append("<p>The source and the target account must be different.</p>");
		} else if(amount == ""){
			$(".message").append("<p>Invalid amount.</p>");
		}else {
			$.ajax({
				method: "GET",
				url: "transfer",
				datatype: "json",
				data: { "source" : source, "target" : target, "amount" : amount },
				success: function(data){
					$(".message").append("<p>" + data + "</p>");
				},
				error: function(jqXHR, exception) {
	                var statusCode = jqXHR.status;
					document.getElementsByClassName("message")[0].innerHTML = "";
					if(statusCode == 406){
						$(".message").append("<p>Invalid amount.</p>");
					} else if(statusCode == 500){
						$(".message").append("<p>Sorry, our database servers are temporarily down.</p>");
					} else {
						$(".message").append("<p>Something went wrong. Please try again a few minutes later.</p>")
					}
				}
	        })
		}
	})
}

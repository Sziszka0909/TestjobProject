$(document).ready(function	() {
	//Call this two functions
	getSessionId();
	choose();
	
})
//get session Id from server
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
//Get account from server
//If data is empty show error message
//Else append to table the bank account details
function getAccount(id){
	$.ajax({
		method: "GET",
		url: "bankaccount",
		datatype: "json",
		success: function(data){
			if(data.length == 0){
				document.getElementsByClassName("historytable")[0].innerHTML = "";
				document.getElementsByClassName("inputField")[0].innerHTML = "";
				$(".errormessage").append("<h3>You haven't got any bank account.</h3>");
			} else {
				$.each(data, function(ArrayID, BankAccount){
					$(".sourceaccount").append("<option>" + BankAccount.accountNumber + "</option>");
				})
			}
		},
		error: function(data) {
			document.getElementsByClassName("errormessage")[0].innerHTML = "";
			$(".errormessage").append("<p>" + data + "</p>");
		}
	})
}

//When choose button clicked
//Set history table empty
//Get source account from field
//Declare a helper variable
//Get account history from server
//When data is arrive, create the table's letter head
//Append the account history details to table and in every iteration increase the helper
//When the iteration is over and the helper value is 0, set history table to empty
//And show the error message
function choose(){
	$(".choose").click(function(){
		document.getElementsByClassName("historytable")[0].innerHTML = "";
		document.getElementsByClassName("transactionnotyet")[0].innerHTML = "";
		var sourceAccount = $(".sourceaccount").val();
		var numberOfAccounts = 0;
		$.ajax({
			method: "GET",
			url: "accounthistory",
			datatype: "json",
			data: { "sourceAccount" : sourceAccount },
			success: function(data){
				$(".historytable").append("<tr><th>Source/target account</th>" +
						"<th>Currency</th><th>Amount</th>" +
						"<th>Transaction type</th></tr>")
				$.each(data, function(ArrayID, AccountHistory){
					if(AccountHistory.accountNumber == sourceAccount){
						numberOfAccounts++;
						$(".historytable").append("<tr><td>" + AccountHistory.sourceTargetAccount + "</td>" +
								"<td>" + AccountHistory.currency + "</th><td>" + AccountHistory.amount + "</td>" +
								"<td>" + AccountHistory.transactionType + "</td></tr>");
					}
				})
				if(numberOfAccounts == 0){
					document.getElementsByClassName("historytable")[0].innerHTML = "";
					$(".transactionnotyet").append("<h3>You haven't got any transaction yet.</h3>");
				}
			},
			error: function(data) {
				document.getElementsByClassName("errormessage")[0].innerHTML = "";
				$(".errormessage").append("<p>" + data + "</p>");
			}
		})
	})
}


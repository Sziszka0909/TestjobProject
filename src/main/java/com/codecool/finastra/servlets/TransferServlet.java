package com.codecool.finastra.servlets;
//This servlet communicate with db bankaccount table

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.codecool.finastra.dao.AccountHistoryDBDao;
import com.codecool.finastra.dao.BankAccountDBDao;

@WebServlet("/transfer")
public class TransferServlet extends HttpServlet{
	
	private BankAccountDBDao bankAccountDBDao = new BankAccountDBDao();
	
	//Get the details from clients side
	//Get target and source accounts currency from db
	//Check the source and the target account's is the same or not
	//Check the source account had enough money for the transaction
	//Create transfer
	//Catch the exception if the amount us not integer
	//Every case I send response to clients side
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String source = req.getParameter("source");
		String target = req.getParameter("target");
		
		PrintWriter out = resp.getWriter();
		try	{
			String sourceCurrency = bankAccountDBDao.getCurrency(source);
			String targetCurrency = bankAccountDBDao.getCurrency(target);
			
			int balance = bankAccountDBDao.getBalance(source);
			int amount = Integer.parseInt(req.getParameter("amount"));
			
			if(!sourceCurrency.equals(targetCurrency)){
				out.write("Transaction error. The currencies are not the same.");
				out.close();
			} else if(balance-amount < 0){
				out.write("Transaction error. Source account not enough balance for this transaction.");
				out.close();
			} else {
				bankAccountDBDao.createTransfer(source, target, amount);
				out.write("Transaction completed.");
				out.close();
			}
		}catch (NumberFormatException e) {
			out.write("Invalid amount.");
			out.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

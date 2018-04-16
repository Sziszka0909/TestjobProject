package com.codecool.finastra.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.codecool.finastra.dao.BankAccountDBDao;

@WebServlet("/deleteaccountnumber")
public class DeleteAccountNumberServlet extends HttpServlet{
	
	private BankAccountDBDao bankAccountDBDao = new BankAccountDBDao();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String bankAccountNumber = req.getParameter("accountNumber");
		try {
			bankAccountDBDao.deleteBankAccount(bankAccountNumber);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

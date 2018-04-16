package com.codecool.finastra.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.codecool.finastra.dao.BankAccountDBDao;

@WebServlet("/generate")
public class GenerateAccountServlet extends HttpServlet{
	
	private BankAccountDBDao bankAccountDBDao = new BankAccountDBDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String currency = req.getParameter("currency");
		HttpSession session = req.getSession(false);
		int userID = (Integer) session.getAttribute("id"); 
		
		try {
			bankAccountDBDao.addBankAccount(currency, userID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}


package com.codecool.finastra.servlets;
//This servlet communicate with db accounthistory table

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.codecool.finastra.dao.AccountHistoryDBDao;

@WebServlet("/accounthistory")
public class AccountHistoryServlet extends HttpServlet{
	
	private AccountHistoryDBDao accountHistoryDBDao = new AccountHistoryDBDao();
	
	final static Logger logger = Logger.getLogger(AccountHistoryServlet.class);
	//Get the account number from request
	//Send the history details to clients side based on account number
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String accountNumber = req.getParameter("accountNumber");
		String result;
		try {
			result = accountHistoryDBDao.getHistoryDetails(accountNumber);
			PrintWriter out = resp.getWriter();
			resp.setContentType("application/json");
			out.write(result);
			out.close();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Catch SQL Exception", e);
		}
	}

}

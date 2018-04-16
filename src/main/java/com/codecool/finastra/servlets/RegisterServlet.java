package com.codecool.finastra.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.codecool.finastra.dao.BankAccountDBDao;
import com.codecool.finastra.dao.UserDBDao;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	
	private UserDBDao userDBDao = new UserDBDao();
	private BankAccountDBDao bankAccountDBDao = new BankAccountDBDao();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		PrintWriter out = resp.getWriter();
		
		try {
			if (userDBDao.usernameAvailable(username) == false) {
				out.write("Username is not available.");
			} else {
				userDBDao.addUSer(username, password);
				out.write("ok");
			}
			out.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

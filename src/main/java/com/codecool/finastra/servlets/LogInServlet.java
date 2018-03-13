package com.codecool.finastra.servlets;
//This servlet communicate with db users table

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.codecool.finastra.dao.UserDBDao;
import com.codecool.finastra.models.User;
import com.google.gson.Gson;

import org.apache.log4j.Logger;
import org.json.*;

@WebServlet("/login")
public class LogInServlet extends HttpServlet{
	
	private UserDBDao userDBDao = new UserDBDao();
	
	final static Logger logger = Logger.getLogger(LogInServlet.class);

	//Get username and password from frontend
	//Cast result to User object
	//If User object's password not equal ""
	//Create Session, set attribute id equals to user's id in db
	//Send response to clients side
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String result;
		PrintWriter out = resp.getWriter();
		try {
			result = userDBDao.getUser(username, password);
			Gson gson = new Gson();
			User user = gson.fromJson(result, User.class);
			
			
			if(!user.getPassword().equals("")){
				JSONObject jsonObject = new JSONObject(result);
				int id = jsonObject.getInt("userId");
				HttpSession session = req.getSession();
				session.setAttribute("id", id);
				out.write("ok");
				out.close();
			} else {
				out.write("error");
				out.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			out.write("Sorry, our database servers are temporarily down.");
			logger.error("Catch SQL Exception", e);
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}

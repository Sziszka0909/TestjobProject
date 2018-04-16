package com.codecool.finastra.filter;
//In this class I create filters.
//In Webfilter annotation set which urlpatterns wants to be unavailable without session
//If I haven't got session, the application redirect me to index.html

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.Filter;

//If I navigate the above pages without session, I redirected to index.html 
@WebFilter(urlPatterns = {"/accounthistory","/bankaccount","/logout",
		"/transactionpage","/transfer","/transaction", "/transition",
		"/transaction.html","/accounthistory.html","/bankaccount.html",
		"/generate"})
public class SessionFilter implements Filter{

	//Get session, if it null redirect to index.html
	//Else set header to no-store cache, and allow the navigation
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain cha)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) req;
		HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
		HttpSession session = httpServletRequest.getSession(false);
		if(session == null){
			httpServletResponse.sendRedirect("index.html");
		} else {
			httpServletResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			httpServletResponse.setHeader("Pragma", "no-cache");
			httpServletResponse.setDateHeader("Expires", 0);
			cha.doFilter(req, resp);
		}
	}
	

}

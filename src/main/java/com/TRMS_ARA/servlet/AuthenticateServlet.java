package com.TRMS_ARA.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.LoginDao;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/authenticate")
public class AuthenticateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AuthenticateServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			LoginDao dao = new LoginDao();
			int userId = dao.loginAndGetId((String) request.getParameter("username"),
					(String)request.getParameter("password"));
			
			if (userId != 0) {
				System.out.println("HI2 from userId != 0");
				request.getSession().setAttribute("employeeId", userId);
				response.sendRedirect("Form_Status.html");
				
			}
			else {
				System.out.println("HI from userId == 0");
				request.setAttribute("errorMessage", "Invalid Username and Password");
				request.getRequestDispatcher("loginPage").forward(request, response);;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

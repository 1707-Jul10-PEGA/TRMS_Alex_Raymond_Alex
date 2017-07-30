package servlet;

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
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			LoginDao dao = new LoginDao((String) request.getParameter("username"),
					(String)request.getParameter("password"));
			int userId = dao.loginAndGetId();
			
			if (userId != 0) {
				System.out.println("HI2 from userId != 0");
				request.getSession().setAttribute("userId", userId);
				response.sendRedirect("ReimbursementForm");
				
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

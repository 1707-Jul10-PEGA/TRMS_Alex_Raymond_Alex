package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginPageServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5751052872050473807L;

	public LoginPageServlet() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws IOException, ServletException{
		request.getRequestDispatcher("loginPage.html").forward(request, response);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)  throws IOException, ServletException{
		request.getRequestDispatcher("loginPage.html").forward(request, response);
	}
}

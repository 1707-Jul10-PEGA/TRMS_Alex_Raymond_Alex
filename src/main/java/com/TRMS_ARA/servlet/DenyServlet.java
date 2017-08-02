package com.TRMS_ARA.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ApproverDao;

@WebServlet("/deny")
public class DenyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DenyServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ApproverDao dao = new ApproverDao();
		PrintWriter pw = response.getWriter();
		try {
			if(!dao.denyReimbursement(Integer.parseInt(request.getParameter("rfId")))){
				pw.write("Denial of the application is Unsuccessful");
			};
		} catch(SQLException e) {
			e.printStackTrace();
		}
		pw.write("Application denied");
	}

}

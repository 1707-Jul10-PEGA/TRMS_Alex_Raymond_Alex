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

@WebServlet("/approve")
public class ApproveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public ApproveServlet() {
        super();
       
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ApproverDao dao = new ApproverDao();
		PrintWriter pw = response.getWriter();

		try {
			if (dao.approveReimbursement(Integer.parseInt(request.getParameter("rfId")))) {
				pw.write("Form approved");
			}else {
				pw.write("Form not approved. Reimbursement Form cannot be approved by the current employee");
			};
		} catch(SQLException e) {
			pw.write("SQL Exception in Server");
		}
		catch(NumberFormatException e) {
			pw.write("Invalid rf_id recieved");
		}
		pw.write("Form approved");
	}

}
package com.TRMS_ARA.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.EmployeeDao;
import model.reimbursement.ReimbursementForm;

@WebServlet("/viewRFDetails")
public class ViewRFDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public ViewRFDetailsServlet() {
        super();
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		EmployeeDao emDao = new EmployeeDao();
		ReimbursementForm rf = null;
		try {
			System.out.println("VIEW RFID IS: " + Integer.parseInt(request.getParameter("rfId")));
			rf = emDao.getRFInfo(Integer.parseInt(request.getParameter("rfId")), (int)(request.getSession().getAttribute("employeeId")));
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		PrintWriter pw = response.getWriter();
		
		if (rf == null) {
			pw.print("NULL");
		}
		else {
			String rfString = (new ObjectMapper()).writeValueAsString(rf);
			response.setContentType("application/json");
			pw.print(rfString);
		}
	}

}

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
public class ViewRFDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public ViewRFDetails() {
        super();
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		EmployeeDao emDao = new EmployeeDao();
		ReimbursementForm rf = null;
		try {
			System.out.println("VIEW RFID IS: " + Integer.parseInt(request.getParameter("rfId")));
			rf = emDao.getRFInfo(Integer.parseInt(request.getParameter("rfId")));
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		PrintWriter pw = response.getWriter();
		
		if (rf == null) {
			pw.print("NULLLLLLLL");
		}
		else {
			String rfString = (new ObjectMapper()).writeValueAsString(rf);
			
			pw.print(rfString);
		}
	}

}
